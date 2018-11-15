package com.example.ezast.tucyute;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    //Canvas
    private int canvasWidth;
    private int canvasHeight;

    // TuCyute
    //private Bitmap tucute;

    private Bitmap tucute[] = new Bitmap[2];
    private int tucuteX = 10;
    private int tucuteY;
    private int tucuteSpeed;

    // Background
    private Bitmap bgImage;

    //Life
    private Bitmap life[] = new Bitmap[2];

    // Status Check
    private boolean touch_flg = false;


    public GameView(Context context) {
        super(context);

        tucute[0] = BitmapFactory.decodeResource(getResources(), R.drawable.tucyuteside1);
        tucute[1] = BitmapFactory.decodeResource(getResources(), R.drawable.tucyuteside2);


        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);


        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);

        //First position
        tucuteY = 500;
    }

    @Override
    protected void onDraw(Canvas canvas){

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bgImage, -400, 0, null);

        //Tucute
        int minTucuteY = tucute[0].getHeight();
        int maxTucuteY = canvasHeight - tucute[0].getHeight() * 3;

        tucuteY += tucuteSpeed;
        if (tucuteY < minTucuteY) tucuteY = minTucuteY;
        if (tucuteY > maxTucuteY) tucuteY = maxTucuteY;

        tucuteSpeed += 2;

        if (touch_flg){
            //bounce animation
            canvas.drawBitmap(tucute[1], tucuteX, tucuteY, null);
            touch_flg = false;

        } else {
            canvas.drawBitmap(tucute[0], tucuteX, tucuteY, null);
        }


        canvas.drawBitmap(life[0], 860, 5, null);
        canvas.drawBitmap(life[0], 920, 5, null);
        canvas.drawBitmap(life[1], 980, 5, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
            touch_flg = true;
            tucuteSpeed = -20;

        }
        return true;
    }
}
