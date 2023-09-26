package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListItemsActivity extends AppCompatActivity {

    Button buttonPB,buttonHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        buttonPB = findViewById(R.id.button5);
        buttonHome = findViewById(R.id.button6);

        buttonPB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openLoginActivity();
            }
        });
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openMainActivity();
            }
        });
    }
    public View.OnClickListener openLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        return null;
    }

    public View.OnClickListener openMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        return null;
    }
}