package com.example.jan.notiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemListPopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_pop_up);

        Intent intent = getIntent();
        String val = intent.getStringExtra("detail");
        TextView tv = (TextView) findViewById(R.id.textview);
        tv.setText(val);
    }
}
