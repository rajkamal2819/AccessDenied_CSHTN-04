package com.Hackthon.botshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfile extends AppCompatActivity {

    LinearLayout editProfile;
    LinearLayout signOut;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();


        editProfile = (LinearLayout) findViewById(R.id.edit_user_profile_layout);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this,EditProfile.class);
                startActivity(i);
            }
        });

        signOut = findViewById(R.id.sign_out_txt_layout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               auth.signOut();
                Intent i = new Intent(UserProfile.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}