package com.example.czettergbor.nagyhf_mobweb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenu;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.czettergbor.nagyhf_mobweb.Fragments.FragmentAccHistory;
import com.example.czettergbor.nagyhf_mobweb.Fragments.FragmentGoal;
import com.example.czettergbor.nagyhf_mobweb.Fragments.FragmentPieChart;
import com.example.czettergbor.nagyhf_mobweb.Fragments.FragmentSpendingTracker;
import com.example.czettergbor.nagyhf_mobweb.adapter.FragmentAdapterMenu;
import com.example.czettergbor.nagyhf_mobweb.data.Account;
import com.example.czettergbor.nagyhf_mobweb.data.Form;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Saver saver;
    ArrayList<Form> forms;
    Account myCard;
    FragmentAdapterMenu adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        saver = new Saver(getBaseContext());

        Toast.makeText(getBaseContext(), getResources().getString(R.string.reszletek), Toast.LENGTH_LONG).show();
        saver = new Saver(getBaseContext());
        forms = saver.loadArrayListForms();
        myCard = saver.loadCreditCard();

        TextView txtNameContent = findViewById(R.id.txtNameContent);
        TextView txtBalanceContent = findViewById(R.id.txtBalanceContent);
        TextView txtNumContent = findViewById(R.id.txtNumContent);
        txtNameContent.setText(myCard.getOwner());
        txtBalanceContent.setText(myCard.getBalance() + " HUF");
        txtNumContent.setText(myCard.getAccountNum());

        //setting up fragments
        adapter = new FragmentAdapterMenu(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        setUpViewPager(viewPager);

        //creating the Floating buttons
        FabSpeedDial FloatingBtns = findViewById(R.id.FabSoeedDial1);
        FloatingBtns.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().equals(getResources().getString(R.string.utal_s))) {
                    Intent mIntent = new Intent(NavigationActivity.this, MoneyTransferActivity.class);
                    saver.saveArrayList("forms", forms);
                    startActivity(mIntent);
                }
                if (menuItem.getTitle().equals(getResources().getString(R.string.rlap_hozz_ad_s))) {
                    Intent mIntent2 = new Intent(NavigationActivity.this, AddFormActivity.class);
                    saver.saveArrayList("forms", forms);
                    startActivity(mIntent2);
                }
                if (menuItem.getTitle().equals(getResources().getString(R.string.mobilos_fizet_s))) {
                    Intent mIntent2 = new Intent(NavigationActivity.this, MobilPaymentActivity.class);
                    saver.saveArrayList("forms", forms);
                    startActivity(mIntent2);
                }

                return false;
            }

            @Override
            public void onMenuClosed() {

            }
        });

        //setting up drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //drawer header texts
        View headerView = navigationView.getHeaderView(0);
        TextView txtNameHeader = headerView.findViewById(R.id.txtNameHeader);
        TextView txtNumHeader = headerView.findViewById(R.id.txtNumHeader);
        txtNameHeader.setText(myCard.getOwner());
        txtNumHeader.setText(myCard.getAccountNum());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.kilepes))
                    .setMessage(getResources().getString(R.string.kilepes_long))
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface arg0, int arg1) {
                            finishAffinity();
                        }
                    }).create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(NavigationActivity.this, MainActivity.class);
            startActivity(mIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent mIntent;
        if (id == R.id.nav_moneyTransfer) {
            mIntent = new Intent(NavigationActivity.this, MoneyTransferActivity.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_manageForms) {
            mIntent = new Intent(NavigationActivity.this, ManageFormsActivity.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_Qrscan) {
            mIntent = new Intent(NavigationActivity.this, QRscanActivity.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_myQr) {
            mIntent = new Intent(NavigationActivity.this, MyQrActivity.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_help) {
            mIntent = new Intent(NavigationActivity.this,HelpActivity.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_lock) {
            mIntent = new Intent(NavigationActivity.this, LockCardActivity.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_maps) {
            mIntent = new Intent(NavigationActivity.this, MapsActivity.class);
            startActivity(mIntent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void setUpViewPager(ViewPager viewPager) {
        FragmentAdapterMenu adapterMenu = new FragmentAdapterMenu(getSupportFragmentManager());
        adapterMenu.addFragment(new FragmentAccHistory(), "FragmentAccHistory");
        adapterMenu.addFragment(new FragmentPieChart(), "FragmentPieChart");
        adapterMenu.addFragment(new FragmentSpendingTracker(), "FragmentSpendingTracker");
        adapterMenu.addFragment(new FragmentGoal(), "FragmentGoal");
        viewPager.setAdapter(adapterMenu);
    }

    public void populateForms() {
        forms.add(new Form("Teszt Béla", "12345678-12345678-0000000"));
        forms.add(new Form("Teszt András", "87654321-87654321-00000000"));
    }
}
