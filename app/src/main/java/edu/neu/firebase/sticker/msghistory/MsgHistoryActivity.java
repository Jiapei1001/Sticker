package edu.neu.firebase.sticker.msghistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.neu.firebase.sticker.R;

public class MsgHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_history);

        Intent intent = getIntent();
        String currUser = intent.getStringExtra("current_user_username");
        String friend = intent.getStringExtra("friend_username");

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


    }
}