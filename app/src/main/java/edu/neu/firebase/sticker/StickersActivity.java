package edu.neu.firebase.sticker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StickersActivity extends AppCompatActivity {
    private String sender;
    private String receiver;
    private TextView inputText;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);
        sender = getIntent().getExtras().get("sender").toString();
        receiver = getIntent().getExtras().get("receiver").toString();
        inputText = (TextView) findViewById(R.id.text);
        createNotificationChannel();
    }

    public void onClickButtonSticker(View view) {
        String stickerInfo = (String) view.getTag();

        String time = String.valueOf(System.currentTimeMillis());
        uploadMessageInfo(sender, receiver, time, stickerInfo);

        String channelId = "100";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(StickersActivity.this, channelId);

        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        builder.setContentTitle("Stick Send to the User!");                    //set title
        builder.setContentText("Click to jump to choose other user");                 //message content
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);

        //jump to activity
        Intent intent = new Intent(StickersActivity.this, JumpAvtivity.class);
        PendingIntent pi = PendingIntent.getActivities(StickersActivity.this, 0, new Intent[]{intent}, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pi);
        //show content
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("100", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void uploadMessageInfo(String sender, String receiver, String time, String stickerInfo) {
        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference messageInfo = database.child("messageHistory");
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