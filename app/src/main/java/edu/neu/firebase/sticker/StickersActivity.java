package edu.neu.firebase.sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.neu.firebase.sticker.msghistory.MsgCard;

public class StickersActivity extends AppCompatActivity {
    private String sender;
    private String receiver;
    private EditText inputText;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);
        sender = getIntent().getExtras().get("sender").toString();
        receiver = getIntent().getExtras().get("receiver").toString();
        inputText = (EditText)findViewById(R.id.text);
    }

    public void onClickButtonSticker(View view) {
        String stickerInfo = view.getTag().toString();
        String time = String.valueOf(System.currentTimeMillis());
        uploadMessageInfo(sender, receiver, time, stickerInfo);
    }

    private void uploadMessageInfo(String sender, String receiver, String time, String stickerInfo) {
        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference messageInfo = database.child("massageHistory");
        //String pushId = messageInfo.getKey();
        int stickerId = getApplicationContext().getResources().getIdentifier("drawable/" + stickerInfo, null, getApplicationContext().getPackageName());
        MsgCard msg = new MsgCard(stickerId, sender, receiver, time, stickerInfo);
        String messageRef = "user " + sender + " send sticker " + stickerId + " to " + " user " + receiver + " at " + time;
        Map messageDatails = new HashMap<>();
        messageDatails.put(messageRef, msg);
        messageInfo.updateChildren(messageDatails).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(StickersActivity.this, "Sticker sent successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(StickersActivity.this, "Error!", Toast.LENGTH_LONG).show();
                }
                inputText.setText("");
            }
        });
    }
}