package com.example.groupproject2;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    String names;
    EditText editTextName;
    Button buttonAdd;

    FirebaseAuth auth;
    FirebaseUser user;
    TextView Name;
    DatabaseReference databaseUsers;

    //for map
    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;
    TextView showLocation;
    LocationManager locationManager;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseUsers = FirebaseDatabase.getInstance("https://groupproject2-3beab-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("userName");

        //to access map permission
//        ActivityCompat.requestPermissions( this,
//                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        showLocation = findViewById(R.id.showLocation);
//        btnGetLocation = findViewById(R.id.btnGetLocation);
//        btnGetLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LocationManager nManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    OnGPS();
//                } else {
//                    getLocation();
//                }
//            }
//        });

        //to display user name fetch from firebase realtime database
        auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail();
        Name = (TextView) findViewById(R.id.textView2);
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keyID : snapshot.getChildren()) {
                    if (keyID.child("email").getValue().equals(email)) {
                        names = keyID.child("user").getValue().toString();
                        Name.setText("Welcome " + names);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "error occurred", Toast.LENGTH_LONG);
            }
        });
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    public void getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                ProfileActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                ProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
//            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (locationGPS != null) {
//                double lat = locationGPS.getLatitude();
//                double longi = locationGPS.getLongitude();
//                latitude = String.valueOf(lat);
//                longitude = String.valueOf(longi);
////                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
//
//                databaseUsers.addValueEventListener(new ValueEventListener() {
//                    auth = FirebaseAuth.getInstance();
//                    String email = auth.getCurrentUser().getEmail();
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot keyID : snapshot.getChildren()) {
//                            if (keyID.child("email").getValue().equals(email)) {
//                                //collect altitude and longitude to the databse
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getApplicationContext(), "error occurred", Toast.LENGTH_LONG);
//                    }
//                });
//            } else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public void openMap(View v) {
        //try to insert lat and long in the database when this function is called
//        getLocation();
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void openAbout(View v){
        Intent i = new Intent(this, AboutPage.class);
        startActivity(i);
    }
    public void openProfile(View v){
        Intent i = new Intent(this, Userprofile.class);
        startActivity(i);
    }
    public void signout(View v){
        auth.signOut();
        finish();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}