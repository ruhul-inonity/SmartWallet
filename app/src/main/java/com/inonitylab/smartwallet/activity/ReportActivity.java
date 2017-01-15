package com.inonitylab.smartwallet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.adapter.BudgetAdapter;
import com.inonitylab.smartwallet.adapter.ExpenseReportAdapter;
import com.inonitylab.smartwallet.adapter.IncomeReportAdapter;
import com.inonitylab.smartwallet.adapter.TransactionAdapter;
import com.inonitylab.smartwallet.database.TransactionCRUD;
import com.inonitylab.smartwallet.model.ReportModel;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    Spinner spinnerMonth;
    Button buttonGo;
    long month;
    String mon = "01-2017";

    TransactionCRUD transactionCRUD;
    ArrayList<ReportModel> allIncomeList,allExpenseList,allBudget;
    IncomeReportAdapter incomeAdapter;
    ExpenseReportAdapter expenseAdapter;
    BudgetAdapter budgetAdapter;
    RecyclerView recyclerViewIncome,recyclerViewExpense,recyclerViewBudget;

    ArrayAdapter<String> monthAdapter;
    ArrayList<String> monthList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        spinnerMonth = (Spinner) findViewById(R.id.spnMonthPicker);
        buttonGo = (Button) findViewById(R.id.buttonReport);
        setDataAndPrepareAdapter();



        monthList.add("january");
        monthList.add("February");
        monthList.add("March");
        monthAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, monthList);
        spinnerMonth.setAdapter(monthAdapter);

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month = spinnerMonth.getSelectedItemId();
                month = month+1;
                 mon = "0"+String.valueOf(month)+"-2017";
                Log.d("month check","................... "+mon);
                showReport();
            }
        });
    }

    public void showReport(){
        recyclerViewIncome.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewIncome.setLayoutManager(mLayoutManager);
        recyclerViewIncome.setItemAnimator(new DefaultItemAnimator());
        recyclerViewIncome.setAdapter(incomeAdapter);

        recyclerViewExpense.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManagerEx = new LinearLayoutManager(getApplicationContext());
        recyclerViewExpense.setLayoutManager(mLayoutManagerEx);
        recyclerViewExpense.setItemAnimator(new DefaultItemAnimator());
        recyclerViewExpense.setAdapter(expenseAdapter);

        recyclerViewBudget.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManagerBud = new LinearLayoutManager(getApplicationContext());
        recyclerViewBudget.setLayoutManager(mLayoutManagerBud);
        recyclerViewBudget.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBudget.setAdapter(budgetAdapter);
    }

    private void setDataAndPrepareAdapter() {
        //month = spinnerMonth.getSelectedItemId();
       // month = month+1;
       // String mon = "0"+String.valueOf(month)+"-2017";
        Log.d("month check","................... "+mon);
        transactionCRUD = new TransactionCRUD(this);
        allIncomeList = transactionCRUD.getReport(mon, "Income");
        allExpenseList = transactionCRUD.getReport(mon, "Expense");
        allBudget = new ArrayList<>();
        getBudgets();
        recyclerViewIncome = (RecyclerView) findViewById(R.id.recycler_income_report);
        recyclerViewExpense = (RecyclerView) findViewById(R.id.recycler_expense_report);
        recyclerViewBudget = (RecyclerView) findViewById(R.id.recycler_budget_report);
        incomeAdapter = new IncomeReportAdapter(allIncomeList);
        expenseAdapter = new ExpenseReportAdapter(allExpenseList);
        budgetAdapter = new BudgetAdapter(allBudget);

        incomeAdapter.notifyDataSetChanged();
        expenseAdapter.notifyDataSetChanged();
        budgetAdapter.notifyDataSetChanged();
    }

    private void getBudgets() {
        TransactionCRUD transactionCRUD = new TransactionCRUD(this);
        ArrayList<String[]> report = transactionCRUD.getBalanceStatement("2017/01/01", "2017/01/30", "Expense");
        int spentAmount = 0;

        for (int i = 0; i < report.size(); i++){
            String categoryName = report.get(i)[0];
            double spent = Double.valueOf(report.get(i)[1]);
            double maxBudget = transactionCRUD.getMaxBudget(categoryName);
            ReportModel reportModel = new ReportModel(categoryName,maxBudget,spent);
            allBudget.add(reportModel);
        }
    }

}
