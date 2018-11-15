package com.example.ezast.tucyute;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Animation Variables declared
    public AnimationDrawable idleFaceMovement;
    public AnimationDrawable sleepFaceMovement;
    public AnimationDrawable energyBarMovement;
    public AnimationDrawable hungerBarMovement;
    public AnimationDrawable eatingMovement;
    public AnimationDrawable treatMovement;
    public AnimationDrawable talkFaceMovement;

    //Vibration variable
    Vibrator vibrate;

    //Age and treats / reward variables
    TextView ageValue;
    TextView treatValue;
    int age = 0;
    int treat = 0;
    private View view;
    Switch switch1;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Method for the background music while starting.
        final MediaPlayer mapSong = MediaPlayer.create(this, R.raw.mapmusic);
        //Play Music
        mapSong.start();
        mapSong.setVolume(100,100);
        mapSong.setLooping(true);

        //Method for the switch to toggle music on and off
        switch1 = (Switch) findViewById(R.id.togglebutton);

        //Method for Treat sound.
        final MediaPlayer treatSound = MediaPlayer.create(this, R.raw.treatsound);

        //Method for talking sounds. To be used upon opening the first time. See line ---216
        final MediaPlayer talkSound = MediaPlayer.create(this, R.raw.intro);

        //Vibrate method creation
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        //Create media player for sound fx.
        final MediaPlayer eatingSound = MediaPlayer.create(this, R.raw.tucyuteate);
        final MediaPlayer sleepSound = MediaPlayer.create(this, R.raw.sleeping);
        //final MediaPlayer treatSound = MediaPlayer.create( this, R.raw.treatsound); ---see line 286

        //Display the counter as the text view next to the clock.
        ageValue = findViewById(R.id.agetext);

        //Display the treats as the text view next to the treat candy icon.
        treatValue = findViewById(R.id.treatText);

        // Treat animation flying across screen.
        final ImageView treatsFlying = findViewById(R.id.treats);
        treatsFlying.setBackgroundResource(R.drawable.treatanimation);
        treatMovement = (AnimationDrawable) treatsFlying.getBackground();

        // Rest Bar Decrease over time animation
        final ImageView energyLevel = findViewById(R.id.energyBar);
        energyLevel.setBackgroundResource(R.drawable.energybaranimation);
        energyBarMovement = (AnimationDrawable) energyLevel.getBackground();

        energyBarMovement.start();

        // Hunger Bar Decrease over time animation
        final ImageView hungerLevel = findViewById(R.id.hungerBar);
        hungerLevel.setBackgroundResource(R.drawable.hungerbaranimation);
        hungerBarMovement = (AnimationDrawable) hungerLevel.getBackground();

        //Start hunger bar drop animation
        hungerBarMovement.start();

        //idle face movement
        final ImageView face = findViewById(R.id.mainface);
        face.setBackgroundResource(R.drawable.idlefaceanimation);
        idleFaceMovement = (AnimationDrawable) face.getBackground();

        idleFaceMovement.start();

        //Creates eating animation
        final ImageView eating = findViewById(R.id.mainface);
        eating.setBackgroundResource(R.drawable.eatinganimation);
        eatingMovement = (AnimationDrawable) eating.getBackground();

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    mapSong.pause();
                    Toast.makeText(getApplicationContext(), "Music Off", Toast.LENGTH_SHORT).show();
                }else{
                    mapSong.start();
                    Toast.makeText(getApplicationContext(), "Music On", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // -------down below on line 155 shows the start of the idle face movements.

        //Feed Button
        final FloatingActionButton fab1 = findViewById(R.id.feed);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stops the idle face animation
                idleFaceMovement.stop();
                final ImageView mainFace = findViewById(R.id.mainface);
                mainFace.setBackgroundResource(R.drawable.ic_blank);

                //Stops the sleep face animation
                sleepFaceMovement.stop();
                final ImageView sleepFace = findViewById(R.id.sleepface);
                sleepFace.setBackgroundResource(R.drawable.ic_blank);

                    //Start quick vibrate on press.
                //vibrate.vibrate(20);

                //starts eating animation
                eatingMovement.stop();
                eatingMovement.start();

                //Play the sound of eating
                eatingSound.start();

                //Reset Hunger
                hungerBarMovement.stop();
                hungerBarMovement.start();
            }
        });

        //Rest Button
        final FloatingActionButton fab2 = findViewById(R.id.rest);
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View sleeping) {

                //Counts the click of the rest button and aging the pet.
                age++;
                ageValue.setText(Integer.toString(age));

                //Stops the idle face animation when the button is pressed.
                idleFaceMovement.stop();
                face.setBackgroundResource(R.drawable.ic_blank);

                //Creates the sleep animation.
                ImageView sleepface = findViewById(R.id.sleepface);
                sleepface.setBackgroundResource(R.drawable.sleepanimation);
                sleepFaceMovement = (AnimationDrawable) sleepface.getBackground();

                //Start the sleeping sound upon press.
                sleepSound.start();

                //Start the sleeping animation
                sleepFaceMovement.start();
                energyBarMovement.stop();

                //Test if age is a certain value, then give more treats
                //If age is a certain value, the background will change as the pet ages.
                ageValue.setText(Integer.toString(age));

                if ((age == 5)){

                    //Play the eating sound
                    //treatSound.start();
                    Toast.makeText(getApplicationContext(), "5 Years Old", Toast.LENGTH_SHORT).show();

                    //Adding treats upon sleep.
                    treatValue.setText(Integer.toString(treat));
                    int i = treat + 5;
                    treat = i;
                    treatMovement.stop();
                    treatMovement.start();

                    //Set the background
                    final ImageView backgroundMain1 = findViewById(R.id.background2);
                    backgroundMain1.setVisibility(View.GONE);

                    //Clears the sleep face animation when the button is pressed.
                    final ImageView sleepFace = findViewById(R.id.sleepface);
                    sleepFace.setBackgroundResource(R.drawable.ic_blank);

                    //Starts the talking face animation again
                    final ImageView talk = findViewById(R.id.mainface);
                    talk.setBackgroundResource(R.drawable.talkinganimation);
                    talkFaceMovement = (AnimationDrawable) talk.getBackground();
                    talkFaceMovement.start();

                    //Start the talking sounds
                    talkSound.start();

                    //Starts the idle face animation again
                    final ImageView face = findViewById(R.id.mainface);
                    idleFaceMovement = (AnimationDrawable) face.getBackground();

                } else if (age > 5){
                    //Adding treats upon touch.
                    treatValue.setText(Integer.toString(treat));
                    int i = treat + 1;
                    treat = i;
                    treatMovement.stop();
                    treatMovement.start();
                }

                if ((age == 10)) {

                    //Change the background
                    final ImageView backgroundMain2 = findViewById(R.id.background4);
                    backgroundMain2.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "You reached 10 Years!", Toast.LENGTH_SHORT).show();
                }

                if ((age == 20)) {

                    //Change the background
                    final ImageView backgroundMain3 = findViewById(R.id.background5);
                    backgroundMain3.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "You reached adulthood!", Toast.LENGTH_SHORT).show();

                }
            }
            });
        //Study Button
        FloatingActionButton fab3 = findViewById(R.id.study);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Nothing happens yet! Soon to come!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        //House Button
        FloatingActionButton fab4 = findViewById(R.id.home);
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent houseIntent = new Intent(getApplicationContext(), HouseActivity.class);

                startActivity(houseIntent);
            }
        });
        //Map button
        FloatingActionButton fab5 = findViewById(R.id.map);
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View map) {
                Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(mapIntent);
            }
        });
        //Wake button
        FloatingActionButton fab6 = findViewById(R.id.wake);
        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Clears the sleep face animation
                final ImageView sleepFace = findViewById(R.id.sleepface);
                sleepFace.setBackgroundResource(R.drawable.ic_blank);

                //Starts the idle face animation again
                final ImageView face = findViewById(R.id.mainface);
                face.setBackgroundResource(R.drawable.idlefaceanimation);
                idleFaceMovement = (AnimationDrawable) face.getBackground();
                idleFaceMovement.start();
                energyBarMovement.start();
            }
        });
    }
//Override to make the idle face animations start as soon as program launches and loops.

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void touch(View view){
        //Method for Treat sound.
        //final MediaPlayer treatSound = MediaPlayer.create( this, R.raw.treatsound);

        //Play the eating sound
        //treatSound.start();

        //Adding treats upon touch.
        //treatValue.setText(Integer.toString(treat));
        //treat++;
        //treatMovement.stop();
        //treatMovement.start();

    }
}