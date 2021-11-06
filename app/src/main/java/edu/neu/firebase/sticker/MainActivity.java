package edu.neu.firebase.sticker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button_login);
        button1.setOnClickListener(v -> {
        System.out.println("Login button clicked");
        TextView usernameTextView = (TextView) findViewById(R.id.editUsername);
        String username = usernameTextView.getText().toString().replaceAll("\\s", "");

        if (username.length() == 0) {
            Toast.makeText(MainActivity.this, "Please enter user Name", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent1 = new Intent(MainActivity.this, HomePageActivity.class);
            intent1.putExtra("USERNAME", username);
            startActivity(intent1);
        }
    });
}}