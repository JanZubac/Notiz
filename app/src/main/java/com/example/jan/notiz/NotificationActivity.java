package com.example.jan.notiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;

public class NotificationActivity extends AppCompatActivity {

    Button saveButton, mapButton;
    ImageButton backButton;
    EditText t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        t = (EditText) findViewById(R.id.activity);
        saveButton = (Button) findViewById(R.id.savebutton);
        backButton = (ImageButton) findViewById(R.id.back);
    }

    public void saveNotification(View view) {
        Intent intent = new Intent();
        intent.putExtra("editTextValue", t.getText().toString());
        setResult(RESULT_OK, intent);
        setContentView(R.layout.activity_main);
        finish();
    }

    public void goBack(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
    }



    public void clearText(View view) {
        t.setText("");
    }
}