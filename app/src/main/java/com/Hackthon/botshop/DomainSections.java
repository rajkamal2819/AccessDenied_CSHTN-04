package com.Hackthon.botshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DomainSections extends AppCompatActivity {

    TextView fashion;
    TextView sports;
    TextView others;
    TextView tech;
    TextView chatBot;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domain_sections);

        fashion = findViewById(R.id.fashion_textView);
        sports = findViewById(R.id.sports_textView);
        others = findViewById(R.id.others_textView);
        tech = findViewById(R.id.tech_textView);
        chatBot = findViewById(R.id.chat_bot_textView);
        user = findViewById(R.id.user_profile_textView);

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
}