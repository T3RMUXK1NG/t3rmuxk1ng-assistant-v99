package com.t3rmuxk1ng.v99.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.List;

public class PermissionHelper {
    public static final int REQUEST_ALL_PERMISSIONS = 1000;

    private static final String[] CORE_PERMISSIONS = {
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS
    };

    public static boolean hasCorePermissions(Context context) {
        for (String p : CORE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public static void requestAllPermissions(Activity activity, PermissionCallback callback) {
        ActivityCompat.requestPermissions(activity, CORE_PERMISSIONS, REQUEST_ALL_PERMISSIONS);
    }

    public static boolean hasMicrophonePermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) 
            == PackageManager.PERMISSION_GRANTED;
    }

    public interface PermissionCallback {
        void onAllGranted();
        void onSomeDenied(List<String> deniedPermissions);
    }
}
