package com.rsassistant.v99.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rsassistant.v99.Constants;
import com.rsassistant.v99.R;
import com.rsassistant.v99.nlp.HinglishProcessor;
import com.rsassistant.v99.nlp.IntentClassifier;
import com.rsassistant.v99.service.AIAssistantService;
import com.rsassistant.v99.service.VoiceRecognitionService;
import com.rsassistant.v99.ui.camera.CameraActivity;
import com.rsassistant.v99.ui.settings.SettingsActivity;
import com.rsassistant.v99.util.PermissionHelper;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecognitionListener, TextToSpeech.OnInitListener {

    private TextView greetingText, statusText, voiceStatusText;
    private ImageButton voiceButton, cameraButton, settingsButton;
    private RecyclerView conversationRecyclerView;
    
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private HinglishProcessor hinglishProcessor;
    private IntentClassifier intentClassifier;
    private ConversationAdapter conversationAdapter;
    
    private boolean isListening = false;
    private boolean ttsInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        setupRecyclerView();
        
        textToSpeech = new TextToSpeech(this, this);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        
        hinglishProcessor = new HinglishProcessor(this);
        intentClassifier = new IntentClassifier(this);
        
        checkPermissions();
        updateGreeting();
    }

    private void initViews() {
        greetingText = findViewById(R.id.greetingText);
        statusText = findViewById(R.id.statusText);
        voiceStatusText = findViewById(R.id.voiceStatusText);
        voiceButton = findViewById(R.id.voiceButton);
        cameraButton = findViewById(R.id.cameraButton);
        settingsButton = findViewById(R.id.settingsButton);
        conversationRecyclerView = findViewById(R.id.conversationRecyclerView);
        
        voiceButton.setOnClickListener(v -> toggleVoiceRecognition());
        cameraButton.setOnClickListener(v -> startActivity(new Intent(this, CameraActivity.class)));
        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }

    private void setupRecyclerView() {
        conversationAdapter = new ConversationAdapter();
        conversationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        conversationRecyclerView.setAdapter(conversationAdapter);
        conversationAdapter.addMessage(new ConversationMessage(
            "Welcome to RS Assistant v99! How can I help you?", false));
    }

    private void checkPermissions() {
        if (!PermissionHelper.hasCorePermissions(this)) {
            PermissionHelper.requestAllPermissions(this, new PermissionHelper.PermissionCallback() {
                @Override public void onAllGranted() { startVoiceService(); }
                @Override public void onSomeDenied(java.util.List<String> denied) {}
            });
        }
    }

    private void startVoiceService() {
        Intent intent = new Intent(this, VoiceRecognitionService.class);
        startForegroundService(intent);
    }

    private void updateGreeting() {
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        String greeting = hour < 12 ? "Good Morning!" : hour < 17 ? "Good Afternoon!" : "Good Evening!";
        greetingText.setText(greeting);
    }

    private void toggleVoiceRecognition() {
        if (isListening) stopListening();
        else startListening();
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN");
        speechRecognizer.startListening(intent);
        isListening = true;
        voiceStatusText.setText("Listening...");
        voiceButton.setImageResource(R.drawable.ic_mic_active);
    }

    private void stopListening() {
        speechRecognizer.stopListening();
        isListening = false;
        voiceStatusText.setText("Tap to speak");
        voiceButton.setImageResource(R.drawable.ic_mic);
    }

    @Override public void onReadyForSpeech(Bundle params) { voiceStatusText.setText("Say something..."); }
    @Override public void onBeginningOfSpeech() {}
    @Override public void onRmsChanged(float rmsdB) {}
    @Override public void onBufferReceived(byte[] buffer) {}
    @Override public void onEndOfSpeech() { voiceStatusText.setText("Processing..."); }
    @Override public void onError(int error) { stopListening(); }
    
    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null && !matches.isEmpty()) {
            String text = matches.get(0);
            conversationAdapter.addMessage(new ConversationMessage(text, true));
            processVoiceInput(text);
        }
        stopListening();
    }

    @Override public void onPartialResults(Bundle partialResults) {}
    @Override public void onEvent(int eventType, Bundle params) {}

    private void processVoiceInput(String input) {
        String processed = hinglishProcessor.process(input);
        IntentClassifier.IntentResult intent = intentClassifier.classify(processed);
        
        if (intent.isActionable()) {
            executeAction(intent);
        } else {
            AIAssistantService.getAIResponse(input, new AIAssistantService.ResponseCallback() {
                @Override public void onResponse(String response) {
                    runOnUiThread(() -> {
                        conversationAdapter.addMessage(new ConversationMessage(response, false));
                        speak(response);
                    });
                }
                @Override public void onError(String error) {}
            });
        }
    }

    private void executeAction(IntentClassifier.IntentResult intent) {
        String action = intent.getAction();
        String target = intent.getTarget();
        switch (action) {
            case "camera": startActivity(new Intent(this, CameraActivity.class)); speak("Opening camera"); break;
            case "time": speak("The time is " + java.text.DateFormat.getTimeInstance().format(new java.util.Date())); break;
            case "date": speak("Today is " + java.text.DateFormat.getDateInstance().format(new java.util.Date())); break;
        }
    }

    private void speak(String text) {
        if (ttsInitialized) textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttsInitialized = true;
            textToSpeech.setLanguage(new Locale("en", "IN"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) speechRecognizer.destroy();
        if (textToSpeech != null) { textToSpeech.stop(); textToSpeech.shutdown(); }
    }
}
