package com.example.jan.notiz;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    Button saveButton, mapButton;
    ImageButton backButton;
    EditText title, text, address;
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibe = (Vibrator) NotificationActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        setContentView(R.layout.activity_notification);
        title = findViewById(R.id.title);
        text = findViewById(R.id.notificationText);
        address = findViewById(R.id.address);
       // saveButton = findViewById(R.id.savebutton);
        backButton = findViewById(R.id.back);
    }

    public void saveNotification(View view) {
        Intent intent = new Intent();
        vibe.vibrate(80);
        ArrayList<String> notification = new ArrayList<String>();
        notification.add(title.getText().toString());
        notification.add(text.getText().toString());
        notification.add(address.getText().toString());
        notification.add("notNotified");
        if(getLocationFromAddress(this, notification.get(2)) == null) {
            AddressPopup popup = new AddressPopup();
            popup.show(getFragmentManager(), "Invalid address");
            return;
        }
        intent.putStringArrayListExtra("notificationArray", notification);
        //intent.putExtra("editTextValue", text.getText().toString());
        setResult(RESULT_OK, intent);
        //setContentView(R.layout.activity_main);
        finish();
        System.out.println("SENDING NOTIFICATION DATA TO MAIN-ACTIVITY");
    }

    public void goBack(View view) {
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(intent);

        //setContentView(R.layout.activity_main);

        finish();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            return new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}