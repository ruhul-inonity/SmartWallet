package com.inonitylab.smartwallet.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.database.CategoriesCRUD;
import com.inonitylab.smartwallet.database.TransactionCRUD;
import com.inonitylab.smartwallet.model.Categories;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.ArrayList;

public class BudgetActivity extends AppCompatActivity {


    Spinner spnSelectCategory;
    Spinner spnCategoryReport;
    EditText editAmount;
    TextView txtFromDate,txtToDate;
    TextView txtSpentAmount,txtMaxAmount;
    Button buttonSaveBudget,buttonWatchBudgetReport;
    LinearLayout linearLayoutBudgetEntry;
    ArrayAdapter<String> categoryAdapter,categoryBudgetAdapter;
    ArrayList<String> categoryNames = new ArrayList<>();
    ArrayList<Categories> allCategories = new ArrayList<>();

    final int DIALOG_FROM = 1,DIALOG_TO = 2;
    String date;
    int dateFlag;
    int year, month, day;
    String  selectedMonth, selectedDay,selectedYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBudget);
        setSupportActionBar(toolbar);

        getCategories();

        spnSelectCategory = (Spinner) findViewById(R.id.spnSelectCategory);
        spnCategoryReport = (Spinner) findViewById(R.id.spnSelectCategoryToSeeBud);
        editAmount = (EditText) findViewById(R.id.editTextBudgetAmount);
        txtFromDate = (TextView) findViewById(R.id.textViewBudgetFromTime);
        txtSpentAmount = (TextView) findViewById(R.id.textSpentAmount);
        txtMaxAmount = (TextView) findViewById(R.id.textMaxAmount);
        txtToDate = (TextView) findViewById(R.id.textViewBudgetToTime);
        buttonSaveBudget = (Button) findViewById(R.id.buttonAddBudget);
        buttonWatchBudgetReport = (Button) findViewById(R.id.buttonWatchBudget);
        categoryAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row_spinner, categoryNames);
        categoryBudgetAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row_spinner, categoryNames);
        spnSelectCategory.setAdapter(categoryAdapter);
        spnCategoryReport.setAdapter(categoryBudgetAdapter);

        buttonSaveBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBudgetToDb();
            }
        });
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFlag = 0;
                showDialog(DIALOG_FROM);
            }
        });
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFlag = 1;
                showDialog(DIALOG_TO);
            }
        });
        buttonWatchBudgetReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReport();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddBudget);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               linearLayoutBudgetEntry = (LinearLayout) findViewById(R.id.linearLayoutAddCategory);
                linearLayoutBudgetEntry.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showReport() {
        TransactionCRUD transactionCRUD = new TransactionCRUD(this);
        ArrayList<String[]> report = transactionCRUD.getBalanceStatement("2017/01/01", "2017/01/30", "Expense");
        String category = spnCategoryReport.getSelectedItem().toString();
        Log.d("Selected category","................. "+category);
        int spentAmount = 0;
        int maxBudget = (int) transactionCRUD.getMaxBudget(category);

        for (int i = 0; i < report.size(); i++){
            String categoryName = report.get(i)[0];
            if (categoryName.equals(category)){
                 double x = Double.valueOf(report.get(i)[1]);
                spentAmount = (int) x;
                }
        }
        AnimateHorizontalProgressBar progressBar = (AnimateHorizontalProgressBar) findViewById(R.id.animate_progress_bar);
        progressBar.setMax(maxBudget);
        progressBar.setProgressWithAnim(spentAmount);
        txtSpentAmount.setText(new StringBuilder().append("Spent Amount :").append(spentAmount).toString());
        txtMaxAmount.setText(new StringBuilder().append("Max Amount :").append(maxBudget).toString());
    }

    private DatePickerDialog.OnDateSetListener dpListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year_x, int monthOfYear, int dayOfMonth) {
                    year = year_x;
                    month = monthOfYear+1;
                    day = dayOfMonth;
                    if (month < 10) {
                        selectedMonth = "0" + String.valueOf(
                                month);
                    } else
                        selectedMonth = String.valueOf(month);

                    if (day < 10) {
                        selectedDay = "0" + String.valueOf(day);
                    } else
                        selectedDay = String.valueOf(day);
                    selectedYear = String.valueOf(year);
                    date = selectedYear + "/" + selectedMonth + "/" + selectedDay;
                    Log.d("Date picker,vou.ent","............................ date:"+date+" schedule:"+selectedDay+" "+selectedMonth+" "+selectedYear);

                    if (dateFlag == 0){
                        txtFromDate.setText(date);
                    }else {
                        txtToDate.setText(date);
                    }
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        year = calendar.get(java.util.Calendar.YEAR);
        month = calendar.get(java.util.Calendar.MONTH) + 1;
        if (month < 10) {
            selectedMonth = "0" + String.valueOf(month);
        } else
            selectedMonth = String.valueOf(month);


        day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        if (day < 10) {
            selectedDay = "0" + String.valueOf(day);
        } else
            selectedDay = String.valueOf(day);

        if (id == DIALOG_TO){
            return new DatePickerDialog(this, dpListener, year, month-1, day);
        }else if (id == DIALOG_FROM){
            return new DatePickerDialog(this, dpListener, year, month-1, day);
        }
        return null;
    }

    private void getCategories() {

        CategoriesCRUD categoriesCRUD = new CategoriesCRUD(this);
        allCategories = categoriesCRUD.getExpenseCategories();

        for (Categories c : allCategories){
            categoryNames.add(c.getCategoryName());
            Log.d("getCategories ","..................... "+c.getCategoryName());
        }
    }
    private void saveBudgetToDb() {
        TransactionModel transactionModel = new TransactionModel();
        TransactionCRUD transactionCRUD = new TransactionCRUD(this);
        String category = spnSelectCategory.getSelectedItem().toString();
        double amount = Double.parseDouble(editAmount.getText().toString());
        String fromDate = txtFromDate.getText().toString();
        String toDate = txtToDate.getText().toString();

        transactionModel.setCategoryName(category);
        transactionModel.setAmount(amount);
        transactionModel.setFromDate(fromDate);
        transactionModel.setToDate(toDate);
        transactionModel.setCategoryType("Expense");

       long a = transactionCRUD.insertBudget(transactionModel);
        if (a == 1){
            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
        }
        linearLayoutBudgetEntry.setVisibility(View.GONE);
    }
}
