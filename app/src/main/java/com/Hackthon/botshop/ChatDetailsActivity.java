package com.Hackthon.botshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.Hackthon.botshop.databinding.ActivityChatDetailsBinding;

public class ChatDetailsActivity extends AppCompatActivity {

    private ActivityChatDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}