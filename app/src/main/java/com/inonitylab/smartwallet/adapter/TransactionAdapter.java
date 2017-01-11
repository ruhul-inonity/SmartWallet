package com.inonitylab.smartwallet.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruhul on 1/1/17.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ProductViewHolder> {
    private ArrayList<TransactionModel> transactionList;

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView date,category,amount,note;

        public ProductViewHolder(View view){
            super(view);
            date = (TextView) view.findViewById(R.id.txtDate);
            category = (TextView) view.findViewById(R.id.txtCategory);
            note = (TextView) view.findViewById(R.id.txtNote);
            amount = (TextView) view.findViewById(R.id.txtAmount);
        }
    }

    public TransactionAdapter(ArrayList<TransactionModel> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_transaction_row, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        TransactionModel transactionModel = transactionList.get(position);
        holder.date.setText(transactionModel.getDate());
        holder.category.setText(transactionModel.getCategoryType());
        holder.amount.setText(String.valueOf(transactionModel.getAmount()));
        holder.note.setText(transactionModel.getNote());
        //Log.d("blaaa","..................... "+transactionModel.getCategoryType());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }


}
