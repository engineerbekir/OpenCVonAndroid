package com.ardic.opencvapplication;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.android.LoaderCallbackInterface;

public class OpenCvCamera extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG="MainActivity";
    Mat mRGB;
    Mat mRGBT;
    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface.SUCCESS:{
                    Log.i(TAG,"onManagerConnected :Opencv Loaded");
                    cameraBridgeViewBase.enableView();
                }break;

                default:{
                    super.onManagerConnected(status);
                }break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(OpenCvCamera.this,new String[]{Manifest.permission.CAMERA},1);
        setContentView(R.layout.activity_open_cv_camera);

        cameraBridgeViewBase=(CameraBridgeViewBase) findViewById(R.id.camera_surface);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraBridgeViewBase.setCameraPermissionGranted();
                }
                else {
                    //as
                }
                }
                return;

            }
        }

    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug()){
          Log.d(TAG,"onResume: OpenCV initialized");
          baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            Log.d(TAG,"onResume : OpenCV not initialized");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this,baseLoaderCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    public void onCameraViewStopped() {
        mRGB.release();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRGB=new Mat(height,width, CvType.CV_8UC4);
        mRGBT=new Mat(height,width,CvType.CV_8UC1);
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRGB=inputFrame.rgba();
        mRGBT=inputFrame.gray();

        return mRGB;
    }
}
























