package com.narcoding.dotpuzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Naim on 17.04.2016.
 */
public class Bolumler {
    //asd
    public int bolumsayisi=12;


    private Context context;

    private ArrayList<Bolum> bolumler;

    public ArrayList<Bolum> getBolumler() {
        return bolumler;
    }

    public int BolumBilgisiGetir(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("songecilenbolum", 0);
    }

    public void RandomBolumlerOlustur(){


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if( sharedPreferences.getString("1.bolum", String.valueOf(0)) !=String.valueOf(0)){
            Log.e("csd", "asdasdasdasdasdasdasd");
        }
        else{

            for(int i=1;i<12;i++){
                // List<Integer> y=new ArrayList<Integer>();

                Integer[] y = new Integer[i];

                for(int j=0;j<i;j++){
                    Random rand = new Random();
                    y[j]=rand.nextInt(25);
                }

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(i+".bolum", Arrays.toString(y));
                editor.commit();
            }
        }

    }



    public Bolumler(Context context){


        this.context=context;
        RandomBolumlerOlustur();
        int songecilenbolum=BolumBilgisiGetir();

        bolumler = new ArrayList<Bolum>();

        for(int i=1;i<bolumsayisi+1;i++){
            bolumler.add(new Bolum(i,songecilenbolum+2>i,songecilenbolum+2>i));
        }
    }
}

