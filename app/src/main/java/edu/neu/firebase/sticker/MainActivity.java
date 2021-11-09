package edu.neu.firebase.sticker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase; //for insert database object value
    private DatabaseReference mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get user reference
        mUsers = mDatabase.child("users");

        Button button1 = findViewById(R.id.button_login);
        button1.setOnClickListener(v -> {
            System.out.println("Login button clicked");
            TextView usernameTextView = findViewById(R.id.editUsername);
            String username = usernameTextView.getText().toString().replaceAll("\\s", "");
            if (username.length() == 0) {
                Toast.makeText(MainActivity.this, "Please enter user Name", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent1 = new Intent(MainActivity.this, ContactHistoryActivity.class);
                intent1.putExtra("USERNAME", username);
                startActivity(intent1);
            }
        });
    }
}