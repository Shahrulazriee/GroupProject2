package com.example.groupproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userprofile extends AppCompatActivity {

    DatabaseReference databaseUsers;
    FirebaseAuth auth;
    TextView Name, Email, Latitude, Longitude;
    String names, emails, longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        databaseUsers = FirebaseDatabase.getInstance("https://groupproject2-3beab-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("userName");

        //authentication
        auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail();
        Name = (TextView) findViewById(R.id.textView2);
        Email = (TextView) findViewById(R.id.textView3);
//        Longitude = (TextView) findViewById(R.id.textView25);
//        Latitude = (TextView) findViewById(R.id.textView26);
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keyID : snapshot.getChildren()) {
                    if (keyID.child("email").getValue().equals(email)) {
                        names = keyID.child("user").getValue().toString();
                        emails = keyID.child("email").getValue().toString();
//                        emails = keyID.child("latitude").getValue().toString();
//                        emails = keyID.child("longitude").getValue().toString();
                        Name.setText("Name: " + names);
                        Email.setText("Email: " + emails);
//                        Latitude.setText("Latitude: " + latitude);
//                        Longitude.setText("Latitude: " + longitude);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "error occurred", Toast.LENGTH_LONG);
            }
        });
    }
}
