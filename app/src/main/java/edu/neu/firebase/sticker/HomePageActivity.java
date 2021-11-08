package edu.neu.firebase.sticker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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



    }
}
