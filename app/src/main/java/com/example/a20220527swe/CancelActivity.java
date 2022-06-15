package com.example.a20220527swe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;
import java.util.HashMap;

public class CancelActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mPostReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        mDatabase=FirebaseDatabase.getInstance();


        Button button_confirm=(Button) findViewById(R.id.cancel_confirm);
        Button button_B=(Button) findViewById(R.id.cancel_cancel);


        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFirebaseDatabase(true);
                mPostReference.child("/id").removeValue();
                Intent intent = new Intent(getApplication(),FirstSectorActivity.class);
                startActivity(intent);
            }
        });

        button_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),FirstSectorActivity.class);
                startActivity(intent);
            }
        });
    }
    public void clearFirebaseDatabase(boolean add){
        mPostReference=mDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            FirebasePost post = new FirebasePost(null,null);
            postValues = post.toMap();
        }
        childUpdates.clear();
        mPostReference.updateChildren(childUpdates);
    }
}