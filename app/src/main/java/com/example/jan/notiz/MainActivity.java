package com.example.jan.notiz;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView l;
    List<String> notifications;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l = (ListView) findViewById(R.id.list);
        notifications = new ArrayList<String>();
        setUpNotifications();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.addicon);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,  "main")
                .setSmallIcon(R.drawable.exl)
                .setContentTitle("Bra jobbat!")
                .setContentText("Du startade nyss världens bästa applikation")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        int id = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            CharSequence name = "Comm channel";
            String description = "Notification channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("main", name, importance);
            mChannel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);

            NotificationManagerCompat notMan = NotificationManagerCompat.from(this);


            notificationManager.createNotificationChannel(mChannel);
            notMan.notify(id++, mBuilder.build());
        }




        FloatingActionButton mapButton = (FloatingActionButton) findViewById(R.id.karta);
        fab.setImageResource(R.drawable.addicon);
    }

    private void setUpNotifications() {

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                notifications );

        l.setAdapter(arrayAdapter);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                notifications.add(strEditText);

                arrayAdapter.notifyDataSetChanged();
            }
        }
    }



    public void enterNotificationActivity(View view) {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        startActivityForResult(intent, 1);
    }


    public void enterMap(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivityForResult(intent, 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}