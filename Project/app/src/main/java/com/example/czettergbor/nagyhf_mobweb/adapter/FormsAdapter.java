package com.example.czettergbor.nagyhf_mobweb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.czettergbor.nagyhf_mobweb.R;
import com.example.czettergbor.nagyhf_mobweb.data.Form;
import com.example.czettergbor.nagyhf_mobweb.data.Saver;

import java.util.ArrayList;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.ViewHolder> {
    Saver saver;
    ArrayList<Form> forms;
    LayoutInflater mInflater;
    Context mContext;

    public FormsAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        saver = new Saver(mContext);
        forms = saver.loadArrayListForms();
    }


    @Override
    public FormsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_form, parent, false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(FormsAdapter.ViewHolder holder, final int position) {
        holder.txtFormName.setText(forms.get(position).getName());
        holder.txtFormNum.setText(forms.get(position).getAccountNum());
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        holder.btnDeleteForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forms.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, forms.size());
                saver.saveArrayList("forms",forms);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView txtFormName;
        TextView txtFormNum;
        Button btnDeleteForm;

        public ViewHolder(View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipe);
            txtFormName = itemView.findViewById(R.id.txtFormName);
            txtFormNum = itemView.findViewById(R.id.txtFormNum);
            btnDeleteForm = itemView.findViewById(R.id.btnDeleteForm);

        }
    }

}
