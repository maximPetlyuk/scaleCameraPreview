package com.onix.scalecamerapreview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.onix.scalecamerapreview.camera.CameraPreview;

public class MainActivity extends AppCompatActivity {

    private CameraPreview mCameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCameraPreview.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mCameraPreview.pause();
    }
}
