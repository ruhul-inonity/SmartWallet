package com.inonitylab.smartwallet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.adapter.TransactionAdapter;
import com.inonitylab.smartwallet.database.CategoriesCRUD;
import com.inonitylab.smartwallet.database.TransactionCRUD;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.ArrayList;

public class CategoryReportActivity extends AppCompatActivity {
    TransactionAdapter transactionAdapter;
    RecyclerView recyclerView;
    TransactionCRUD transactionCRUD;
    CategoriesCRUD categoriesCRUD;
    ArrayList<TransactionModel> allTransactionsList;
    TextView txtCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_report);
        txtCategory = (TextView) findViewById(R.id.textSelectedCategory);
        String category = getIntent().getStringExtra("category");
        txtCategory.setText(category);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_category_transaction);
        setDataAndPrepareAdapter(category);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(transactionAdapter);
    }

    private void setDataAndPrepareAdapter(String category) {
        transactionCRUD = new TransactionCRUD(this);
        categoriesCRUD = new CategoriesCRUD(this);
        int categoryId = categoriesCRUD.getCategoryId(category);
        allTransactionsList = transactionCRUD.getTransactionsByCategory(categoryId);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_category_transaction);
        transactionAdapter = new TransactionAdapter(allTransactionsList, category);

        transactionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}