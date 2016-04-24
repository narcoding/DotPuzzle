package com.narcoding.dotpuzzle;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {



    private GridView pnlGrid;
    private Bolumler bolumler;
    private BolumAdapter controller;
    private RelativeLayout pnlmain;

    private void init(){
        this.getSharedPreferences("YOUR_PREFS", 0).edit().clear().commit();
        pnlGrid=(GridView) findViewById(R.id.pnlGrid);
        bolumler = new Bolumler(this);
        controller = new BolumAdapter(MainActivity.this,bolumler);
        pnlGrid.setAdapter(controller);
        pnlmain= (RelativeLayout) findViewById(R.id.pnlmain);
        pnlmain.setBackgroundResource(R.drawable.zemin);
    }

    private void registerhandle(){

        pnlGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (bolumler.getBolumler().get(position).getGecildi()) {
                    Intent intent = new Intent(MainActivity.this, Oyun.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                    intent.putExtra("bolum", position + 1);
                    startActivity(intent, bundle);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        registerhandle();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent=new Intent(MainActivity.this,OyunSec.class);
        Bundle bundle= ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
        startActivity(intent, bundle);
        return true;
    }
}
