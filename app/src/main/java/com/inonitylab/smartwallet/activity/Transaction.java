package com.inonitylab.smartwallet.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;

import java.util.ArrayList;

public class Transaction extends AppCompatActivity {
    private Spinner transaction_tag;
    private Spinner expenseType;
    private Spinner transactionRecurring;
    private TextView dateSelected;
    private ArrayList expenseTypes;
    private ArrayList transactionRecurringTypes;
    private Button dateBtn;
    private CheckBox recurringOption;
    private EditText transactionName;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getExpenseTypes();
        getTransactionRecurringTypes();

       /* dateSelected = (TextView) findViewById(R.id.transaction_date_selected);
        dateSelected.setVisibility(View.INVISIBLE);

        dateBtn = (Button) findViewById(R.id.transaction_Datebtn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize a new date picker dialog fragment
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });


        addButton = (Button) findViewById(R.id.transaction_Addbtn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Transaction.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Transaction.this, MainActivity.class));
            }

        });


        expenseType = (Spinner) findViewById(R.id.spinner_transaction_expenseType);
        transactionRecurring = (Spinner) findViewById(R.id.spinner_transaction_Recurring);
        transactionRecurring.setVisibility(View.GONE);
*/

        recurringOption = (CheckBox) findViewById(R.id.transaction_checkbox_recurringOption);
        recurringOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recurringOption.isChecked()){
                    transactionRecurring.setVisibility(View.VISIBLE);
                }
                else transactionRecurring.setVisibility(View.GONE);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Transaction.this,
                android.R.layout.simple_spinner_item,expenseTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseType.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Transaction.this,
                android.R.layout.simple_spinner_item,transactionRecurringTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionRecurring.setAdapter(adapter2);

    }

    private void getTransactionRecurringTypes() {
        transactionRecurringTypes = new ArrayList();
        transactionRecurringTypes.add("Daily");
        transactionRecurringTypes.add("Weekly");
        transactionRecurringTypes.add("Monthly");
        transactionRecurringTypes.add("Yearly");

    }

    private void getExpenseTypes() {
        expenseTypes = new ArrayList();
        expenseTypes.add("IncomeCategory");
        expenseTypes.add("ExpenseCategory");
    }



          /*  String startDate = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1)+"-"+String.valueOf(dayOfMonth);
        String endDate = startDate;*/
        //tvRange.setText("Date  : " + startDate);

}
