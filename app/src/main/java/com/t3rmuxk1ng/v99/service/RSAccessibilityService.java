package com.t3rmuxk1ng.v99.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.os.Build;

public class RSAccessibilityService extends AccessibilityService {
    private static RSAccessibilityService instance;

    @Override public void onCreate() { super.onCreate(); instance = this; }
    @Override public void onDestroy() { super.onDestroy(); instance = null; }
    @Override public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override public void onInterrupt() {}

    public static RSAccessibilityService getInstance() { return instance; }
    public static boolean isServiceEnabled() { return instance != null; }

    public boolean performTap(float x, float y) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false;
        Path path = new Path();
        path.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 100));
        return dispatchGesture(builder.build(), null, null);
    }

    public boolean performSwipe(float startX, float startY, float endX, float endY) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false;
        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 300));
        return dispatchGesture(builder.build(), null, null);
    }

    public boolean goBack() { return performGlobalAction(GLOBAL_ACTION_BACK); }
    public boolean goHome() { return performGlobalAction(GLOBAL_ACTION_HOME); }
    public boolean openRecents() { return performGlobalAction(GLOBAL_ACTION_RECENTS); }
    public boolean openNotifications() { return performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS); }
}
