package com.example.jan.notiz;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private LocationResult locationResult;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private FloatingActionButton mFab;
    private LocationManager lm;
    ArrayList<ArrayList<String>> notifications;
    TextToSpeech tts;
    int nbrNotifications = 0;
    ArrayList<ArrayList<String>> list;
    int j;
    int id = 0;
    ArrayList<LatLng> markerPositions;
    float[] results;
    Vibrator vibe;
    boolean updatePos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        updatePos = false;
        tts  =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
        setContentView(R.layout.activity_maps);
        vibe = (Vibrator) MapsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mFab = (FloatingActionButton) findViewById(R.id.myloc);
        updatePos();
        results = new float[1];
        list = new ArrayList<ArrayList<String>>();

        notifications = new ArrayList<ArrayList<String>>();
        markerPositions = new ArrayList<LatLng>();
        int size = getIntent().getIntExtra("int", 0);
        for(int i = 0; i < size; ++i) {
            StringBuilder sb = new StringBuilder();
            sb.append("array");
            sb.append(i);
            list.add(getIntent().getStringArrayListExtra(sb.toString()));
            LatLng adr = getLocationFromAddress(this, list.get(j).get(2));
            markerPositions.add(adr);
        }

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


    public void showMarkers(View view) {
        //ArrayList<String> list = getIntent().getStringArrayListExtra("notArray");
        vibe.vibrate(80);
        int size = getIntent().getIntExtra("int", 0);
        for(j = 0; j < size; ++j) {
            //StringBuilder sb = new StringBuilder();
            //sb.append("array");
            //sb.append(j);
            //list.add(getIntent().getStringArrayListExtra(sb.toString()));
            LatLng adr = getLocationFromAddress(this, list.get(j).get(2));
            //markerPositions.add(adr);
            if (adr != null) {
                addMarker(adr);
            }
        }
        /*
        if(list != null) {
            notifications.add(list);
            LatLng adr = getLocationFromAddress(this, list.get(2)); // 2 is the address

            if (adr != null) {
                addMarker(adr);
            }
            nbrNotifications++;
        } */
    }


    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                notifications.add(data.getStringArrayListExtra("notArray"));
                addMarker(getLocationFromAddress(this, notifications.get(nbrNotifications).get(2))); //2 is address
            }
        }
        nbrNotifications++;
        System.out.println("ON-ACTIVITY-RESULT FOR MAPS ACTIVITY");
    }
    */



    private void addMarker(LatLng pos) {
        //mMap.addMarker(new MarkerOptions().position(pos).title(notifications.get(nbrNotifications).get(0)));
        mMap.addMarker(new MarkerOptions().position(pos).title(list.get(j).get(0)));
        //markerPositions.add(pos);
    }

    public void goBack(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        vibe.vibrate(80);
        int i = 0;
        if(list != null) {
            for (ArrayList<String> l : list) {
                StringBuilder sb = new StringBuilder();
                sb.append("notification");
                sb.append(i);
                intent.putStringArrayListExtra(sb.toString(), l);
                i++;
            }
            intent.putExtra("theSize", i);
        }
        startActivity(intent);
    }


    private void updatePos() {
        locationResult = new LocationResult() {
            public void gotLocation(Location location) {
                // Location found!
                //System.out.println("Got here");
                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));
                //System.out.println("Got here");
            }
        };

        getLocation(this, locationResult);
    }



    public void getLocation(View view) {
        vibe.vibrate(80);
        updatePos();
    }


    public boolean getLocation(Context context, LocationResult result) {

        locationResult = result;
        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        //don't start listeners if no provider is enabled
        if (!gps_enabled && !network_enabled)
            return false;


        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);

        if(network_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            //System.out.println("I AM CALLED");
            if (mMap != null) {
                //mMap.clear();
                int index = 0;
                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                for(LatLng ll: markerPositions) {
                    Location.distanceBetween(ll.latitude, ll.longitude, location.getLatitude(), location.getLongitude(), results);
                    if (results[0] < 20) {
                        // if(list.size() <= index) {
                        if (!list.get(index).get(3).equals("isNotified")) {
                            sendNotice(list.get(index).get(0), list.get(index).get(1));
                            vibe.vibrate(100);
                            StringBuilder sb = new StringBuilder();
                            String title = list.get(index).get(0);
                            String text = list.get(index).get(1);
                            sb.append(title);
                            sb.append(text);
                            tts.setSpeechRate(1);
                            tts.speak(sb.toString(), TextToSpeech.QUEUE_FLUSH, null);
                            list.get(index).set(3, "isNotified");
                            NotificationPopup popup = new NotificationPopup();
                            popup.show(getFragmentManager(), "Notification done?");
                            //}
                        }
                        index++;
                    }
                }
                   if(updatePos == false) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));
                        updatePos = true;
                    }
            }
            //lm.removeUpdates(this);
            //lm.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };




    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (mMap != null) {
                //mMap.clear();
                Bundle args = new Bundle();
                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                int index = 0;
                for(LatLng ll: markerPositions) {
                    Location.distanceBetween(ll.latitude, ll.longitude, location.getLatitude(), location.getLongitude(), results);
                    if(results[0] < 70) {
                        // if(list.size() <= index) {
                        if (!list.get(index).get(3).equals("isNotified")) {
                            sendNotice(list.get(index).get(0), list.get(index).get(1));
                            vibe.vibrate(100);
                            StringBuilder sb = new StringBuilder();
                            String title = list.get(index).get(0);
                            String text = list.get(index).get(1);
                            sb.append(title);
                            sb.append(text);
                            tts.setSpeechRate(1);
                            tts.speak(sb.toString(), TextToSpeech.QUEUE_FLUSH, null);
                            list.get(index).set(3, "isNotified");
                            args.putString("title", list.get(index).get(0));
                            NotificationPopup popup = new NotificationPopup();
                            popup.setArguments(args);
                            popup.show(getFragmentManager(), "Notification done?");
                            //}
                        }
                        index++;

                    }
                }
                if(updatePos == false) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));
                    updatePos = true;
                }
            }
            //lm.removeUpdates(this);

        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };



    public void onMapReady(final GoogleMap googleMap) {
        this.mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        mMap.setMyLocationEnabled(true);
    }


    public static abstract class LocationResult{
        public abstract void gotLocation(Location location);
    }

    public void sendNotice(String title, String text) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,  "main")
                .setSmallIcon(R.drawable.exl)
                .setContentTitle(title)
                .setContentText(text)
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
            notMan.notify(id, mBuilder.build());
            id++;
        }
    }

}
