package com.example.androidassignments;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ListItemsActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE  = 2;
    Button buttonPB,buttonHome;
    ImageButton buttonPhoto;
    Switch buttonSwitch;

    CheckBox buttonCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        buttonPB = findViewById(R.id.button5);
        buttonHome = findViewById(R.id.button6);
        buttonPhoto = findViewById(R.id.imageButton);
        buttonSwitch = findViewById(R.id.switchListItems);
        buttonCheckBox = findViewById(R.id.checkBoxListItems);

        print(getString(R.string.iambuttonDebug));



        // CheckBox button
        buttonCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showFinishDialog();
                }
            }
        });



        // Switch button
        buttonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked ) {
                CharSequence text = isChecked ? getString(R.string.switchOn) : getString(R.string.switchOff);
                int duration = isChecked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
                toast.show();
            }
        });



        // Change photo button
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                if (ContextCompat.checkSelfPermission(ListItemsActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListItemsActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(pictureIntent.resolveActivity(getPackageManager())!=null){
                        startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });



        // Previous Page button
        buttonPB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openMainActivity();
            }
        });



        // Home Button
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openLoginActivity();
            }
        });


        Log.i("ListItemsActivity", "onCreateFun");
    }

    //Oncreate fun ends



    private void showFinishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);

        builder.setMessage(R.string.dialogMessage)
                .setTitle(R.string.dialogTitle)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onFinish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        buttonCheckBox.setChecked(false);
                    }
                })
                .show();
    }


    private void onFinish() {
        // Debug information
        Log.i("ListItemsActivity", "onFinish()");
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Response", R.string.listActivityExitResponse);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }





    private void print(String message) {
        //Toast:
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        //Snackbar:
//         Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Log.i("Camera", "image");

            if (extra != null) {
                Bitmap image = (Bitmap) extra.get("data");
                if(image != null){
                    buttonPhoto.setImageBitmap(image);
                }

            }
        }

    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to take a picture", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onStart(){
        super.onStart();
        Log.i("ListItemsActivity", "onStartFun");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("ListItemsActivity", "onResumeFun");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("ListItemsActivity", "onPauseFun");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("ListItemsActivity", "onStopFun");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("ListItemsActivity", "onDestroyFun");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("ListItemsActivity", "onSaveInstanceStateFun");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("ListItemsActivity", "onRestoreInstanceStateFun");
    }

    // Opens Login Activity Page
    public View.OnClickListener openLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        return null;
    }

    // Opens Main Activity Page
    public View.OnClickListener openMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        return null;
    }



}