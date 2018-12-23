package com.example.czettergbor.nagyhf_mobweb;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

public class LockCardActivity extends AppCompatActivity {

    ImageView imgLock;
    RippleView btnLock;
    TextView btnLockText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_card);

        imgLock = findViewById(R.id.imgLock);
        btnLock = findViewById(R.id.btnLock);
        btnLockText = findViewById(R.id.btnLockText);
        imgLock.setImageResource(R.drawable.img_unlocked);
        btnLock.setClickable(false);

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnLockText.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                btnLockText.setText(getResources().getString(R.string.zarolas_short));
                btnLock.setClickable(true);
                btnLock.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        imgLock.setImageResource(R.drawable.img_locked);
                        Toast.makeText(LockCardActivity.this, getResources().getString(R.string.zarolas_long), Toast.LENGTH_SHORT).show();
                        btnLock.setOnRippleCompleteListener(null);
                    }
                });
            }
        }.start();


    }
}
