package com.example.a20220527swe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth=null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN=90001;
    private SignInButton signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ivGlide=(ImageView) findViewById(R.id.iv_glide);

        signInButton=findViewById(R.id.signInButton);


        Glide.with(this).load("https://www.google.com/search?q=%EA%B0%80%EC%B2%9C%EB%8C%80%ED%95%99%EA%B5%90%EB%A1%9C%EA%B3%A0&sxsrf=ALiCzsZotR8kNTiWzJGjdCbP3_v5u0JdTg:1653587942476&tbm=isch&source=iu&ictx=1&vet=1&fir=5ZIkVW2myYxk7M%252CQtIGBmhjxEHp9M%252C_%253BzVobX4uc0UiN0M%252CvsCjWjuU4pzFkM%252C_%253BjNNR3TVDmHtxNM%252CQIS5-nD_VXfd9M%252C_%253BRzJV_w1f6CD2sM%252CSzlse1TP6DqatM%252C_%253BMiKVjIQ4tGyW-M%252C2J4lnDrBCA3OzM%252C_%253BZ2BytJeOTPHPqM%252CkJS1IQ1_rYqEQM%252C_%253BfDv79WLe0r82rM%252CVt3FsrhwEM460M%252C_%253B75bP5ulQ8g06LM%252CUtDCKxfP_CR6EM%252C_%253BmFNgzFjBx4tm1M%252CSzlse1TP6DqatM%252C_%253BwOU26xbgmux7AM%252CaHUPCqTd7J8uPM%252C_%253BCg48cuB7f9eo0M%252CKakWVNXtR6msuM%252C_%253B15WwqeaQZOLkTM%252CQtIGBmhjxEHp9M%252C_&usg=AI4_-kQmb5Eu2mfaZBEhg7ncH_FZWEOorg&sa=X&ved=2ahUKEwjo2amP3_33AhVEm1YBHWblA_gQ9QF6BAgEEAE#imgrc=5ZIkVW2myYxk7M").override(300,200).fitCenter().into(ivGlide);

        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(getApplication(),AfterLoginActivity.class);
            startActivity(intent);
            finish();
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("105544026128-h2ovithlemio8rknari2vno34abe1172.apps.googleusercontent.com").requestEmail().build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    //[start signin]
    private void signIn(){
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }catch (ApiException e){

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Snackbar.make(findViewById(R.id.layout_main),"인증 성공",Snackbar.LENGTH_SHORT).show();
                    FirebaseUser user=mAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    Snackbar.make(findViewById(R.id.layout_main),"인증 실패",Snackbar.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }
    private void updateUI(FirebaseUser user){
        if(user != null){
            Intent intent=new Intent(this,AfterLoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}