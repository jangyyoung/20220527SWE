package com.example.a20220527swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    ValueEventListener mValueEventListener;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_first_sector);
        ValueEventListener mValueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    String key=postSnapshot.getKey();
                    FirebasePost firebasePost=postSnapshot.getValue(FirebasePost.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("id_list").addValueEventListener(mValueEventListener);
        Button cancel=(Button)findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancel=new Intent(FirstSectorActivity.this,CancelActivity.class);
                startActivity(intentCancel);
            }
        });
        Button[] buttonNo=new Button[147];
        for(int i=0;i<buttonNo.length;i++){
            String buttonId="locker_"+(i+1);
            buttonNo[i]=findViewById(getResources().getIdentifier(buttonId,"id",getPackageName()));


        }
        for(int i=0;i<buttonNo.length;i++){

            int finalI = i;
            String finalI1=Integer.toString(finalI);
            DatabaseReference database=FirebaseDatabase.getInstance().getReference();
            DatabaseReference ref=database.child("id_list");
            Query lockerQuery= ref.orderByChild("id").equalTo(i);
            String refer;
            buttonNo[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lockerQuery.toString()==finalI1){
                        Toast.makeText(getApplicationContext(),"이미 예약된 사물함입니다.",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent=new Intent(FirstSectorActivity.this,ConfirmActivity.class);
                        intent.putExtra("data",(finalI +1)+"라커를 예약하시겠습니까?");
                        intent.putExtra("LN",finalI+1);
                        startActivity(intent);
                    }


                }
            });
        }


    }

}