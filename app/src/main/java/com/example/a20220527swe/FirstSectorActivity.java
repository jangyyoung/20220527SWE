package com.example.a20220527swe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstSectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_sector);

        Button[] buttonNo=new Button[147];
        for(int i=0;i<buttonNo.length;i++){
            String buttonId="locker_"+(i+1);
            buttonNo[i]=findViewById(getResources().getIdentifier(buttonId,"id",getPackageName()));
        }

        Button button1=(Button) findViewById(R.id.reserve_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FirstSectorActivity.this,ReserveTable.class);
                startActivity(intent);
            }
        });
        Button button2=(Button)findViewById(R.id.reserve_cancel_btn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_cancel=new Intent(FirstSectorActivity.this,CancelActivity.class);
                startActivity(intent_cancel);
            }
        });


    }
}