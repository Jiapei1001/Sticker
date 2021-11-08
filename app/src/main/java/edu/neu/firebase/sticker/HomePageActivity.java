package edu.neu.firebase.sticker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import edu.neu.firebase.sticker.R;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePageActivity extends AppCompatActivity {

    private String username;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        username = getIntent().getStringExtra("USERNAME");

        TextView user_info = (TextView) findViewById(R.id.userInfoTextView);
        user_info.setText("Hello " + username);
        User user = new User(username, 4);
        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get user reference
        mUsers = mDatabase.child("users");
        //add user to db
        mUsers.child(username).setValue(user);
        createNotificationChannel();
        Button button2 = (Button) findViewById(R.id.sendNotificationButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String channelId = "100";
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(HomePageActivity.this, channelId);

                builder.setSmallIcon ( R.drawable.logo );
                builder.setLargeIcon ( BitmapFactory.decodeResource( getResources (),R.drawable.logo ) );
                builder.setContentTitle("Title");                    //set title
                builder.setContentText("Click to jump");                 //message content
                builder.setWhen(System.currentTimeMillis());
                builder.setAutoCancel(true);


                //jump to activity
                Intent intent =new Intent (HomePageActivity.this,NotificationIntent.class);
                PendingIntent pi = PendingIntent.getActivities(HomePageActivity.this, 0, new Intent[]{intent}, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(pi);
                //show content
                Notification notification = builder.build();
                manager.notify(1, notification);
            }
        });
    }

    public void createNotificationChannel() {
        // This must be called early because it must be called before a notification is sent.
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
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


}
