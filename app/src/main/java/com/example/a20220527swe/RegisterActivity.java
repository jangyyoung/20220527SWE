package com.example.a20220527swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    String table;
    String user;
    private DatabaseReference mPostReference;





    public void postFirebaseDatabase(boolean add){
        mPostReference=FirebaseDatabase.getInstance().getReference();
        Map<String ,Object> childUpdates=new HashMap<>();
        Map<String ,Object>postValues=null;
        if(add){
            reservationServer post= new reservationServer(table, user);
            postValues=post.toMap();
        }
        childUpdates.put("/id_list/"+table,postValues);
        mPostReference.updateChildren(childUpdates);
    }









    private FirebaseAuth rFirebaseAuth;//파이어베이스 인증처리
    private DatabaseReference rDatabaseRef; //실시간 데이터베이스
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rFirebaseAuth=FirebaseAuth.getInstance();
        rDatabaseRef= FirebaseDatabase.getInstance().getReference("table");//경로
        final FirebaseAuth[] mAuth=new FirebaseAuth[1];
        mAuth[0]=FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth[0].getCurrentUser();
        //Toast.makeText(RegisterActivity.this,user.getEmail(),Toast.LENGTH_SHORT).show();
        //email 데이터 받아오는것도 성공했어.
        Button button=findViewById(R.id.locker_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail=user.getEmail();
                String strPwd="locker_1";
                rFirebaseAuth.createUserWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=rFirebaseAuth.getCurrentUser();
                            reservationServer table=new reservationServer();


                            rDatabaseRef.child("TableAccount").child(firebaseUser.getUid()).setValue(table);
                            Toast.makeText(RegisterActivity.this,"성공", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(RegisterActivity.this,"실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}