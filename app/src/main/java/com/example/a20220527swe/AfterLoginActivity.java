package com.example.a20220527swe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AfterLoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRevoke,btnLogout,btnRegister;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        btnLogout=(Button)findViewById(R.id.btn_logout);
        btnRevoke=(Button)findViewById(R.id.btn_revoke);


        btnRegister=(Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(this);
        btnRevoke.setOnClickListener(this);
    }

    private  void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
    private void revokeAccess(){
        mAuth.getCurrentUser().delete();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                signOut();;
                finishAffinity();
                break;
            case R.id.btn_revoke:
                revokeAccess();
                finishAffinity();
                break;
            case R.id.btn_register:
                Intent intent =new Intent(AfterLoginActivity.this,FirstSectorActivity.class);
                startActivity(intent);
        }
    }
}