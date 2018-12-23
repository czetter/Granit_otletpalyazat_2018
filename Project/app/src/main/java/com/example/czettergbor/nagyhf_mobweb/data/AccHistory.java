package com.example.czettergbor.nagyhf_mobweb.data;

import android.nfc.Tag;
import android.util.Log;

import com.example.czettergbor.nagyhf_mobweb.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class AccHistory implements Serializable {

    int osszeg;
    String nev;
    Date date;

    public AccHistory() {
        Random r = new Random();
        osszeg = r.nextInt(20000);
        if(osszeg % 3 == 0) {
            osszeg = osszeg * -1;
            nev = "Vásárlás kártyával";
        }else
            nev = "Befizetés";
        int month = r.nextInt(11) + 1;
        int day = r.nextInt(29)+1;
       // Log.d("Acchistory created",nev+" "+osszeg+ " Month: "+month+" Day:"+day);
        date = new Date(118,month, day);

    }

    public String getNev() {
        return nev;
    }

    public int getOsszeg() {
        return osszeg;
    }

    public Date getDate() {
        return date;
    }

}
