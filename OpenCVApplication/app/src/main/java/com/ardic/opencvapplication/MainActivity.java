package com.ardic.opencvapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {



//    private static String TAG = "MainActivity";
//    static
//    {
//        if (OpenCVLoader.initDebug())
//        {
//            Log.d(TAG,"OpenCV installed successfully");
//        }
//        else
//        {
//            Log.d(TAG,  "OpenCV not installed");
//        }
//    }

    private Button open_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open_camera=findViewById(R.id.open_camera);
        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OpenCvCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });



    }
}