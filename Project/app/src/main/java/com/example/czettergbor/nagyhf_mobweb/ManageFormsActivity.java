package com.example.czettergbor.nagyhf_mobweb;

import android.content.Intent;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.czettergbor.nagyhf_mobweb.adapter.FormsAdapter;
import com.example.czettergbor.nagyhf_mobweb.data.Form;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class ManageFormsActivity extends AppCompatActivity {
    Saver saver;
    ArrayList<Form> forms;
    FormsAdapter formsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_forms);
        saver = new Saver(getBaseContext());
        forms = saver.loadArrayListForms();

        RecyclerView formsRecycleView = findViewById(R.id.rcvForms);
        formsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        formsAdapter = new FormsAdapter(this);
        formsRecycleView.setAdapter(formsAdapter);

        FabSpeedDial FloatingBtns = findViewById(R.id.FabSoeedDialForms);
        FloatingBtns.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals(getResources().getString(R.string.rlap_hozz_ad_s))) {
                    Intent mIntent2 = new Intent(ManageFormsActivity.this,AddFormActivity.class);
                    mIntent2.putExtra("BackToTormManager",true);
                    saver.saveArrayList("forms",forms);
                    startActivity(mIntent2);
                }
                return false;
            }
            @Override
            public void onMenuClosed() {
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(ManageFormsActivity.this, NavigationActivity.class);
        startActivity(mIntent);
        finish();
    }

}
