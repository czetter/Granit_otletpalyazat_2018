package com.example.czettergbor.nagyhf_mobweb.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.adapter.ItemAdapter;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;
import com.example.czettergbor.nagyhf_mobweb.data.SpentItem;

import java.util.ArrayList;

public class FragmentSpendingTracker extends Fragment {
    String TAG = "FragmentSpendingTracker";
   ArrayList<SpentItem> items;
    Saver saver;
    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    ImageButton btnAddItem;
    DialogFragmentAddItem addItemDialog;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spendingtracker, container, false);
        recyclerView = view.findViewById(R.id.spendingRecyclerView);
        btnAddItem = view.findViewById(R.id.btnAddItem);
        saver = new Saver(getContext());
        items = saver.loadArrayListItems();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter = new ItemAdapter(getContext());
        recyclerView.setAdapter(itemAdapter);

        addItemDialog = new DialogFragmentAddItem();
        addItemDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                itemAdapter.updateItems();
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemDialog.show(getFragmentManager(), "Dialog");

            }
        });



        return view;
    }

}
