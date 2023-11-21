package com.example.androidassignments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

// MessageDetailsFragment.java
public class MessageFragment extends Fragment {
    Long id;

    private ChatWindow chatActivity;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ChatWindow) {
            chatActivity = (ChatWindow) context;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        TextView messageText = view.findViewById(R.id.messageText);
        TextView messageId = view.findViewById(R.id.messageID);
        Button deleteButton=view.findViewById(R.id.button_delete);

        Bundle args = getArguments();
        if (args != null) {
            String message = args.getString("message");
            id = args.getLong("id");

            messageText.setText(message);
            messageId.setText("ID: " + id);

            // Implement onClickListener for deleteButton if needed
//            deleteButton.setOnClickListener(v -> {
//                // Implement delete action here
//            });
        }
        deleteButton.setOnClickListener(v -> {
            // Notify the ChatWindow activity that this message should be deleted
            if (chatActivity != null) {
                chatActivity.deleteMessage(id);
                chatActivity.updateListView();
                chatActivity.removeFragment();
            }else{
                Intent data = new Intent();
                data.putExtra("idToDelete",id);
                getActivity().setResult(getActivity().RESULT_OK, data);
                getActivity().finish();//
            }
        });

        return view;
    }
}