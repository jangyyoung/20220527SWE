package com.example.a20220527swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirstSectorActivity extends AppCompatActivity {
    TextView txtResult;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mPostReference;
    private FirebaseAuth rFirebaseAuth;
    private DataSnapshot dataSnapshot;
    private ChildEventListener mChild;
    private ArrayAdapter<String> adapter;
    List<Object> Array=new ArrayList<Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_sector);

        //mPostReference=FirebaseDatabase.getInstance().getReference();
        //String lastLoaker=mPostReference.child("id_list").child("id").orderByKey().limitToFirst(147).toString();

        Button[] buttonNo=new Button[147];
        for(int i=0;i<buttonNo.length;i++){
            String buttonId="locker_"+(i+1);
            buttonNo[i]=findViewById(getResources().getIdentifier(buttonId,"id",getPackageName()));


        }
        for(int i=0;i<buttonNo.length;i++){
            int finalI = i;
            DatabaseReference database=FirebaseDatabase.getInstance().getReference();
            DatabaseReference ref=database.child("id_list");
            Query lockerQuery= ref.orderByChild("id").equalTo(i);
            String refer;
            buttonNo[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lockerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String value=snapshot.getValue(String.class);
                            //FirebasePost value=snapshot.getValue(FirebasePost.class);
                            if(value!=null){
                                Toast.makeText(getApplicationContext(),"이미 예약된 사물함입니다.",Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intent=new Intent(FirstSectorActivity.this,ConfirmActivity.class);
                                intent.putExtra("data",(finalI +1)+"라커를 예약하시겠습니까?");
                                intent.putExtra("LN",finalI+1);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            });
        }
        ValueEventListener mValueEventListener;
        ValueEventListener mvalueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot:dataSnapshot.getChildren()){
                    String key=datasnapshot.getKey();
                    String loakerid=datasnapshot.child("id").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        //mDatabase.child("id_list").addValueEventListener(mvalueEventListener);


    }

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                txtResult.setText(result);
            }
        }
    }

}