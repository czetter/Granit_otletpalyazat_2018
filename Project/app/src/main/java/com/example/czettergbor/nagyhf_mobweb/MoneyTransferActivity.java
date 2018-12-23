package com.example.czettergbor.nagyhf_mobweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.czettergbor.nagyhf_mobweb.data.Form;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

import java.util.ArrayList;

public class MoneyTransferActivity extends AppCompatActivity {
    Saver saver;
    ArrayList<Form> forms;
    Spinner urlapSpinner;
    TextView txtNev;
    TextView txtAccNum;
    TextView txtAmount;
    TextView txtKozl;
    RippleView btnKesz;
    Switch swMentes;
    Boolean qr = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        saver = new Saver(getBaseContext());
        forms = saver.loadArrayListForms();
        txtAccNum = findViewById(R.id.kezdAcc);
        txtNev = findViewById(R.id.kezdNev);
        txtAmount = findViewById(R.id.amount);
        txtKozl = findViewById(R.id.kozl);
        btnKesz = findViewById(R.id.btnKesz);
        swMentes = findViewById(R.id.swMentesUrlapkent);
        urlapSpinner = findViewById(R.id.urlapSpinner);

        String[] formNames = new String[forms.size() + 1];
        formNames[0] = "Űrlap választása";
        for (int i = 1; i < forms.size()+1; i++) {
            formNames[i] = forms.get(i-1).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, formNames);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        urlapSpinner.setAdapter(adapter);

        urlapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    txtAccNum.setText("");
                    txtNev.setText("");
                    qr = getIntent().getBooleanExtra("QrScanned",false);
                    if(qr){
                        String name = getIntent().getStringExtra("QrName");
                        String accnum = getIntent().getStringExtra("QrAccNum");
                        txtNev.setText(name);
                        txtAccNum.setText(accnum);
                    }
                } else {
                    txtNev.setText(forms.get(i - 1).getName());
                    txtAccNum.setText(forms.get(i - 1).getAccountNum());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txtAccNum.setText("");
                txtNev.setText("");
            }
        });

        btnKesz.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                if (txtNev.getText().toString().isEmpty()) {
                    txtNev.requestFocus();
                    txtNev.setError(getResources().getString(R.string.error_empty));
                    return;
                }

                if (txtAccNum.getText().toString().isEmpty()) {
                    txtAccNum.requestFocus();
                    txtAccNum.setError(getResources().getString(R.string.error_empty));
                    return;
                }

                if (txtAmount.getText().toString().isEmpty()) {
                    txtAmount.requestFocus();
                    txtAmount.setError(getResources().getString(R.string.error_empty));
                    return;
                }

                if (txtKozl.getText().toString().isEmpty()) {
                    txtKozl.requestFocus();
                    txtKozl.setError(getResources().getString(R.string.error_empty));
                    return;
                }

                if(swMentes.isChecked()){
                    forms.add(new Form(txtNev.getText().toString(),txtAccNum.getText().toString()));
                    saver.saveArrayList("forms",forms);
                }
                Intent mIntent = new Intent(MoneyTransferActivity.this,NavigationActivity.class);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(MoneyTransferActivity.this,NavigationActivity.class);
        startActivity(mIntent);
        finish();
    }

}
