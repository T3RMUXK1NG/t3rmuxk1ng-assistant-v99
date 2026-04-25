package com.rsassistant.v99.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.rsassistant.v99.Constants;
import com.rsassistant.v99.R;
import com.rsassistant.v99.ui.main.MainActivity;

public class VoiceRecognitionService extends Service {
    private static VoiceRecognitionService instance;
    private boolean isRecording = false;

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        startForeground();
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override public IBinder onBind(Intent intent) { return null; }

    @Override public void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    private void startForeground() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        
        Notification notification = new NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_SERVICE)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("Listening for commands")
            .setSmallIcon(R.drawable.ic_mic)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build();
        
        startForeground(Constants.NOTIFICATION_ID_SERVICE, notification);
    }

    public static VoiceRecognitionService getInstance() { return instance; }
}
