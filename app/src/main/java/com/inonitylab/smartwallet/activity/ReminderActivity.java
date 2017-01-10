package com.inonitylab.smartwallet.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.database.CategoriesCRUD;
import com.inonitylab.smartwallet.database.TransactionCRUD;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.util.*;
import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {


    EditText editReminderNote,editReminderAmount;
    TextView txtCategory,txtDate,txtTime;
    CheckBox checkIsRepeat;
    Button button;
    Spinner spinnerRepeat;

    ArrayAdapter<String> repeatAdapter;
    ArrayList<String> repeatTime = new ArrayList<>();

    String flag = "0";
    private int mHour, mMinute;
    private String amount,date,note,category,categoryType,recurring;
    private String  selectedMonth, selectedDay,selectedYear;
    final int DIALOG_ID_DATE = 1;
    final int DIALOG_ID_TIME = 2;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        editReminderNote = (EditText) findViewById(R.id.editTextReminderNote);
        editReminderAmount = (EditText) findViewById(R.id.editTextReminderAmount);
        txtCategory = (TextView) findViewById(R.id.textViewCategory);
        txtDate = (TextView) findViewById(R.id.textReminderDate);
        txtTime = (TextView) findViewById(R.id.textReminderTime);
        checkIsRepeat = (CheckBox) findViewById(R.id.checkReminder);
        spinnerRepeat = (Spinner) findViewById(R.id.spinnerReminderRepeat);
        button = (Button) findViewById(R.id.buttonAddReminder);

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID_DATE);
            }
        });

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultTime();
                inputTime();
            }
        });

        checkIsRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIsRepeat.isChecked()){
                    spinnerRepeat.setVisibility(View.VISIBLE);
                }
                else spinnerRepeat.setVisibility(View.GONE);
            }
        });
        repeatTime.add("Daily");
        repeatTime.add("Weekly");
        repeatTime.add("Monthly");
        repeatAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, repeatTime);
        spinnerRepeat.setAdapter(repeatAdapter);

        txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fromActivity","Reminder");
                bundle.putString("note",editReminderNote.getText().toString());
                bundle.putString("amount",editReminderAmount.getText().toString());
                bundle.putString("date",txtDate.getText().toString());
                bundle.putString("category",txtCategory.getText().toString());
                bundle.putString("repeat",spinnerRepeat.getSelectedItem().toString());
                Intent intent = new Intent(ReminderActivity.this,PickCategoryActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReminder();
            }
        });
        try {
            Bundle pickedBundle = getIntent().getExtras();
            if (pickedBundle != null && pickedBundle.isEmpty()){
                category = pickedBundle.getString("category");
                categoryType = pickedBundle.getString("categoryType");
                //flag = getIntent().getExtras().getString("flag");
                Log.d("category and flag","............................"+flag + category);
                txtCategory.setText(category);
                if (!flag.isEmpty() && flag.equals("pickCategory")){

                    editReminderAmount.setText(pickedBundle.getString("amount"));
                    txtDate.setText(pickedBundle.getString("date").toString());
                    editReminderNote.setText(pickedBundle.getString("note"));
                    txtCategory.setText(category);
                    Log.d("category and flag","............................"+flag + category+pickedBundle.getString("date"));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bundle pickedBundle = getIntent().getExtras();
            categoryType = pickedBundle.getString("categoryType");
            flag = pickedBundle.getString("flag");
            Log.d("category and flag","............................"+flag + category);
            if (!flag.isEmpty() && flag.equals("reminder")){

                txtCategory.setText(pickedBundle.getString("category"));
                editReminderAmount.setText(pickedBundle.getString("amount"));
                txtDate.setText(pickedBundle.getString("date").toString());
                editReminderNote.setText(pickedBundle.getString("note"));
                Log.d("category and flag","............................"+flag + category+pickedBundle.getString("date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setDefaultTime() {
        Calendar c;
        c = java.util.Calendar.getInstance();
        mHour = c.get(java.util.Calendar.HOUR_OF_DAY);
        mMinute = c.get(java.util.Calendar.MINUTE);

    }

    private void inputTime() {
        //defTime = 1;
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    private void addReminder() {
        TransactionCRUD transactionCRUD = new TransactionCRUD(this);
        CategoriesCRUD categoriesCRUD = new CategoriesCRUD(this);

        //Log.d("....check ","...... id check "+categoryId);
        Double amount = Double.valueOf(editReminderAmount.getText().toString());
        int categoryId = categoriesCRUD.getCategoryId(category);
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        String note = editReminderNote.getText().toString();

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAmount(amount);
        transactionModel.setCategoryId(categoryId);
        transactionModel.setDate(date);
        transactionModel.setTime(time);
        transactionModel.setNote(note);
        transactionModel.setCategoryType(categoryType);

        long flag =   transactionCRUD.insertReminder(transactionModel);
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
                    txtDate.setText(date);
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

        if (id == DIALOG_ID_DATE)
            return new DatePickerDialog(this, dpListener, year, month-1, day);
        return null;
    }
}
