package com.narcoding.dotpuzzle;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Naim on 17.04.2016.
 */
public class OyunSec extends AppCompatActivity {

    LinearLayout pnlOyunSec;
    ImageButton btnClassic,btnArcade;
    TextView txtRecord;
    int r=0;

    private void init(){
        pnlOyunSec= (LinearLayout) findViewById(R.id.pnlOyunSec);
        pnlOyunSec.setBackgroundResource(R.drawable.zemin);
        btnClassic= (ImageButton) findViewById(R.id.btnClassic);
        btnArcade= (ImageButton) findViewById(R.id.btnArcade);
        btnClassic.setBackgroundResource(R.drawable.button_giris);
        btnArcade.setBackgroundResource(R.drawable.button_giris);
        txtRecord= (TextView) findViewById(R.id.txtRecord);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        r= sharedPreferences.getInt("arcsongecilenbolum", 0);

        txtRecord.setText("Record: "+r);



    }

    private void OnClick() {
        btnClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OyunSec.this,MainActivity.class);
                Bundle bundle= ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                startActivity(intent,bundle);
                finish();

            }
        });

        btnArcade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OyunSec.this, Arcade.class);
                Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                intent.putExtra("bolum", 1);
                startActivity(intent, bundle);
                finish();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun_sec);
        init();
        OnClick();
        setTitle("DotPuzzle");

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //android.os.Process.killProcess(android.os.Process.myPid());

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return true;
    }
}
