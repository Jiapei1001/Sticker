package edu.neu.firebase.sticker.stickerHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.neu.firebase.sticker.R;
import edu.neu.firebase.sticker.Utils;
import edu.neu.firebase.sticker.msghistory.MsgCard;

public class ReceivedStickersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_stickers);

        Intent intent = getIntent();
        String currUser = intent.getStringExtra("current_user_username");
        Button button = (Button) findViewById(R.id.show_received_stickers_btn);
        TextView textView = (TextView) findViewById(R.id.received_stickers_texts);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // connect to DB
                DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference();
                fireDB.child("messageHistory").orderByKey().addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Integer> stickerCnt = new HashMap<>();
                        for (DataSnapshot kv : snapshot.getChildren()) {
                            MsgCard msgCard = kv.getValue(MsgCard.class);
                            if (msgCard.getSender().equals(currUser)) {
                                String sticker = msgCard.getSticker();
                                stickerCnt.put(sticker, stickerCnt.getOrDefault(sticker, 1) + 1);
                            }
                        }
                        List<Map.Entry<String, Integer>> list = new LinkedList<>(stickerCnt.entrySet());
                        Collections.sort(list, (a, b) -> b.getValue().compareTo(a.getValue()));

                        String result = "\n\n";
                        for (Map.Entry<String, Integer> e : list) {
                            result += e.getKey() + " has been sent: " + e.getValue() + " times\n\n";
                        }
                        textView.setText(result);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
