package com.example.czettergbor.nagyhf_mobweb.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.data.Account;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class FragmentPieChart extends Fragment {

    PieChart mPieChart;
    Account myCard;
    Saver saver;

    int plusz = 0;
    int minusz = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piechart, container, false);
        saver = new Saver(getContext());
        myCard = saver.loadCreditCard();
        mPieChart = view.findViewById(R.id.piechart);
        Spinner spnHonap = view.findViewById(R.id.spnHonap);
        initSpinner(spnHonap);

        spnHonap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int honap = position + 1;
                calculateValues(honap);
                mPieChart.clearChart();
                if (minusz > 0)
                    mPieChart.addPieSlice(new PieModel("Kiadás", minusz, Color.RED));
                if (plusz > 0)
                    mPieChart.addPieSlice(new PieModel("Bevétel", plusz, getContext().getResources().getColor(R.color.colorLightGreen)));
                mPieChart.startAnimation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        calculateValues(getTodaysMonth());
        spnHonap.setSelection(getTodaysMonth() - 1);
        if (minusz >0)
            mPieChart.addPieSlice(new PieModel(getResources().getString(R.string.kiadas), minusz, Color.RED));
        if (plusz > 0)
            mPieChart.addPieSlice(new PieModel(getResources().getString(R.string.bevetel), plusz, getContext().getResources().getColor(R.color.colorLightGreen)));
        mPieChart.startAnimation();

        return view;
    }

    public void initSpinner(Spinner spn) {
        String[] months = {"Január", "Februás", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December"};
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(months));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
    }

    public void calculateValues(int month) {
        plusz = 0;
        minusz = 0;
        for (int i = 0; i < myCard.getHistory().size(); i++) {
            if (getMonthMine(myCard.getHistory().get(i).getDate()) == month) {
                if (myCard.getHistory().get(i).getOsszeg() > 0)
                    plusz += myCard.getHistory().get(i).getOsszeg();
                else
                    minusz -= myCard.getHistory().get(i).getOsszeg();
            }
        }
    }

    public int getMonthMine(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    public int getTodaysMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;

    }
}
