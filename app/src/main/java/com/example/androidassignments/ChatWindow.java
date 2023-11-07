package com.example.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "ChatWindow";
    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private ArrayList<String> chatMessages;
    public static final String KEY_MESSAGE = "MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatListView = findViewById(R.id.chatListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        chatMessages = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ChatDatabaseHelper databaseHelper = new ChatDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query = "SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor != null && cursor.moveToNext()) {
            int messageColumnIndex = cursor.getColumnIndex(KEY_MESSAGE);
            if (messageColumnIndex >= 0) {
                String dbMessage = cursor.getString(messageColumnIndex);
                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + dbMessage);
                chatMessages.add(dbMessage);
            } else {
                Log.e(ACTIVITY_NAME, "Column not found: " + KEY_MESSAGE);
            }
        }

        if (cursor != null) {
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                Log.i(ACTIVITY_NAME, "Column " + i + ": " + columnName);
            }

            cursor.close();
        }

        db.close();

        final ChatAdapter adapter = new ChatAdapter(this, chatMessages);
        chatListView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEditText.getText().toString();
                chatMessages.add(message);
                adapter.notifyDataSetChanged();
                saveMessageToDatabase(message);
                messageEditText.setText("");
            }
        });
    }

    private void saveMessageToDatabase(String message) {
        ChatDatabaseHelper databaseHelper = new ChatDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);

        long newRowId = db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Log.e(ACTIVITY_NAME, "Error inserting message into the database");
        } else {
            Log.i(ACTIVITY_NAME, "Message inserted successfully");
        }

        db.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        private ArrayList<String> message;

        public ChatAdapter(Context ctx, ArrayList<String> txtMessage) {
            super(ctx, 0);
            this.message = txtMessage;
        }

        @Override
        public int getCount() {
            return message.size();
        }

        @Override
        public String getItem(int position) {
            return message.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View resultView = null;
            if (position % 2 == 0) {
                resultView = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                resultView = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            String message = getItem(position);

            TextView textView = resultView.findViewById(R.id.message_text);
            textView.setText(getItem(position));

            return resultView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChatDatabaseHelper databaseHelper = new ChatDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.close();
        }
    }
}
