package com.narcoding.dotpuzzle;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;
import java.util.Random;

public class Arcade extends AppCompatActivity {

    RelativeLayout pnlArcade;

    Adapter adp;
    GridView gridViewA;


    //img sayısı
    int length=25;
    int[] imgs;


    TextView txtSureA;
    ImageButton btnGeri;
    ImageView imgSureA;

    int i=1;
    int a;


    private int[] ImgListGetir(int len){

        int[] imglist = new int[len];

        for(int i=0;i<len;i++){
            imglist[i]=R.drawable.iki;
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

    private void RandomBolumOlustur(int tiksayisi){

        List<Integer> kontroldizisi = new ArrayList<Integer>();

        for(int i=0;i<tiksayisi;i++){
            Random rand = new Random();
            int pos = rand.nextInt(length);

            //bolumde aynı yere tıklayıp oluşturulan bölümlerin engellenmesi

            //

            Log.e("csd", (pos + 1) + ". Noktada random bişi oluştu");

            int s= (int) Math.sqrt((double) length);

            int newpos=pos+1;
            int y=newpos%s;
            if(y==0){y=s;}

            int x=((newpos-y)/s)+1;

            Log.e("csd", (x)+","+(y) + ". Koordinat");

            List<Integer> imgdizi = new ArrayList<Integer>();
            imgdizi.add((x - 1) * s + y);
            if (!(y+"").equals("1")) {
                imgdizi.add((x - 1) * s + y - 1);
            }
            if (!(y+"").equals(String.valueOf(s))) {
                imgdizi.add((x - 1) * s + y + 1);
            }
            if (!(x+"").equals("1")) {
                imgdizi.add((x - 1) * s + y - s);
            }
            if (!(x+"").equals(String.valueOf(s))) {
                imgdizi.add((x - 1) * s + y + s);
            }

            for (int sayi : imgdizi) {
                RenkDegistirTers(sayi - 1);
            }
        }

        gridViewA.setAdapter(adp);
    }


    private void init(){

        pnlArcade= (RelativeLayout) findViewById(R.id.pnlArcade);
        pnlArcade.setBackgroundResource(R.drawable.zemin);
        btnGeri= (ImageButton) findViewById(R.id.btnGeriA);
        btnGeri.setBackgroundResource(R.drawable.geri);
        imgSureA= (ImageView) findViewById(R.id.imgSureA);
        imgSureA.setBackgroundResource(R.drawable.sure);
        imgs=ImgListGetir(length);
        gridViewA= (GridView) findViewById(R.id.gridViewA);
        gridViewA.setNumColumns((int) Math.sqrt(imgs.length));
        gridViewA.setColumnWidth((int) (imgs.length ));


        txtSureA= (TextView) findViewById(R.id.txtSureA);

        adp=new Adapter(this,imgs);
        gridViewA.setAdapter(adp);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade);


        Intent intent=getIntent();
        if(intent.getExtras()!=null) {
            a = intent.getIntExtra("bolum", 1);
        }else {a=1;}

        setTitle("Level "+a);


        init();
        txtSureA.setText(a+"");


        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Arcade.this, OyunSec.class);
                Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
                startActivity(intent, bundle);

            }
        });


        RandomBolumOlustur(a);


        gridViewA.setAdapter(adp);


        gridViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {

                int hmlsys = a - i;

                txtSureA.setText(hmlsys+"");
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
                gridViewA.setAdapter(adp);

                boolean holder = true;

                for (int i = 0; i < imgs.length; i++) {
                    if (imgs[i] != R.drawable.iki) {
                        holder = false;
                    }
                }


                if (holder) {
                    if(a==12){
                        Toast.makeText(Arcade.this,  "GREAT! HEIGHT SCORE :"+ a, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Arcade.this, OyunSec.class);
                        intent.putExtra("bolum", a + 1);

                        Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
                        startActivity(intent, bundle);
                    }else {
                        Toast.makeText(Arcade.this, "LEVEL " + a + " COMPLETED!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Arcade.this, Arcade.class);
                        intent.putExtra("bolum", a + 1);

                        Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                        startActivity(intent, bundle);
                    }

                } else if (hmlsys == 0) {

                    Toast.makeText(Arcade.this, "LEVEL " + a + " FAILED!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Arcade.this, OyunSec.class);

                    Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
                    startActivity(intent, bundle);

                }


            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(Arcade.this, OyunSec.class);
        Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.saga, R.anim.soldan).toBundle();
        startActivity(intent, bundle);
        return true;
    }
}
