package com.example.groupproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText e1, e2, e3;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseUsers;
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseUsers = FirebaseDatabase.getInstance("https://groupproject2-3beab-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("userName");
        setContentView(R.layout.activity_register);
        e1 = (EditText)findViewById(R.id.editText); //email
        e2 = (EditText)findViewById(R.id.editText2); //password
        e3 = (EditText)findViewById(R.id.editText3); //name
        mAuth = FirebaseAuth.getInstance();


//        realtime database
        FirebaseApp.initializeApp(this);


//        addNamebtn = (Button) findViewById(R.id.button3); //button sign up
    }

    //realtime database
    private void addName(){
        String name = e3.getText().toString().trim();
        String email = e1.getText().toString().trim();
        String currentTime = df.format(Calendar.getInstance().getTime());
        if(!TextUtils.isEmpty(name)){
            String id = databaseUsers.push().getKey();
            user user = new user(id, name, email, currentTime, null, null);
            databaseUsers.child(id).setValue(user);
            Toast.makeText(this, "Name updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    //authentication
    public void createUser(View v){
        addName();
        if(e1.getText().toString().equals("") && e2.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Blank not allowed", Toast.LENGTH_SHORT).show();
        }else{
//            String id = databaseUser.push().getKey();
//            String username = e3.getText().toString().trim();
//            user user = new user(id, username, email);
//            databaseUser.child(id).setValue(user);

            String email = e1.getText().toString();
            String password = e2.getText().toString();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"User created successfully",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(),"User could not be found",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}