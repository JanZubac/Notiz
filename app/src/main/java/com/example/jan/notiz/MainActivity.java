package com.example.jan.notiz;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
<<<<<<< HEAD
=======
import android.content.DialogInterface;
>>>>>>> origin/master
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView l;
    List<String> notifications;
    ArrayList<String> notification;
    ArrayList<ArrayList<String>> toSend;
    ArrayAdapter<String> arrayAdapter;
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        l = findViewById(R.id.list);
        notifications = new ArrayList<String>();
        toSend = new ArrayList<ArrayList<String>>();
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                notifications);
        l.setAdapter(arrayAdapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // HÅRDKODAD ATT SKICKA ENBART EN STRÄNG FRÅN SENAST TILLAGDA NOTISEN. FUNKAR BARA FÖR 1 NOTIS
                Intent intent = new Intent(getApplicationContext(), ItemListPopUpActivity.class);
                intent.putExtra("detail", notification.get(1));
                startActivity(intent);
            }
        });

        TextView tv = (TextView) findViewById(R.id.textview);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.addicon);

<<<<<<< HEAD
        int size = getIntent().getIntExtra("theSize", 0);
        if(size > 0) {
            for (int j = 0; j < size; ++j) {
                StringBuilder sb = new StringBuilder();
                sb.append("notification");
                sb.append(j);
                toSend.add(getIntent().getStringArrayListExtra(sb.toString()));
            }
            for(ArrayList <String> l: toSend) {
                notifications.add(l.get(0));
            }
        }
=======

>>>>>>> origin/master

        /*
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,  "main")
                .setSmallIcon(R.drawable.exl)
                .setContentTitle("Bra jobbat!")
                .setContentText("Du startade nyss världens bästa applikation")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

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
            notMan.notify(0, mBuilder.build());
        }
        */

        FloatingActionButton mapButton = findViewById(R.id.karta);
        fab.setImageResource(R.drawable.addicon);

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("BEFORE: ON-ACTIVITY-RESULT FOR NOTIFICATION ACTIVITY");
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                notification = data.getStringArrayListExtra("notificationArray");
                toSend.add(notification);
                notifications.add(notification.get(0));
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }





    public void sendCoordinates(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        vibe.vibrate(80);
        int i = 0;
        for(ArrayList<String> l: toSend) {
            StringBuilder sb = new StringBuilder();
            sb.append("array");
            sb.append(i);
            intent.putStringArrayListExtra(sb.toString(), l);
            i++;
        }
        //intent.putStringArrayListExtra("notArray", notification); //FUNKAR ENDAST OM MAN GÅR DIREKT FRÅN NOT -> MAIN -> MAP
        intent.putExtra("int", i);

        setResult(RESULT_OK, intent);
        startActivityForResult(intent, 2);

        finish();

       // System.out.println("SENDING COORDINATES");
    }


    public void enterNotificationActivity(View view) {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        vibe.vibrate(80);
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