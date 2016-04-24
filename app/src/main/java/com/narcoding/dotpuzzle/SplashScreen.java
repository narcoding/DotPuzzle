package com.narcoding.dotpuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

/**
 * Created by Naim on 17.04.2016.
 */
public class SplashScreen extends Activity {

    RelativeLayout pnlSplash;

    private void init(){
        pnlSplash= (RelativeLayout) findViewById(R.id.pnlSplash);
        pnlSplash.setBackgroundResource(R.drawable.zemin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

        Thread thread = new Thread() {

            @Override
            public void run() {

                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException e) {

                } finally {

                    finish();

                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), OyunSec.class);
                    startActivity(intent);
                    finish();


                }

            }
        };

        thread.start();


    }
}
