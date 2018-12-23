package com.example.czettergbor.nagyhf_mobweb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.czettergbor.nagyhf_mobweb.data.Account;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

public class MyQrActivity extends AppCompatActivity {
    Saver saver;
    Account myAcc;
    ImageView imgMyQr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr);
        imgMyQr = findViewById(R.id.imgMyQr);
        saver = new Saver(getBaseContext());
        myAcc = saver.loadCreditCard();
        String res = myAcc.getQrName();
        int id = getBaseContext().getResources().getIdentifier(res, "drawable", getBaseContext().getPackageName());
        Drawable dr = getResources().getDrawable(id);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 720,960, true));

        imgMyQr.setImageDrawable(d);
      //  imgMyQr.setImageResource(id);
    }
}
