package com.example.androidassignments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// MessageDetails.java
public class MessageDetails extends AppCompatActivity {
    Long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
//                String message = extras.getString("message");
//                id = extras.getLong("id");

                MessageFragment detailsFragment = new MessageFragment();
//                Bundle args = new Bundle();
//                args.putString("message", message);
//                args.putLong("id", id);
                detailsFragment.setArguments(extras);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, detailsFragment)
                        .commit();


            }
//            FrameLayout frameLayout=findViewById(R.id.frameLayout);
//            Button deleteButton=frameLayout.findViewById(R.id.button_delete);
//        deleteButton.setOnClickListener(v -> {
//            // Notify the ChatWindow activity that this message should be deleted
//            Intent data = new Intent();
//            data.putExtra("idToDelete",id);
//            setResult(RESULT_OK, data);
//            finish(); // Finish the MessageDetails activity
//        });
        }
    }
}