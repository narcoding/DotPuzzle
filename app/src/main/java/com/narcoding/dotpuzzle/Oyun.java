package com.narcoding.dotpuzzle;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Naim on 17.04.2016.
 */
public class Oyun extends AppCompatActivity {


    Adapter adp;
    GridView gv;
    //img sayısı
    int length=25;
    int[] imgs;

    RelativeLayout pnlOyun;


    TextView txtSure;
    ImageButton btnGeri;
    ImageButton btnRestartO;
    ImageView imgSure;

    int l=0;
    int i=1;
    int a=1;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent=new Intent(Oyun.this,MainActivity.class);
        Bundle bundle= ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
        startActivity(intent,bundle);
        return true;
    }

    public int BolumBilgisiGetir(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getInt("songecilenbolum", 0);
    }


    public void BolumBilgisiKaydet( int value){

        if ((BolumBilgisiGetir()<value)){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("songecilenbolum", value);
            editor.commit();
        }
    }



    private int[] ImgListGetir(int len){

        int[] imglist = new int[len];

        for(int i=0;i<len;i++){
            imglist[i]= R.drawable.iki;
        }

        return imglist;
    }

    private void RenkDegistir(int pos){

        if (imgs[pos] == R.drawable.dort) {
            imgs[pos] = R.drawable.uc;


        } else if (imgs[pos] == R.drawable.uc) {
            imgs[pos] = R.drawable.iki;


        } else if (imgs[pos] == R.drawable.iki) {
            imgs[pos] = R.drawable.dort;

        }

    }

    private void RenkDegistirTers(int pos){

        if (imgs[pos] == R.drawable.uc) {
            imgs[pos] = R.drawable.dort;


        } else if (imgs[pos] == R.drawable.iki) {
            imgs[pos] = R.drawable.uc;


        } else if (imgs[pos] == R.drawable.dort) {
            imgs[pos] = R.drawable.iki;

        }

    }

    private void RandomBolumOlustur(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String replace = sharedPreferences.getString(a+".bolum", "").replace("[", "");
        String replace1 = replace.replace("]","");
        List<String> arrayList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
        List<Integer> favList = new ArrayList<Integer>();

        for(String fav:arrayList){
            favList.add(Integer.parseInt(fav.trim()));
        }

        List<Integer> kontroldizisi = new ArrayList<Integer>();

        for(int i=0;i<favList.size();i++) {

            int pos = favList.get(i);

            int kere=0;

            for (int x : kontroldizisi) {
                if(pos==x) kere++;
            }

            if(kere<2){

                kontroldizisi.add(pos);



                int s = (int) Math.sqrt((double) length);

                int newpos = pos + 1;
                int y = newpos % s;
                if (y == 0) {
                    y = s;
                }

                int x = ((newpos - y) / s) + 1;


                List<Integer> imgdizi = new ArrayList<Integer>();
                imgdizi.add((x - 1) * s + y);
                if (!(y + "").equals("1")) {
                    imgdizi.add((x - 1) * s + y - 1);
                }
                if (!(y + "").equals(String.valueOf(s))) {
                    imgdizi.add((x - 1) * s + y + 1);
                }
                if (!(x + "").equals("1")) {
                    imgdizi.add((x - 1) * s + y - s);
                }
                if (!(x + "").equals(String.valueOf(s))) {
                    imgdizi.add((x - 1) * s + y + s);
                }

                for (int sayi : imgdizi) {
                    RenkDegistirTers(sayi - 1);
                }

            }
            else{
                Log.e("csd", "3 tane aynı nokta denk geldi ayık ol");
            }
        }

        gv.setAdapter(adp);
    }


    private List<Integer> RandomBolumKaydet(int bolumno){

        List<Integer> kontroldizisi = new ArrayList<Integer>();

        for(int i=0;i<bolumno;i++) {
            Random rand = new Random();
            int pos = rand.nextInt(length);

            int kere=0;

            for (int x : kontroldizisi) {
                if(pos==x) kere++;
            }

            if(kere<2) {
                kontroldizisi.add(pos);
            }
        }


        return kontroldizisi;
    }


    private void init(){
        pnlOyun= (RelativeLayout) findViewById(R.id.pnlOyun);
        pnlOyun.setBackgroundResource(R.drawable.zemin);
        btnGeri= (ImageButton) findViewById(R.id.btnGeriO);
        btnGeri.setBackgroundResource(R.drawable.geri);
        btnRestartO= (ImageButton) findViewById(R.id.btnRestartO);
        btnRestartO.setBackgroundResource(R.drawable.restart);
        imgSure= (ImageView) findViewById(R.id.imgSure);
        imgSure.setBackgroundResource(R.drawable.sure);
        imgs=ImgListGetir(length);
        gv= (GridView) findViewById(R.id.gridView);
        gv.setNumColumns((int) Math.sqrt(imgs.length));
        gv.setColumnWidth((int) (Math.sqrt(imgs.length)));
        txtSure= (TextView) findViewById(R.id.txtSure);
        adp=new Adapter(this,imgs);
        gv.setAdapter(adp);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);

        Intent intent=getIntent();

        if(intent.getExtras()!=null){
            a=intent.getIntExtra("bolum",1);
        }else {
            a=1;}
        setTitle("Level "+a);

        init();
        txtSure.setText(a + "");

        btnRestartO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oyun.this, MainActivity.class);
                Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
                startActivity(intent, bundle);
                finish();
            }
        });



        RandomBolumOlustur();



        gv.setAdapter(adp);




        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {

                int hamlesayisi=a-i;

                txtSure.setText(hamlesayisi+"");
                i++;


                String[] dizi = ((String) v.getTag()).split("-");


                int s = (int) Math.sqrt(imgs.length);
                int y = Integer.parseInt(dizi[1]);
                int x = Integer.parseInt(dizi[0]);



                List<Integer> imgdizi = new ArrayList<Integer>();
                imgdizi.add((x - 1) * s + y);
                if (!dizi[1].equals("1")) {
                    imgdizi.add((x - 1) * s + y - 1);
                }
                if (!dizi[1].equals(String.valueOf(s))) {
                    imgdizi.add((x - 1) * s + y + 1);
                }
                if (!dizi[0].equals("1")) {
                    imgdizi.add((x - 1) * s + y - s);
                }
                if (!dizi[0].equals(String.valueOf(s))) {
                    imgdizi.add((x - 1) * s + y + s);
                }

                for (int sayi : imgdizi) {
                    RenkDegistir(sayi - 1);
                }
                gv.setAdapter(adp);

                boolean holder=true;

                for (int i =0;i<imgs.length;i++){
                    if(imgs[i]!=R.drawable.iki){holder=false;}
                }



                if (holder) {
                    if(a==12){
                        Toast.makeText(Oyun.this,  "GREAT!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Oyun.this, MainActivity.class);

                        Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
                        startActivity(intent, bundle);
                        finish();
                    }else {

                        Toast.makeText(Oyun.this, "LEVEL " + a + " COMPLETED!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Oyun.this, MainActivity.class);

                        Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
                        startActivity(intent, bundle);

                        BolumBilgisiKaydet(a);
                        finish();
                    }

                }
                else if(hamlesayisi==0){
                    Toast.makeText(Oyun.this,"LEVEL " + a + " FAILED!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Oyun.this, MainActivity.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
                    startActivity(intent, bundle);
                    finish();

                }


            }
        });


    }
}

