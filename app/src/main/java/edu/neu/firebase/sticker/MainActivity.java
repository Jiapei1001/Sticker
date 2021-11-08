package edu.neu.firebase.sticker;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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

            Button button1 = (Button) findViewById(R.id.button_login);
            button1.setOnClickListener(v -> {
                EditText simpleEditText = (EditText) findViewById(R.id.editUsername);
                String username = simpleEditText.getText().toString();
                if (username.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please enter user Name!  :)", Toast.LENGTH_SHORT).show();
                }
                else if (username.contains(".")||username.contains("//")) {
                    Toast.makeText(MainActivity.this, "Please do not enter . or //!  :)", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent1 = new Intent(MainActivity.this, HomePageActivity.class);
                    intent1.putExtra("USERNAME", username);
                    startActivity(intent1);
                }
            });
        }}