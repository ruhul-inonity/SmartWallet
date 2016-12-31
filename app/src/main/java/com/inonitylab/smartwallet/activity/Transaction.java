package com.inonitylab.smartwallet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.adapter.TransactionAdapter;
import com.inonitylab.smartwallet.database.TransactionCRUD;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.ArrayList;

public class Transaction extends AppCompatActivity {
    TransactionCRUD transactionCRUD;
    ArrayList<TransactionModel> allTransactionsList;
    TransactionAdapter transactionAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        allTransactionsList = new ArrayList<>();

        setDataAndPrepareAdapter();
        //recycle view
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(transactionAdapter);


    }

    private void setDataAndPrepareAdapter() {
        transactionCRUD = new TransactionCRUD(this);
        allTransactionsList = transactionCRUD.getAllTransactions();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_transaction);
        transactionAdapter = new TransactionAdapter(allTransactionsList);

        transactionAdapter.notifyDataSetChanged();
    }


}
