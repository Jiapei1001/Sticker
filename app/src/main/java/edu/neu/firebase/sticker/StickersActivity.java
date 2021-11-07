package edu.neu.firebase.sticker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class StickersActivity extends AppCompatActivity {
    private String sender;
    private String receiver;
    private TextView text;
    private DatabaseReference database;
    private DatabaseReference messageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);
        sender = getIntent().getExtras().get("sender").toString();
        receiver = getIntent().getExtras().get("receiver").toString();
        text = (TextView)findViewById(R.id.text);

    }
    public void onClickButtonSticker(View view) {
        int stickerId = view.getId();
        String time = String.valueOf(System.currentTimeMillis());
        uploadMessageInfo(sender, receiver, time, stickerId);
    }

    private void uploadMessageInfo(String sender, String receiver, String time, int stickerId) {

    }
}