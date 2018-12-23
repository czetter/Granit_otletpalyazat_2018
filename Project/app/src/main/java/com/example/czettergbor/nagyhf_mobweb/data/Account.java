package com.example.czettergbor.nagyhf_mobweb.data;

import android.os.Build;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Account implements Serializable {
    String owner;
    String accountNum;
    int balance;
    ArrayList<AccHistory> history;
    String qrName;
    Goal goal;


    public Account(String _owner, String _accountNum,String filename) {
        qrName = filename;
        owner = _owner;
        accountNum = _accountNum;
        goal = null;

        history = new ArrayList<>();
        for (int i = 0; i < 15; i++)
            history.add(new AccHistory());

        for (int i = 0; i < history.size(); i++) {
            balance += history.get(i).getOsszeg();
        }
        Collections.sort(history, new Comparator<AccHistory>() {
            @Override
            public int compare(AccHistory accHistory, AccHistory t1) {
                return t1.getDate().compareTo(accHistory.getDate());
            }
        });
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public int getBalance() {
        return balance;
    }

    public ArrayList<AccHistory> getHistory() {
        return history;
    }

    public String getQrName() {
        return qrName;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
