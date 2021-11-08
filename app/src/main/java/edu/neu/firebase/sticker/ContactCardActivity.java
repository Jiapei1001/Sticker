package edu.neu.firebase.sticker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ContactCardActivity extends AppCompatActivity {
    ArrayList<User> userList = new ArrayList<>();
    String username = getIntent().getStringExtra("USERNAME");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_card);
    }

    public void onSendStickersClick(int position) {
        Intent intent = new Intent(ContactCardActivity.this, StickersActivity.class);
        startActivity(intent);
    }

    public void onChatHistoryClick(int position) {
        Intent intent = new Intent(ContactCardActivity.this, MsgHistoryActivity.class);
        startActivity(intent);
    }
}
