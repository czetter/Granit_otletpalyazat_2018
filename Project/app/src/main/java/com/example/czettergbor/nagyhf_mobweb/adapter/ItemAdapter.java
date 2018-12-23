package com.example.czettergbor.nagyhf_mobweb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;
import com.example.czettergbor.nagyhf_mobweb.data.SpentItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Saver saver;
    LayoutInflater mInflater;
    Context mContext;
    ArrayList<SpentItem> items;

    public ItemAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        saver = new Saver(mContext);
        items = saver.loadArrayListItems();
        sortItems();

    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_item, parent, false);
        return  new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, final int position) {
        SpentItem entry = items.get(position);
        holder.txtItemName.setText(entry.getItemName());
        holder.txtItemPrice.setText(""+entry.getPrice()+" HUF");
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        if(items.get(position).getPrice()>0)
            holder.txtItemPrice.setTextColor(mContext.getResources().getColor(R.color.colorDarkGreen));
        else
            holder.txtItemPrice.setTextColor(Color.RED);

        holder.txtItemDate.setText(dateConverter(entry.getDate()));

        holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
                saver.saveArrayList("items",items);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName;
        TextView txtItemPrice;
        TextView txtItemDate;
        Button btnDeleteItem;
        SwipeLayout swipeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipe);
            txtItemDate = itemView.findViewById(R.id.txtItemDate);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);


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

    public void updateItems(){
        items = saver.loadArrayListItems();
        sortItems();
        notifyItemInserted(items.size()-1);
        notifyDataSetChanged();
    }
    public void sortItems(){
        Collections.sort(items, new Comparator<SpentItem>() {
            @Override
            public int compare(SpentItem spentItem, SpentItem t1) {
                return t1.getDate().compareTo(spentItem.getDate());
            }
        });
    }
}
