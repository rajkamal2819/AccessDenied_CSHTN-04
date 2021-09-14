package com.Hackthon.botshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class UserProfile extends AppCompatActivity {

    LinearLayout editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        editProfile = (LinearLayout) findViewById(R.id.edit_user_profile_layout);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this,EditProfile.class);
                startActivity(i);
            }
        });

    }
}