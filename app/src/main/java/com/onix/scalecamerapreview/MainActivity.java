package com.onix.scalecamerapreview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.onix.scalecamerapreview.camera.CameraPreview;
import com.onix.scalecamerapreview.models.Size;
import com.onix.scalecamerapreview.utils.Logger;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int DEFAULT_PREVIEW_SIZE_INDEX = 0;
    private static final int REQUEST_CAMERA = 0;

    private CameraPreview mCameraPreview;
    private Spinner mSpinnerPreviewSize;
    private boolean justFlag;
    private int mCurrentPreviewSizePosition;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestCameraPermission();

        mCurrentPreviewSizePosition = DEFAULT_PREVIEW_SIZE_INDEX;

        mLayout = (RelativeLayout) findViewById(R.id.root_layout);

        mSpinnerPreviewSize = (Spinner) findViewById(R.id.spinner_preview_size);

        mCameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        mCameraPreview.setPreviewSizeIndex(DEFAULT_PREVIEW_SIZE_INDEX);
        mCameraPreview.addStateListener(new CameraPreview.StateListener() {
            @Override
            public void previewSized() {

            }

            @Override
            public void previewStarted() {
                justFlag = true;
                List<Size> mCameraSupportPreviewSizeList = mCameraPreview.getSupportedPreviewSizes();


                if (mCameraSupportPreviewSizeList == null) {
                    return;
                }

                PreviewSizeAdapter adapter = new PreviewSizeAdapter(MainActivity.this, mCameraSupportPreviewSizeList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                mSpinnerPreviewSize.setAdapter(adapter);
                mSpinnerPreviewSize.setSelection(mCurrentPreviewSizePosition);
                mSpinnerPreviewSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {

                        if (justFlag) {
                            justFlag = false;
                            return;
                        }

                        mCurrentPreviewSizePosition = position;
                        changePreviewSize(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }

            @Override
            public void previewStopped() {

            }

            @Override
            public void cameraError(Exception error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCameraPreview.resume();
    }

    public void changePreviewSize(int index) {
        mCameraPreview.pause();
        mCameraPreview.setPreviewSizeIndex(index);
        mCameraPreview.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mCameraPreview.pause();
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Snackbar.make(mLayout, "...",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA);
                        }
                    })
                    .show();
        } else {
            Logger.e(this, "CAMERA permission was NOT granted.");

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CAMERA) {
            // Received permission result for camera permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Snackbar.make(mLayout, "Permission was granted",
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mLayout, "Permission not granted",
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
