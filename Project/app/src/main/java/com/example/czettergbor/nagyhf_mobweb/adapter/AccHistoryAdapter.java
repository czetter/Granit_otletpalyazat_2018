package com.example.czettergbor.nagyhf_mobweb.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.czettergbor.nagyhf_mobweb.Fragments.DialogFragmentAddItem;
import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.data.AccHistory;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;
import com.example.czettergbor.nagyhf_mobweb.data.SpentItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AccHistoryAdapter extends RecyclerView.Adapter<AccHistoryAdapter.ViewHolder> {
    Saver saver;
    ArrayList<AccHistory> hist = new ArrayList<>();
    ArrayList<SpentItem> items;
    LayoutInflater mInflater;
    Context mContext;
    FragmentManager fm;

    public AccHistoryAdapter(Context context, ArrayList<AccHistory> _hist, FragmentManager _fm) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        hist = _hist;
        saver = new Saver(mContext);
        items = saver.loadArrayListItems();
        fm = _fm;


    }


    @Override
    public AccHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_history, parent, false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(final AccHistoryAdapter.ViewHolder holder, int position) {
        final AccHistory mHistory = hist.get(position);
        if (mHistory.getOsszeg() > 0) {
            holder.osszeg.setTextColor(mContext.getResources().getColor(R.color.colorDarkGreen));
            holder.osszeg.setText("+" + mHistory.getOsszeg() + " HUF");
        } else {
            holder.osszeg.setTextColor(Color.RED);
            holder.osszeg.setText("" + mHistory.getOsszeg() + " HUF");
        }
        holder.nev.setText(mHistory.getNev());
        holder.datum.setText(dateConverter(mHistory.getDate()));
        holder.linHistRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.linHistRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.import_short)
                        .setMessage(R.string.import_long)
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                DialogFragmentAddItem dialogFragmentAddItem = new DialogFragmentAddItem();
                                dialogFragmentAddItem.addItem(new SpentItem(mHistory.getNev(),mHistory.getOsszeg(),false,mHistory.getDate()));
                                dialogFragmentAddItem.show(fm,"Dialog");
                            }
                        }).create().show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return hist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView osszeg;
        TextView nev;
        TextView datum;
        LinearLayout linHistRow;

        public ViewHolder(View itemView) {
            super(itemView);
            nev = itemView.findViewById(R.id.nev);
            osszeg = itemView.findViewById(R.id.osszeg);
            datum = itemView.findViewById(R.id.datum);
            linHistRow = itemView.findViewById(R.id.linHistRow);

        }
    }
    public String dateConverter(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return year+"."+month+"."+day;
    }


}
