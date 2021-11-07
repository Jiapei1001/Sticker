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
        int stickerId = view.getId();
        String time = String.valueOf(System.currentTimeMillis());
        uploadMessageInfo(sender, receiver, time, stickerId);
    }

    private void uploadMessageInfo(String sender, String receiver, String time, int stickerId) {
        String messageRef = "user " + sender + " send sticker " + stickerId + " to " + " user " + receiver + " at " + time;
        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference messageInfo = database.child("massages").push();
        String pushId = messageInfo.getKey();
        Map messageBody = new HashMap<>();
        messageBody.put("sticker id", stickerId);
        messageBody.put("from", sender);
        messageBody.put("to", receiver);
        messageBody.put("time", time);

        Map messageDatails = new HashMap<>();
        messageDatails.put(messageRef + "/" + pushId, messageBody);
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