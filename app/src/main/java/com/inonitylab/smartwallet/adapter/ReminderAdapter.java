package com.inonitylab.smartwallet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.ArrayList;

/**
 * Created by ruhul on 1/1/17.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ProductViewHolder> {
    private ArrayList<TransactionModel> transactionList;

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView date,category,amount,note,time;

        public ProductViewHolder(View view){
            super(view);
            date = (TextView) view.findViewById(R.id.txtReminderDate);
            category = (TextView) view.findViewById(R.id.txtReminderCategory);
            note = (TextView) view.findViewById(R.id.txtReminderNote);
            amount = (TextView) view.findViewById(R.id.txtReminderAmount);
            time = (TextView) view.findViewById(R.id.txtReminderTime);
        }
    }

    public ReminderAdapter(ArrayList<TransactionModel> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_reminder_row, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        TransactionModel transactionModel = transactionList.get(position);
        holder.date.setText(transactionModel.getDate());
        holder.category.setText(transactionModel.getCategoryType());
        holder.amount.setText(String.valueOf(transactionModel.getAmount()));
        holder.note.setText(transactionModel.getNote());
        holder.time.setText(transactionModel.getTime());
        //Log.d("blaaa","..................... "+transactionModel.getCategoryType());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }


}
