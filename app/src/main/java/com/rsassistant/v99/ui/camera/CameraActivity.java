package com.rsassistant.v99.ui.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.*;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.common.util.concurrent.ListenableFuture;
import com.rsassistant.v99.R;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewView;
    private ImageButton captureButton, switchButton;
    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;
    private int lensFacing = CameraSelector.LENS_FACING_BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        previewView = findViewById(R.id.previewView);
        captureButton = findViewById(R.id.captureButton);
        switchButton = findViewById(R.id.switchButton);
        cameraExecutor = Executors.newSingleThreadExecutor();

        if (hasCameraPermission()) startCamera();
        else ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);

        captureButton.setOnClickListener(v -> takePhoto());
        switchButton.setOnClickListener(v -> {
            lensFacing = lensFacing == CameraSelector.LENS_FACING_BACK ? 
                CameraSelector.LENS_FACING_FRONT : CameraSelector.LENS_FACING_BACK;
            startCamera();
        });
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                ProcessCameraProvider provider = future.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                imageCapture = new ImageCapture.Builder().build();
                CameraSelector selector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
                provider.unbindAll();
                provider.bindToLifecycle(this, selector, preview, imageCapture);
            } catch (Exception e) { e.printStackTrace(); }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (imageCapture == null) return;
        String name = "RS_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new java.util.Date()) + ".jpg";
        File file = new File(getExternalFilesDir(null), name);
        imageCapture.takePicture(new ImageCapture.OutputFileOptions.Builder(file).build(), cameraExecutor,
            new ImageCapture.OnImageSavedCallback() {
                @Override public void onImageSaved(ImageCapture.OutputFileResults results) {
                    runOnUiThread(() -> Toast.makeText(CameraActivity.this, "Photo saved: " + name, Toast.LENGTH_SHORT).show());
                }
                @Override public void onError(ImageCaptureException e) {
                    runOnUiThread(() -> Toast.makeText(CameraActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (cameraExecutor != null) cameraExecutor.shutdown();
    }
}
