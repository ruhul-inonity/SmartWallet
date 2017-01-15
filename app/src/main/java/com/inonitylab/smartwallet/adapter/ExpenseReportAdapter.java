package com.inonitylab.smartwallet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.model.ReportModel;

import java.util.ArrayList;

/**
 * Created by ruhul on 1/1/17.
 */

public class ExpenseReportAdapter extends RecyclerView.Adapter<ExpenseReportAdapter.ProductViewHolder> {
    private ArrayList<ReportModel> incomeList;

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView category,amount;

        public ProductViewHolder(View view){
            super(view);
            category = (TextView) view.findViewById(R.id.txtReportExCategoryName);
            amount = (TextView) view.findViewById(R.id.txtReportExTotal);
        }
    }

    public ExpenseReportAdapter(ArrayList<ReportModel> transactionList) {
        this.incomeList = transactionList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_expense_report, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ReportModel reportModel = incomeList.get(position);

        holder.category.setText(reportModel.getCategoryName());
        holder.amount.setText(String.valueOf(reportModel.getAmount()));
        //Log.d("blaaa","..................... "+transactionModel.getCategoryType());
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }


}
