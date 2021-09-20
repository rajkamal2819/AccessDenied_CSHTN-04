package com.Hackthon.botshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.RecognizeWithWebsocketsOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.speech_to_text.v1.websocket.BaseRecognizeCallback;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;

public class ChatBot extends AppCompatActivity {

    StreamPlayer streamPlayer=new StreamPlayer();
    private Context context;
    Assistant watsonAssistant;
    private TextToSpeech textToSpeech;
    private SpeechToText speechService;
    private MicrophoneHelper microphoneHelper;
   private ArrayList messageArrayList;
   private RecyclerView recyclerView;
    private Adapter adapter;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int RECORD_REQUEST_CODE = 101;
    private boolean permissionToRecordAccepted = false;
    private EditText inputMessage;
    private boolean initialRequest;
    private static String TAG="MainActivity";
    private boolean listening=false;
    private ImageButton btnSend;
    private ImageButton btnRecord;
    private MicrophoneInputStream capture;



    private void createServices(){
            watsonAssistant=new Assistant("2021-06-14",new IamAuthenticator(context.getString(R.string.assistant_apikey)));
            watsonAssistant.setServiceUrl(context.getString(R.string.assistant_url));


    textToSpeech=new com.ibm.watson.text_to_speech.v1.TextToSpeech(new IamAuthenticator(context.getString(R.string.TTS_apikey)));
            textToSpeech.setServiceUrl(context.getString(R.string.TTS_url));


            speechService = new SpeechToText(new IamAuthenticator(context.getString(R.string.STT_apikey)));
            speechService.setServiceUrl(context.getString(R.string.STT_url));
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

             //this is changes made by me and for si

        context=getApplicationContext();

        //creating a message arraylist to store messages and providing the adapter class with the messagearraylist as object for the constructor

        messageArrayList=new ArrayList<>();
        adapter=new Adapter(messageArrayList);


        //Small helper class to sit in between the client and the more in-depth microphone classes
        microphoneHelper=new MicrophoneHelper(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);

        //usages doubt
        linearLayoutManager.setStackFromEnd(true);

        //setting the recycler view with a linear layout fashion
        recyclerView.setLayoutManager(linearLayoutManager);
        //providing animations
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        //initialising the textmessage as an empty string and the initial request as true and changing it accordingly to switch between user and bot;
        this.inputMessage.setText("");
        this.initialRequest = true;

        checkSelfAudioPermission();

        //method to handle the touch listeners on the the recycler view model
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                BotMessage audioMessage=(BotMessage)messageArrayList.get(position);
                if(audioMessage!=null && !audioMessage.getMessage().isEmpty()){
                    new SayTask().execute(audioMessage.getMessage());
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                recordMessage();
            }
        }));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection()) {
                    sendMessage();
                }
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordMessage();
            }
        });

        createServices();
        sendMessage();
    }

    private void sendMessage() {
     final String inputMessage=this.inputMessage.getText().toString().trim();




    }

            //method returns true or false based on users connection status
    private boolean checkInternetConnection() {

        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();

        boolean isConnected=activeNetwork!=null && activeNetwork.isConnectedOrConnecting();

        // Check for network connections
        if (isConnected) {
            return true;
        } else {
            Toast.makeText(this, " No Internet Connection available ", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    ;





    private void recordMessage() {
        if (!listening){
            capture=microphoneHelper.getInputStream(true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //continue from here
                        speechService.recognizeUsingWebSocket(getRecognizeOptions(capture),new MicrophoneRecognizableDelegate());
                    }
                    catch (Exception e){

                    }
                }
            }).start();
            listening=true;
            Toast.makeText(context, "Listening....continue talking", Toast.LENGTH_SHORT).show();
        }

        else {
            try {
                microphoneHelper.closeInputStream();
                listening = false;
                Toast.makeText(this, "Stopped Listening....Click to Start", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    //Private Methods - Speech to Text
    private RecognizeWithWebsocketsOptions getRecognizeOptions(InputStream audio) {
        //changed the code over here
        return new RecognizeWithWebsocketsOptions.Builder()
                .audio(audio)
                .contentType(ContentType.OPUS.toString())
                .model("en-US_BroadbandModel")
//                .interimResult(true)
                .inactivityTimeout(2000)
                .build();

    }

    //method to check if the audio record permission was granted at the install time or not
    private void checkSelfAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            Log.i(TAG,"Permission to record denied");
            makeAudioRequest();
        }
        else{
            Log.i(TAG,"Permission to record was already granted");
        }
    }

    private void makeAudioRequest() {

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MicrophoneHelper.REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    Log.i(TAG, "Permission has been granted by user");
                }
                return;
            }

            case MicrophoneHelper.REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission to record audio denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class MicrophoneRecognizableDelegate extends BaseRecognizeCallback {
        @Override
        public void onTranscription(SpeechRecognitionResults speechResults) {
            if (speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
                showMicText(text);
            }
        }
    }

    private void showError(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }


    private class SayTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {
            streamPlayer.playStream(textToSpeech.synthesize(new SynthesizeOptions.Builder()
            .text(strings[0])
            .voice(SynthesizeOptions.Voice.EN_GB_KATEV3VOICE)
            .accept(HttpMediaType.AUDIO_WAV)
            .build()).execute().getResult());

            return "Synthesised successfully";
        }
    }


    private void showMicText(String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                inputMessage.setText(text);
            }
        });
    }
    }


