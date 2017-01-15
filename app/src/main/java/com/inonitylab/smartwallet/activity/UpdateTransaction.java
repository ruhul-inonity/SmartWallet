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

import java.util.ArrayList;

public class UpdateTransaction extends AppCompatActivity {

    EditText editTextAmount,editTextNote;
    TextView textDate,textCategory;
    CheckBox checkRecurring;
    Spinner spinnerRecurring;
    Spinner spinnerCategory;
    Button buttonUpdateTransaction, buttonDeleteTransaction;
    CheckBox recurringOption;
    TransactionModel transactionModel;

    String flag = "0";

    private String amount,date,note,category,categoryType,recurring;

    final int DIALOG_ID = 10;
    int year, month, day;
    String  selectedMonth, selectedDay,selectedYear;
    int transId;

    ArrayAdapter<String> recurringAdapter,categoryAdapter;
    ArrayList<String> recurringTime = new ArrayList<>();
    ArrayList<String> categoryNames = new ArrayList<>();
    ArrayList<Categories> allCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_transaction);

        getCategories();

        editTextAmount = (EditText) findViewById(R.id.editTextUpdateTransactionAmount);
        textDate = (TextView) findViewById(R.id.textDateUpdate);
        textCategory = (TextView) findViewById(R.id.textUpdateCategory);
        editTextNote = (EditText) findViewById(R.id.textUpdateNote);
        // spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        checkRecurring = (CheckBox) findViewById(R.id.transaction_checkbox_Update);
        spinnerRecurring = (Spinner) findViewById(R.id.spinner_transaction_RecurringUpdate);
        buttonUpdateTransaction = (Button) findViewById(R.id.buttonUpdateTransaction);
        buttonDeleteTransaction = (Button) findViewById(R.id.buttonDeleteTransaction);

        String transflag = "0";

        try {
            transflag = getIntent().getStringExtra("transFlag");
        } catch (Exception e){
            Log.d("TAG", e+"");
        }
        Log.d("TAG", "......................."+transflag);
        if (String.valueOf(transflag).equals("trans")){

            transactionModel = (TransactionModel) getIntent().getSerializableExtra("transaction");
            transId = transactionModel.get_id();
            editTextAmount.setText(String.valueOf(transactionModel.getAmount()));
            textDate.setText(transactionModel.getDate());
            editTextNote.setText(transactionModel.getNote());
            textCategory.setText(getIntent().getStringExtra("categoryName"));
            Log.d("TAGGG","______________----------------------____________ transaction categoryName: "+transactionModel.getCategoryName());
        }else {
            // Intent intent = new Intent();
            try {
                category = getIntent().getExtras().getString("category");
                categoryType = getIntent().getExtras().getString("categoryType");
                transId = Integer.parseInt(getIntent().getExtras().getString("transID"));
                flag = getIntent().getExtras().getString("flag");
                Log.d("category and flag", "............................" + flag + category);
                textCategory.setText(category);
                if (!flag.isEmpty() && flag.equals("pickCategory")) {

                    Bundle pickedBundle = getIntent().getExtras();
                    editTextAmount.setText(pickedBundle.getString("amount"));
                    textDate.setText(pickedBundle.getString("date").toString());
                    editTextNote.setText(pickedBundle.getString("note"));
                    textCategory.setText(category);
                    Log.d("category and flag", "............................" + flag + category + pickedBundle.getString("date"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        recurringTime.add("Daily");
        recurringTime.add("Weekly");
        recurringTime.add("Monthly");
        recurringAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row_spinner, recurringTime);
        //categoryAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row_spinner, categoryNames);
        spinnerRecurring.setAdapter(recurringAdapter);
        // spinnerCategory.setAdapter(categoryAdapter);

        buttonUpdateTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTransaction();
                Intent intent = new Intent(UpdateTransaction.this,Transaction.class);
                startActivity(intent);
            }
        });

        buttonDeleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTransaction();
                Intent intent = new Intent(UpdateTransaction.this,Transaction.class);
                startActivity(intent);
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
                bundle.putString("fromActivity","updateTransaction");
                bundle.putString("amount",editTextAmount.getText().toString());
                bundle.putString("date",textDate.getText().toString());
                bundle.putString("note",editTextNote.getText().toString());
                bundle.putString("category",textCategory.getText().toString());
                bundle.putString("transID", String.valueOf(transId));
                Intent intent = new Intent(UpdateTransaction.this,PickCategoryActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        checkRecurring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recurringOption.isChecked()){
                    spinnerRecurring.setVisibility(View.VISIBLE);
                }
                else spinnerRecurring.setVisibility(View.GONE);
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

    private void updateTransaction() {
        TransactionCRUD transactionCRUD = new TransactionCRUD(this);
        CategoriesCRUD categoriesCRUD = new CategoriesCRUD(this);

        Double amount = Double.valueOf(editTextAmount.getText().toString());
        int categoryId = categoriesCRUD.getCategoryId(category);
        String date = textDate.getText().toString();
        String note = editTextNote.getText().toString();

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAmount(amount);
        transactionModel.setCategoryId(categoryId);
        transactionModel.setDate(date);
        transactionModel.setNote(note);
        transactionModel.set_id(transId);
        transactionModel.setCategoryType(categoryType);

        long flag =   transactionCRUD.updateTransaction(transactionModel);
        if (flag>0){
            Toast.makeText(getApplicationContext(),"Successfully updated",Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteTransaction(){
        TransactionCRUD transactionCRUD = new TransactionCRUD(this);

        long flag =   transactionCRUD.deleteTransaction(transId);
        if (flag>0){
            Toast.makeText(getApplicationContext(),"Successfully deleted",Toast.LENGTH_SHORT).show();
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