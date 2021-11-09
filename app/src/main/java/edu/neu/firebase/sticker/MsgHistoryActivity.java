package edu.neu.firebase.sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import edu.neu.firebase.sticker.msghistory.MsgAdapter;

public class MsgHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_history);

        Intent intent = getIntent();
        String currUser = intent.getStringExtra("sender");
        String friend = intent.getStringExtra("receiver"); // friend

        TextView msgSubject = (TextView) findViewById(R.id.msg_history_subject);
        msgSubject.setText("messages from your amazing friend: " + friend);

        // get msg contents for adapter
        ArrayList<MsgCard> msgCardList = new ArrayList<>();
        MsgAdapter msgHistoryAdapter = new MsgAdapter(msgCardList, currUser, friend);

        // assign adapter with msg contents to adapter view
        RecyclerView msgHistoryAdapterView = (RecyclerView) findViewById(R.id.msgHistoryAdapter);
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
                            msgCardList.add(msgCard);
                        }
                        Collections.sort(msgCardList, (a, b) -> a.getSendTime().compareTo(b.getSendTime()));

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