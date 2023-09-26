package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity{

    Button buttonPB, buttonNP ,buttonHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonNP =findViewById(R.id.button2);
        buttonPB = findViewById(R.id.button3);
        buttonHome = findViewById(R.id.button4);

        buttonNP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openListItemsActivity();
            }
        });
        buttonPB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openMainActivity();
            }
        });
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openMainActivity();
            }
        });
    }



    public View.OnClickListener openListItemsActivity() {
        Intent i = new Intent(this, ListItemsActivity.class);
        startActivity(i);
        return null;
    }

    public View.OnClickListener openMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        return null;
    }
}