package edu.neu.firebase.sticker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This is a ContactHistoryActivity to show the login user's contact.
 *
 * Ref 1: Read and Write Data on Android
 * https://firebase.google.com/docs/database/android/read-and-write
 */
public class ContactHistoryActivity extends AppCompatActivity implements ContactCardClickListener {
    private String username;
    private final ArrayList<User> contactList = new ArrayList();
    private ContactHistoryAdapter contactHistoryAdapter;
    private static final String TAG = "UserCount";

    public ContactHistoryActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_contact_history);

        this.username = this.getIntent().getStringExtra("USERNAME");

        // Sticker Statics button
        Button btnStickersStat = (Button)this.findViewById(R.id.btnStickersStat);
        btnStickersStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentStickersStat = new Intent(this, StickerStat.class);
//                startActivity(intentStickersStat);
            }
        });

        // Greeting sentence
        TextView greetingForCurrentUser = (TextView)this.findViewById(R.id.currentUserGreetingTextView);

        // Get the greeting sentence according to the current time
        String greeting = Utils.showGreetingWordsByCurrentTime();
        StringBuilder greetingInfo = new StringBuilder();
        greetingInfo.append(greeting).append(", ").append(this.username);

        greetingForCurrentUser.setText(greetingInfo);

        this.createRecyclerView();

        // Write data
        User user = new User(this.username, 4);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mUserReference = mDatabase.child("users");
        mUserReference.child(this.username).setValue(user);

        // Read data
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("Count ", "" + snapshot.getChildrenCount());

                contactList.clear();
                for (DataSnapshot userSnapShot : snapshot.getChildren()) {
                    // Get User object and use the values to update the UI
                    User contactUser = userSnapShot.getValue(User.class);

                    // Check if the contactUser is not current login user
                    if (!contactUser.getUsername().equals(username)) {
                        contactList.add(contactUser);
                    }
                }
                contactHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Getting User failed, log a message
                Log.e("The read failed: ", error.getMessage());
            }
        };
        mUserReference.addValueEventListener(userListener);

    }

    private void createRecyclerView() {
        LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.contactRecyclerView);
        recyclerView.setHasFixedSize(true);
        this.contactHistoryAdapter = new ContactHistoryAdapter(this.contactList, this);
        recyclerView.setAdapter(this.contactHistoryAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void onSendStickersClick(int position) {
        Intent intentSendStickerClick = new Intent(this, StickersActivity.class);
        intentSendStickerClick.putExtra("receiver", contactList.get(position).getUsername());
        intentSendStickerClick.putExtra("sender", username);

        startActivity(intentSendStickerClick);
    }

    public void onChatHistoryClick(int position) {
        Intent intentChatHistoryClick = new Intent(this, MsgHistoryActivity.class);
        intentChatHistoryClick.putExtra("receiver", contactList.get(position).getUsername());
        intentChatHistoryClick.putExtra("sender", username);

        startActivity(intentChatHistoryClick);
    }
}
