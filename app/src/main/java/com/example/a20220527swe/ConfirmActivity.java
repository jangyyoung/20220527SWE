package com.example.a20220527swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmActivity extends AppCompatActivity {
    private DatabaseReference mPostReference;
    private FirebaseAuth rFirebaseAuth;
    String ID;
    String name;
    TextView txtText;
    String sort ="id";
    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_confirm);
        txtText=(TextView)findViewById(R.id.txtText);
        Intent intent =getIntent();

        String data=intent.getStringExtra("data");
        int LoakerN=intent.getIntExtra("LN",0);
        txtText.setText(data);
        Button confirmbutton=(Button) findViewById(R.id.confirm_button);
        Button confirmcancel=(Button) findViewById(R.id.confirm_cancel);
        confirmbutton.setOnClickListener(new View.OnClickListener() {
            String ref;
            @Override
            public void onClick(View v) {
                mPostReference= FirebaseDatabase.getInstance().getReference("/LoakerN");
                mPostReference.orderByChild("LoakerN").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value=snapshot.getValue(String.class);
                        ref=value;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                final FirebaseAuth[] mAuth=new FirebaseAuth[1];
                mAuth[0]=FirebaseAuth.getInstance();
                final FirebaseUser user=mAuth[0].getCurrentUser();
                if(ref==String.valueOf(LoakerN)){
                   // Toast.makeText(getApplicationContext(),"이미 예약된 사물함입니다.",Toast.LENGTH_SHORT).show();
                }else{
                    ID=String.valueOf(LoakerN);
                    name=user.getEmail();
                    postFirebaseDatabase(true);

                }
                Toast.makeText(getApplicationContext(),"예약되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent_confirm=new Intent();
                intent_confirm.putExtra("result","Close Popup");
                setResult(RESULT_OK,intent);
                finish();

            }
        });
        confirmcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_confirm=new Intent();
                intent_confirm.putExtra("result","Close Popup");
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }

    public boolean IsExistUser(){
        boolean IsExist = arrayIndex.contains(name);
        return IsExist;
    }
    public void postFirebaseDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            FirebasePost post = new FirebasePost(ID, name);
            postValues = post.toMap();
        }
        childUpdates.put("/id_list/" + ID, postValues);
        mPostReference.updateChildren(childUpdates);
    }
}