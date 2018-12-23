package com.example.czettergbor.nagyhf_mobweb.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Saver {
    SharedPreferences sharedPreferences;
    Context mContext;


    public Saver(Context context) {
        sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        mContext = context;
    }

    public void saveArrayList(String name, ArrayList list){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(name, json);
        editor.apply();
    }

    public ArrayList loadArrayListForms(){
        String name = "forms";
        Gson gson = new Gson();
        String json = sharedPreferences.getString(name,null);
        Type type = new TypeToken<ArrayList<Form>>(){}.getType();
        ArrayList<Form> loadedForms = gson.fromJson(json,type);
        if(loadedForms == null){
            loadedForms = new ArrayList<>();
            loadedForms.add(new Form("Teszt Béla", "12345678-12345678-0000000"));
            loadedForms.add(new Form("Teszt András", "87654321-87654321-00000000"));
        }
        return loadedForms;
    }

    public ArrayList loadArrayListItems(){
        String name = "items";
        Gson gson = new Gson();
        String json = sharedPreferences.getString(name,null);
        Type type = new TypeToken<ArrayList<SpentItem>>(){}.getType();
        ArrayList<SpentItem> loaded = gson.fromJson(json,type);
        if(loaded == null){
            loaded = new ArrayList<>();
            loaded.add(new SpentItem("Ebéd", 1600, false, new Date(2018-1900, 01, 01)));
        }
        sortItems(loaded);
        return loaded;
    }

    public void saveCreditCard(Account card){
        String name = "myCard";
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(card);
        editor.putString(name,json);
        editor.apply();
    }

    public Account loadCreditCard(){
        String name = "myCard";
        Gson gson = new Gson();
        String json = sharedPreferences.getString(name,null);
        Type type = new TypeToken<Account>(){}.getType();
        return gson.fromJson(json,type);

    }

    public void sortItems(ArrayList<SpentItem> wannabesorted){
        Collections.sort(wannabesorted, new Comparator<SpentItem>() {
            @Override
            public int compare(SpentItem item, SpentItem t1) {
                return t1.getDate().compareTo(item.getDate());
            }
        });
    }


}
