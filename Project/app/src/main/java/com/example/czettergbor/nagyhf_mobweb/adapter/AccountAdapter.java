package com.example.czettergbor.nagyhf_mobweb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.czettergbor.nagyhf_mobweb.NavigationActivity;
import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.data.Account;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

import java.util.ArrayList;

public class AccountAdapter extends  RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    Saver saver;
    ArrayList<Account> cards;
    Context mContext;
    LayoutInflater mInflater;


    public AccountAdapter(Context context,ArrayList<Account> accs){
        cards = accs;
        mContext = context;
        saver = new Saver(mContext);
        mInflater = LayoutInflater.from(context);

    }
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_account, parent, false);
        return new AccountAdapter.ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(final AccountAdapter.ViewHolder holder, int position) {
        final Account mAcc = cards.get(position);
        holder.txtName.setText(mAcc.getOwner());
        holder.txtNum.setText(mAcc.getAccountNum());
        holder.btnChoose.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                saver.saveCreditCard(mAcc);
                Intent mIntent = new Intent(rippleView.getContext(),NavigationActivity.class);
                rippleView.getContext().startActivity(mIntent);
            }
        });
        holder.linAccRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saver.saveCreditCard(mAcc);
                Intent mIntent = new Intent(v.getContext(),NavigationActivity.class);
                v.getContext().startActivity(mIntent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtNum;
        RippleView btnChoose;
        LinearLayout linAccRow;

        public ViewHolder(View itemView) {
            super(itemView);
            linAccRow = itemView.findViewById(R.id.linAccRow);
            txtName = itemView.findViewById(R.id.txtCardName);
            txtNum = itemView.findViewById(R.id.txtAccNum);
            btnChoose = itemView.findViewById(R.id.btnChoose);
        }
    }
}
