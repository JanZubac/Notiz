package com.example.jan.notiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    Button saveButton, mapButton;
    ImageButton backButton;
    EditText title, text, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        title = findViewById(R.id.title);
        text = findViewById(R.id.notificationText);
        address = findViewById(R.id.address);
        saveButton = findViewById(R.id.savebutton);
        backButton = findViewById(R.id.back);
    }

    public void saveNotification(View view) {
        Intent intent = new Intent();
        ArrayList<String> notification = new ArrayList<String>();
        notification.add(title.getText().toString());
        notification.add(text.getText().toString());
        notification.add(address.getText().toString());
        intent.putStringArrayListExtra("notificationArray", notification);
        //intent.putExtra("editTextValue", text.getText().toString());
        setResult(RESULT_OK, intent);
        //setContentView(R.layout.activity_main);
        finish();
    }

    public void goBack(View view) {
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(intent);

        //setContentView(R.layout.activity_main);

        finish();
    }

    public void clearTitle(View view) {
        title.setText("");
    }

    public void clearText(View view) {
        text.setText("");
    }

    public void clearAddress(View view) {
        address.setText("");
    }
}