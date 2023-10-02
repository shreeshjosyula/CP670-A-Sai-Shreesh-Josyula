package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{


    Button buttonPB, buttonNP ,buttonHome,buttonLogin;
    EditText emailText,passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        buttonNP =findViewById(R.id.button2);
//        buttonPB = findViewById(R.id.button3);
//        buttonHome = findViewById(R.id.button4);
        buttonLogin = findViewById(R.id.button7);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        SharedPreferences sp = getSharedPreferences("MyPreference", MODE_PRIVATE);
        String emailStore = sp.getString("DefaultEmail", "shreeshjosyula@gmail.com");

        emailText.setText(emailStore);


        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String emailEntered = emailText.getText().toString().trim();
                String passwordEntered = passwordText.getText().toString().trim();

                if (TextUtils.isEmpty(emailEntered) || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailEntered).matches()) {
                    Toast.makeText(LoginActivity.this, "Enter valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordEntered)) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


                SharedPreferences.Editor editor = sp.edit();
                editor.putString("DefaultEmail", emailEntered);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);


            }

        });


//        buttonNP.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                openListItemsActivity();
//            }
//        });
//        buttonPB.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                openMainActivity();
//            }
//        });
//        buttonHome.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                openMainActivity();
//            }
//        });
        Log.i("LoginActivity", "onCreateFun");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("LoginActivity", "onStartFun");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("LoginActivity", "onResumeFun");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("LoginActivity", "onPauseFun");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("LoginActivity", "onStopFun");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("LoginActivity", "onDestroyFun");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("LoginActivity", "onSaveInstanceStateFun");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("LoginActivity", "onRestoreInstanceStateFun");
    }



//    public View.OnClickListener openListItemsActivity() {
//        Intent i = new Intent(this, ListItemsActivity.class);
//        startActivity(i);
//        return null;
//    }
//
//    public View.OnClickListener openMainActivity() {
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
//        return null;
//    }
}