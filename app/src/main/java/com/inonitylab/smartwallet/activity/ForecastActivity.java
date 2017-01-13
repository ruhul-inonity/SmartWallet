package com.inonitylab.smartwallet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.database.TransactionCRUD;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {
    TransactionCRUD transactionCRUD;
    ArrayList<String[]> report = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);

        transactionCRUD = new TransactionCRUD(this);
        report = transactionCRUD.getIncomeSum();

    }
}
