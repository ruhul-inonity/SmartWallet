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

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ProductViewHolder> {
    private ArrayList<ReportModel> budgetList;

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView category,amount,spent;

        public ProductViewHolder(View view){
            super(view);
            category = (TextView) view.findViewById(R.id.txtBudgetCategoryName);
            amount = (TextView) view.findViewById(R.id.txtBudgetAmount);
            spent = (TextView) view.findViewById(R.id.txtBudgetSpent);
        }
    }

    public BudgetAdapter(ArrayList<ReportModel> transactionList) {
        this.budgetList = transactionList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_budget_report, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ReportModel reportModel = budgetList.get(position);

        holder.category.setText(reportModel.getCategoryName());
        holder.amount.setText(String.valueOf(reportModel.getAmount()));
        holder.spent.setText(String.valueOf(reportModel.getSpentAmount()));
        //Log.d("blaaa","..................... "+transactionModel.getCategoryType());
    }

    @Override
    public int getItemCount() {
        return budgetList.size();
    }


}
