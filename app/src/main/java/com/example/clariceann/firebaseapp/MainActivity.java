package com.example.clariceann.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText mMessage;
    RecyclerView mMessages;
    Button mSend;

    private DatabaseReference mReference;
    private MessagesAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReference = FirebaseDatabase.getInstance().getReference().child("messages");
        mAdapter = new MessagesAdapter(this, mReference);

        mMessage = (EditText) findViewById(R.id.message);
        mMessages = (RecyclerView) findViewById(R.id.messages);
        mSend = (Button) findViewById(R.id.send);

        mMessages.setLayoutManager(new LinearLayoutManager(this));
        mMessages.setAdapter(mAdapter);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMessage.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                } else {

                    String key = mReference.push().getKey();
                    Message message = new Message(mMessage.getText().toString());
                    mReference.child(key).setValue(message);
                    Log.d("MainActivity", mMessage.getText().toString() + " " + key);
                    mMessage.setText("");
                }
            }
        });
    }
}
