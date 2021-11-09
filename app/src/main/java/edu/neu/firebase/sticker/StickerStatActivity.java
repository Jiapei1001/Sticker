package edu.neu.firebase.sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class StickerStatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_stickers);

        String currUser = getIntent().getExtras().get("current_user_username").toString();
        Button button = (Button) findViewById(R.id.show_received_stickers_btn);
        TextView textView = (TextView) findViewById(R.id.received_stickers_texts);
        textView.setText(MessageFormat.format("{0}''s sticker history:", currUser));

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
                            assert msgCard != null;
                            if (msgCard.getSender().equals(currUser)) {
                                String sticker = msgCard.getSticker();
                                stickerCnt.put(sticker, stickerCnt.getOrDefault(sticker, 1) + 1);
                            }
                        }
                        List<Map.Entry<String, Integer>> list = new LinkedList<>(stickerCnt.entrySet());
                        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

                        StringBuilder result = new StringBuilder(currUser + "'s sticker history:\n\n");
                        for (Map.Entry<String, Integer> e : list) {
                            result.append(e.getKey()).append(" has been sent: ").append(e.getValue()).append(" times\n\n");
                        }

                        textView.setText(result.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
