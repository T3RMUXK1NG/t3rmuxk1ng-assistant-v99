package com.t3rmuxk1ng.v99.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.t3rmuxk1ng.v99.service.VoiceRecognitionService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent service = new Intent(context, VoiceRecognitionService.class);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                context.startForegroundService(service);
            else context.startService(service);
        }
    }
}
