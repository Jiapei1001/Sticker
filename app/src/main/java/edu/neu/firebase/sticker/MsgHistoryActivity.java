package edu.neu.firebase.sticker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MsgHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_history);

        Intent intent = getIntent();
        String currUser = intent.getStringExtra("sender");
        String friend = intent.getStringExtra("receiver"); // friend

        TextView msgSubject = findViewById(R.id.msg_history_subject);
        msgSubject.setText(MessageFormat.format("messages from your amazing friend: {0}", friend));

        // get msg contents for adapter
        ArrayList<MsgCard> msgCardList = new ArrayList<>();
        MsgAdapter msgHistoryAdapter = new MsgAdapter(msgCardList, currUser, friend);

        // assign adapter with msg contents to adapter view
        RecyclerView msgHistoryAdapterView = findViewById(R.id.msgHistoryAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgHistoryAdapterView.setLayoutManager(layoutManager);
        msgHistoryAdapterView.setAdapter(msgHistoryAdapter);

        // connect to firebase db
        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference();

        // set event listener
        fireDB.child("messageHistory").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // clean up
                        msgCardList.clear();
                        // update
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            MsgCard msgCard = ds.getValue(MsgCard.class);
                            assert msgCard != null;
                            if ((msgCard.getSender().equals(currUser) && msgCard.getReceiver().equals(friend))
                                    || (msgCard.getSender().equals(friend) && msgCard.getReceiver().equals(currUser))) {
                                msgCardList.add(msgCard);
                            }
                        }
                        msgCardList.sort((a, b) -> a.getSendTime().compareTo(b.getSendTime()));

                        // update msgHistoryAdapter as msg contents have changed
                        msgHistoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
}