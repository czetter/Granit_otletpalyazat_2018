package com.example.czettergbor.nagyhf_mobweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.andexert.expandablelayout.library.ExpandableLayoutListView;
import com.example.czettergbor.nagyhf_mobweb.adapter.AccHistoryAdapter;
import com.example.czettergbor.nagyhf_mobweb.adapter.AccountAdapter;
import com.example.czettergbor.nagyhf_mobweb.data.Account;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    ArrayList<Account> cards = new ArrayList<>();
    RecyclerView rcvAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvAccounts = findViewById(R.id.listview);

        populate();

        rcvAccounts.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        AccountAdapter accountAdapter = new AccountAdapter(getBaseContext(), cards);
        rcvAccounts.setAdapter(accountAdapter);
    }

    private void populate() {
        cards.add(new Account("Teszt Gábor", "00000000-00000000-00000000", "account_tesztgabor"));
        cards.add(new Account("Teszt Szandi", "11111111-22222222-33333333", "account_tesztszandi"));
        cards.add(new Account("Teszt Bálint", "88888888-88888888-88888888", "account_tesztbalint"));
    }
}
