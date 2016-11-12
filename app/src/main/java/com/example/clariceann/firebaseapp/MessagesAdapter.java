package com.example.clariceann.firebaseapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private Context mContext;
    private DatabaseReference mRef;
    private ChildEventListener mChildEventListener;

    private List<String> mMessagesIds = new ArrayList<>();
    private List<Message> mMessages = new ArrayList<>();

    public MessagesAdapter(Context mContext, DatabaseReference mRef) {
        this.mContext = mContext;
        this.mRef = mRef;

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);

                mMessagesIds.add(dataSnapshot.getKey());
                mMessages.add(message);
                notifyItemInserted(mMessages.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Message updatedMessage = dataSnapshot.getValue(Message.class);
                String messageKey = dataSnapshot.getKey();

                int messageIndex = mMessagesIds.indexOf(messageKey);

                if (messageIndex > -1) {
                    mMessages.set(messageIndex, updatedMessage);

                    notifyItemChanged(messageIndex);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.addChildEventListener(mChildEventListener);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), android.R.layout.simple_expandable_list_item_1, null);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.mMessage.setText(mMessages.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView mMessage;

        public MessageViewHolder(View view) {
            super(view);

            mMessage = (TextView) view.findViewById(android.R.id.text1);
        }
    }
}
