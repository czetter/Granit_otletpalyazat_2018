package com.example.czettergbor.nagyhf_mobweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.andexert.library.RippleView;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.NoActionBar);

        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password  = findViewById(R.id.password);

        username.setText("TesztFelhasználó");
        password.setText("Password");



        RippleView loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if (username.getText().toString().isEmpty()) {
                    username.requestFocus();
                    username.setError(getResources().getString(R.string.error_username));
                    return;
                }

                if (password.getText().toString().isEmpty()) {
                    password.requestFocus();
                    password.setError(getResources().getString(R.string.erros_pass));
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
