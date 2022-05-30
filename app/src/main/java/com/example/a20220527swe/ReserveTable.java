package com.example.a20220527swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReserveTable extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mPostReference;
    private FirebaseAuth rFirebaseAuth;
    Button btn_Update;
    EditText edit_ID;
    String ID;
    String name;
    String sort ="id";
    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_table);
        btn_Update = (Button) findViewById(R.id.btn_update);
        edit_ID = (EditText) findViewById(R.id.edit_id);
        Button button1=(Button) findViewById(R.id.locker_1);
        btn_Update.setOnClickListener(new View.OnClickListener() {
        String ref;
            @Override
            public void onClick(View v) {

                mPostReference=FirebaseDatabase.getInstance().getReference("/id");
                mPostReference.orderByChild("id").addValueEventListener(new ValueEventListener() {
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
                if(ref == edit_ID.getText().toString()){

                }
                else{
                    ID = edit_ID.getText().toString();
                    name = user.getEmail();
                    postFirebaseDatabase(true);
                    getFirebaseDatabase();
                    setInsertMode();

                    Intent intent = new Intent(ReserveTable.this,FirstSectorActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void setInsertMode(){
        edit_ID.setText("");
        btn_Update.setEnabled(false);
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
    public void clearFirebaseDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){

        }
    }
    public void getFirebaseDatabase(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: " + key);

                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayData);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        };

    }

    @Override
    public void onClick(View v) {

    }

    //@Override

}