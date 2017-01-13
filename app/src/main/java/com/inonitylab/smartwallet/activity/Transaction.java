package com.inonitylab.smartwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.adapter.RecyclerTouchListener;
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

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TransactionModel transactionModel = allTransactionsList.get(position);
                Intent intent = new Intent(Transaction.this,UpdateTransaction.class);
                intent.putExtra("transFlag","trans");
                intent.putExtra("transaction",transactionModel);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), transactionModel.getNote() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void setDataAndPrepareAdapter() {
        transactionCRUD = new TransactionCRUD(this);
        allTransactionsList = transactionCRUD.getAllTransactions();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_transaction);
        transactionAdapter = new TransactionAdapter(allTransactionsList);

        transactionAdapter.notifyDataSetChanged();
    }


}
