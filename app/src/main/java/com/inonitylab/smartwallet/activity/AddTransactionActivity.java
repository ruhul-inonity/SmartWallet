package com.inonitylab.smartwallet.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.database.CategoriesCRUD;
import com.inonitylab.smartwallet.database.TransactionCRUD;
import com.inonitylab.smartwallet.model.Categories;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.*;

public class AddTransactionActivity extends AppCompatActivity {

    EditText editTextAmount,editTextNote;
    TextView textDate,textCategory;
    CheckBox checkRecurring;
    Spinner spinnerRecurring;
    Spinner spinnerCategory;
    Button buttonAddTransaction;
    String flag = "0";

    private String amount,date,note,category,categoryType,recurring;

    final int DIALOG_ID = 1;
    int year, month, day;
    String  selectedMonth, selectedDay,selectedYear;

    ArrayAdapter<String> recurringAdapter,categoryAdapter;
    ArrayList<String> recurringTime = new ArrayList<>();
    ArrayList<String> categoryNames = new ArrayList<>();
    ArrayList<Categories> allCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        getCategories();

        editTextAmount = (EditText) findViewById(R.id.editTextTransactionAmount);
        textDate = (TextView) findViewById(R.id.textDateSelect);
        textCategory = (TextView) findViewById(R.id.textCategorySelect);
        editTextNote = (EditText) findViewById(R.id.textNote);
       // spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        checkRecurring = (CheckBox) findViewById(R.id.transaction_checkbox_recurringOption);
        spinnerRecurring = (Spinner) findViewById(R.id.spinner_transaction_Recurring);
        buttonAddTransaction = (Button) findViewById(R.id.buttonAddTransaction);

        // Intent intent = new Intent();
        try {
            category = getIntent().getExtras().getString("category");
            categoryType = getIntent().getExtras().getString("categoryType");
            flag = getIntent().getExtras().getString("flag");
            Log.d("category and flag","............................"+flag + category);
            textCategory.setText(category);
            if (!flag.isEmpty() && flag.equals("pickCategory")){

                Bundle pickedBundle = getIntent().getExtras();
                editTextAmount.setText(pickedBundle.getString("amount"));
                textDate.setText(pickedBundle.getString("date").toString());
                editTextNote.setText(pickedBundle.getString("note"));
                textCategory.setText(category);
                Log.d("category and flag","............................"+flag + category+pickedBundle.getString("date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        recurringTime.add("Daily");
        recurringTime.add("Weekly");
        recurringTime.add("Monthly");
        recurringAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row_spinner, recurringTime);
        categoryAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row_spinner, categoryNames);
        spinnerRecurring.setAdapter(recurringAdapter);
       // spinnerCategory.setAdapter(categoryAdapter);

        buttonAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTransaction();
            }
        });

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });

        textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("amount",editTextAmount.getText().toString());
                bundle.putString("date",textDate.getText().toString());
                bundle.putString("note",editTextNote.getText().toString());
                bundle.putString("category",textCategory.getText().toString());
                Intent intent = new Intent(AddTransactionActivity.this,PickCategoryActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void getCategories() {

        CategoriesCRUD categoriesCRUD = new CategoriesCRUD(this);
        allCategories = categoriesCRUD.getAll_Categories();

        for (Categories c : allCategories){
            categoryNames.add(c.getCategoryName());
        }
    }

    private void addTransaction() {
        TransactionCRUD transactionCRUD = new TransactionCRUD(this);

        Double amount = Double.valueOf(editTextAmount.getText().toString());
       // int categoryId = spinnerCategory.getSelectedItemPosition();
        String date = textDate.getText().toString();
        String note = editTextNote.getText().toString();

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAmount(amount);
       // transactionModel.setCategoryId(categoryId);
        transactionModel.setDate(date);
        transactionModel.setNote(note);
        transactionModel.setCategoryType(categoryType);

      long flag =   transactionCRUD.insertTransaction(transactionModel);
        if (flag>0){
            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
        }

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
                    textDate.setText(date);
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

        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpListener, year, month-1, day);
        return null;
    }

}
