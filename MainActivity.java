package com.example.securevideostreamer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securevideostreamer.specialToast.firebaseUtil;
import com.google.android.material.internal.EdgeToEdgeUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseUtil.isLoggedIn()){
                    startActivity(new Intent(getApplicationContext(), Playlist.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), phonenumberInterface.class));
                }
                finish();
            }
        },1000);
    }
}