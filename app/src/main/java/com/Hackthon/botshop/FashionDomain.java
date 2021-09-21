package com.Hackthon.botshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Hackthon.botshop.AdapterModels.ChatAdapter;
import com.Hackthon.botshop.Models.MessagesModels;
import com.Hackthon.botshop.databinding.ActivityFashionBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class FashionDomain extends AppCompatActivity {

    ActivityFashionBinding binding;
    TextView receiverGroupChatName;
    TextView senderGroupChatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFashionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.chatbackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FashionDomain.this, DomainSections.class);
                startActivity(i);
            }
        });

        senderGroupChatName = findViewById(R.id.userName_sender_textview_group_chat);
        receiverGroupChatName = findViewById(R.id.userName_receiver_textview_group_chat);

        String senderId = FirebaseAuth.getInstance().getUid();
        binding.chatDetailsUserName.setText("Fashion Room");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final ArrayList<MessagesModels> messagesModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messagesModels, this);

        binding.chatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        firebaseDatabase.getReference().child("Fashion Room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesModels.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    MessagesModels models = snapshot1.getValue(MessagesModels.class);
                    messagesModels.add(models);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = binding.sendMessageEdittext.getText().toString();
                MessagesModels models = new MessagesModels(senderId, message);
                models.setTimestamp(new Date().getTime());
                binding.sendMessageEdittext.setText("");
                //models.setSenderName(firebaseUser.getDisplayName());

                firebaseDatabase.getReference().child("Fashion Room")
                        .push()
                        .setValue(models).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

            }
        });

        /*binding.sendArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String message = binding.sendMessageEdittext.getText().toString();
                MessagesModels models = new MessagesModels(senderId, message);
                models.setTimestamp(new Date().getTime());
                binding.sendMessageEdittext.setText("");

                *//*firebaseDatabase.getReference().child("Chats")
                        .child(senderRoom)
                        .push()
                        .setValue(messagesModels1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        database.getReference().child("Chats")
                                .child(receiverRoom)
                                .push()
                                .setValue(messagesModels1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });*//*


                firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                               String senderRoom = firebaseUser.getUid() + dataSnapshot.getKey();
                               firebaseDatabase.getReference().child(senderRoom){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });*/



     }


}