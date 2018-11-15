package com.example.ezast.tucyute;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MapActivity extends AppCompatActivity {

    public AnimationDrawable mapMovement;

    public void goBack (View view) {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);

        //Map animation
        final ImageView mapView =  findViewById(R.id.cutemap);
        mapView.setBackgroundResource(R.drawable.mapanimation);
        mapMovement = (AnimationDrawable) mapView.getBackground();

        mapMovement.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}