package com.example.czettergbor.nagyhf_mobweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.czettergbor.nagyhf_mobweb.data.Form;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

import java.util.ArrayList;

public class AddFormActivity extends AppCompatActivity {

    Saver saver;
    ArrayList<Form> forms;
    EditText txtFormName;
    EditText txtFormNum;
    RippleView btnKesz;
    Boolean intentToFormManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);
        saver = new Saver(getBaseContext());
        forms = saver.loadArrayListForms();
        txtFormName = findViewById(R.id.txtFormName);
        txtFormNum = findViewById(R.id.txtFormNum);
        btnKesz = findViewById(R.id.btnKesz);
        intentToFormManager = getIntent().getBooleanExtra("BackToTormManager", false);

        btnKesz.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if (txtFormName.getText().toString().isEmpty()) {
                    txtFormName.requestFocus();
                    txtFormName.setError(getResources().getString(R.string.error_empty));
                    return;
                }

                if (txtFormNum.getText().toString().isEmpty()) {
                    txtFormNum.requestFocus();
                    txtFormNum.setError(getResources().getString(R.string.error_empty));
                    return;
                }
                forms.add(new Form(txtFormName.getText().toString(), txtFormNum.getText().toString()));
                Toast.makeText(getBaseContext(), getResources().getString(R.string.urlap_hozzaadva), Toast.LENGTH_SHORT).show();
                saver.saveArrayList("forms", forms);
                Intent mIntent;
                if (intentToFormManager)
                    mIntent = new Intent(AddFormActivity.this, ManageFormsActivity.class);
                else
                    mIntent = new Intent(AddFormActivity.this,NavigationActivity.class);
                startActivity(mIntent);
            }
        });
    }


}
