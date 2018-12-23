package com.example.czettergbor.nagyhf_mobweb.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.adapter.AccHistoryAdapter;
import com.example.czettergbor.nagyhf_mobweb.data.Account;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

public class FragmentAccHistory extends Fragment {

    RecyclerView historyList;
    AccHistoryAdapter historyAdapter;
    Account myCard;
    Saver saver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acchistory, container, false);
        historyList = view.findViewById(R.id.historyRecyclerViewFragment);
        saver = new Saver(getContext());
        myCard = saver.loadCreditCard();

        historyList.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new AccHistoryAdapter(getContext(), myCard.getHistory(),getFragmentManager());
        historyList.setAdapter(historyAdapter);
        return view;
    }
}
