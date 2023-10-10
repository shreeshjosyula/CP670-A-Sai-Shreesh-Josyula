package com.example.androidassignments;
import android.content.Context;
import android.os.Bundle;
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

    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private ArrayList<String> chatMessages;

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


        final ChatAdapter adapter = new ChatAdapter(this, chatMessages);
        chatListView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEditText.getText().toString();

                chatMessages.add(message);

                adapter.notifyDataSetChanged();

                messageEditText.setText("");
            }
        });
    }


    public class ChatAdapter extends ArrayAdapter<String> {
        public ArrayList<String> message;
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

}
