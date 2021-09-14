package com.Hackthon.botshop;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DomainSections extends AppCompatActivity {

    CardView fashion;
    CardView sports;
    CardView others;
    CardView tech;
    CardView chatBot;
    CardView user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domain_sections);

        fashion = findViewById(R.id.fashion_domain);
        sports = findViewById(R.id.sports_domain);
        others = findViewById(R.id.other_domain);
        tech = findViewById(R.id.tech_domain);
        chatBot = findViewById(R.id.chat_bot_domain);
        user = findViewById(R.id.user_domain);


        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DomainSections.this,ChatBot.class);
                startActivity(i);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DomainSections.this,Sports.class);
                startActivity(i);
            }
        });

        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DomainSections.this,TechDomain.class);
                startActivity(i);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DomainSections.this,Others.class);
                startActivity(i);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DomainSections.this,UserProfile.class);
                startActivity(i);
            }
        });

        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DomainSections.this,FashionDomain.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}