package com.onix.scalecamerapreview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.onix.scalecamerapreview.camera.CameraPreview;
import com.onix.scalecamerapreview.models.Size;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int DEFAULT_PREVIEW_SIZE_INDEX = 0;

    private CameraPreview mCameraPreview;
    private Spinner mSpinnerPreviewSize;
    private boolean justFlag;
    private int mCurrentPreviewSizePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentPreviewSizePosition = DEFAULT_PREVIEW_SIZE_INDEX;

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
}
