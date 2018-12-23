package com.example.czettergbor.nagyhf_mobweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.czettergbor.nagyhf_mobweb.data.Account;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

public class MobilPaymentActivity extends AppCompatActivity {

    Saver saver;
    Account myAcc;
    ImageView imgPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobil_payment);
        saver = new Saver(getBaseContext());
        myAcc = saver.loadCreditCard();

        TextView txtNamePay = findViewById(R.id.txtNamePay);
        TextView txtNumPay = findViewById(R.id.txtNumPay);
        final RippleView btnPay = findViewById(R.id.btnPay);
        imgPay  = findViewById(R.id.imgPay);
        imgPay.setImageResource(R.drawable.img_mobile_pay);
        txtNamePay.setText(myAcc.getOwner());
        txtNumPay.setText(myAcc.getAccountNum());

        btnPay.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                imgPay.setImageResource(R.drawable.img_succesful);
                Toast.makeText(getBaseContext(),getResources().getString(R.string.sikeres_fizetes),Toast.LENGTH_LONG).show();
                btnPay.setOnRippleCompleteListener(null);
            }
        });

    }
}
