package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ChatWindow extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView chatView;
    EditText chatText;
    Button sendButton;
    SQLiteDatabase database;

    private static final String TAG="ChatWindowActivity";
    private final ArrayList<String> StrArr = new ArrayList<>();
    private final ArrayList<Integer> IDarr = new ArrayList<>();
    Cursor cursor;
    ChatAdapter messageAdapter;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChatDatabaseHelper dpHelper=new ChatDatabaseHelper(this);
        database = dpHelper.getWritableDatabase();
        setContentView(R.layout.activity_chat_window);
        chatView=findViewById(R.id.chatView);
        chatText=findViewById(R.id.chatText);
        messageAdapter=new ChatAdapter(this, cursor);
        chatView.setAdapter(messageAdapter);
        sendButton=findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            String message=chatText.getText().toString().trim();
            if(!message.isEmpty()){
                StrArr.add(message);
                ContentValues cValues=new ContentValues();
                cValues.put(ChatDatabaseHelper.KEY_MESSAGE,message);
                database.insert(ChatDatabaseHelper.KEY_TABLE,null,cValues);
                chatText.setText("");
                updateListView();
            }
        });



        cursor=database.query(ChatDatabaseHelper.KEY_TABLE, new String[]{"*"},null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            StrArr.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            IDarr.add(Integer.valueOf(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID))));
            Log.i(TAG, "SQl MESSAGE: "+cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(TAG, "Cursor's  column count =" + cursor.getColumnCount() );
            cursor.moveToNext();
        }
        int ColumnCount=cursor.getColumnCount();
        for(int i=0;i<ColumnCount;i++){
            Log.i(TAG, cursor.getColumnName(i));
        }
        chatView.setOnItemClickListener(this);
        cursor.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        View frameLayout = findViewById(R.id.frameLayout);
        // Check if it's a tablet layout
        if (frameLayout!=null) {
            Toast.makeText(this, "tableLayout", Toast.LENGTH_SHORT).show();
            // Tablet layout, use a fragment to show message details
            MessageFragment detailsFragment = new MessageFragment();
            Bundle args = new Bundle();
            args.putString("message", StrArr.get(position));
            args.putLong("id", IDarr.get(position));
            detailsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Bundle args = new Bundle();
            args.putString("message", StrArr.get(position));
            args.putLong("id", IDarr.get(position));
            // Phone layout, start MessageDetails activity
            Intent intent = new Intent(this, MessageDetails.class);
//            intent.putExtra("message", StrArr.get(position));
//            intent.putExtra("id", IDarr.get(position));
            intent.putExtras(args);
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Check if the result is from the MessageDetails activity
            assert data != null;
            long idToDelete = data.getLongExtra("idToDelete", -1);

            // Handle the delete action using the idToDelete
            if (idToDelete != -1) {
                deleteMessage(idToDelete);
                updateListView();
                removeFragment();
                // Implement the logic to delete the message with the given ID
            }
        }
    }

    public void deleteMessage(long messageId) {
        // Implement the logic to delete the message with the given ID from the database
        database.delete(ChatDatabaseHelper.KEY_TABLE, ChatDatabaseHelper.KEY_ID + "=?", new String[]{String.valueOf(messageId)});
    }

    @SuppressLint("Range")
    public void updateListView() {
        // Clear the existing data in the ArrayList
        StrArr.clear();
        IDarr.clear();

        // Query the database for the latest data
        Cursor newCursor = database.query(ChatDatabaseHelper.KEY_TABLE, new String[]{"*"}, null, null, null, null, null);

        // Populate the ArrayList with the new data
        newCursor.moveToFirst();
        while (!newCursor.isAfterLast()) {
            StrArr.add(newCursor.getString(newCursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            IDarr.add(newCursor.getInt(newCursor.getColumnIndex(ChatDatabaseHelper.KEY_ID)));
            newCursor.moveToNext();
        }

        // Notify the adapter that the underlying data has changed
        messageAdapter.notifyDataSetChanged();

        // Close the old cursor if it's not null
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        // Update the reference to the new cursor
        cursor = newCursor;
    }

    public void removeFragment() {
        // Only call this function if running on a tablet
        View frameLayout = findViewById(R.id.frameLayout);
        if (frameLayout != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.frameLayout)))
                    .commit();
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        database.close();
    }

    public class ChatAdapter extends ArrayAdapter<String>{
        private final Cursor cursor;

        public ChatAdapter(Context ctx, Cursor cursor) {
            super(ctx, 0);
            this.cursor = cursor;
        }
        public int getCount(){
            return StrArr.size();
        }
        public String getItem(int position){
            return StrArr.get(position);
        }
        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent){
            LayoutInflater inflater=ChatWindow.this.getLayoutInflater();
            View Result;
            if(position%2==0)
                Result=inflater.inflate(R.layout.chat_row_incoming,null);
            else
                Result=inflater.inflate(R.layout.chat_row_outgoing,null);
            TextView message= Result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return Result;
        }

        @Override
        public long getItemId(int position) {
            if (cursor != null && cursor.moveToPosition(position)) {
                return cursor.getLong(cursor.getColumnIndexOrThrow(ChatDatabaseHelper.KEY_ID));
            }
            return -1; // Return -1 if no valid ID is found
        }
    }

}