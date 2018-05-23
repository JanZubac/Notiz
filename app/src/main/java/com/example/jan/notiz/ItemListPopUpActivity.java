package com.example.jan.notiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListPopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_pop_up);

        Intent intent = getIntent();
        StringBuilder sb = new StringBuilder();
        int pos = intent.getIntExtra("pos", 0);
        sb.append("item");
        sb.append(pos);

        ArrayList<String> val = intent.getStringArrayListExtra(sb.toString());
        TextView tv = (TextView) findViewById(R.id.textview);
        String title = val.get(0);
        String text = val.get(1);
        String address = val.get(2);

        tv.setText("Title: " + title + "\n" + "Text: " + text + "\n" + "Address: " + address);
        tv.setTextSize(24);
        tv.setTextColor(Color.BLACK);
    }
}
