package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button buttonLP, buttonNP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonNP = (Button) findViewById(R.id.button);
//        buttonLP = (Button) findViewById(R.id.button1);
        buttonNP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
//                openListItemsActivity();
            }


        });
//        buttonLP.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                openListItemsActivity();
//            }
//
//        });


        Log.i("MainActivity", "onCreateFun");

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("MainActivity", "onStartFun");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("MainActivity", "onResumeFun");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("MainActivity", "onPauseFun");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("MainActivity", "onStopFun");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("MainActivity", "onDestroyFun");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("MainActivity", "onSaveInstanceStateFun");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("MainActivity", "onRestoreInstanceStateFun");
    }

//    public void openLoginActivity(){
//        Intent i = new Intent(this, LoginActivity.class);
//        startActivity(i);
//    }
//    public void openListItemsActivity(){
//        Intent i = new Intent(this, LoginActivity.class);
//        startActivity(i);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("###MainActivity", "Returned to MainActivity.onActivityResult");
            if (requestCode == 10) {
                if (resultCode == Activity.RESULT_OK) {
                    String messagePassed = data.getStringExtra("Response");

                    if (messagePassed != null) {
                        // Display a Toast with the information from the Intent
                        Toast.makeText(this, "ListItemsActivity passed: " + messagePassed, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle other result codes if needed
                    Log.i("MainActivity", "User canceled the operation");
                }
            }
            Log.i("MainActivity", "Returned to MainActivity.onActivityResult");
        }
    }