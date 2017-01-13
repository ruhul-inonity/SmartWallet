package com.inonitylab.smartwallet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.database.TransactionCRUD;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {
    TransactionCRUD transactionCRUD;
    ArrayList<String[]> incomeReport = new ArrayList<>();
    ArrayList<String[]> expenseReport = new ArrayList<>();

    ArrayList<Double> incomeByMonth = new ArrayList<>();
    ArrayList<Double> expenseByMonth = new ArrayList();
    ArrayList<Double> netIncome = new ArrayList<>();
    ArrayList<Double> months = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);
        Log.d("Forecast","...................olla from forecast");
        transactionCRUD = new TransactionCRUD(this);


        prepareDataSet();

/*
        incomeByMonth.add(3000.0);
        incomeByMonth.add(3000.0);
        incomeByMonth.add(2000.0);
        incomeByMonth.add(4000.0);
        incomeByMonth.add(3000.0);

        expenseByMonth.add(1000.0);
        expenseByMonth.add(2000.0);
        expenseByMonth.add(4000.0);
        expenseByMonth.add(2000.0);
        expenseByMonth.add(1000.0);

        months.add(1.0);
        months.add(2.0);
        months.add(3.0);
        months.add(4.0);
        months.add(5.0);
*/

        netIncomeCalculation(incomeByMonth, expenseByMonth);

        double pearsonCC = sum(multiply(valueMinusMeanValueCalculation(netIncome),valueMinusMeanValueCalculation(months)))
                /Math.sqrt(sum(squareValueCalculation(valueMinusMeanValueCalculation(netIncome))) * sum(squareValueCalculation(valueMinusMeanValueCalculation(months))));

        double stdDevIncomeY = stdDevCalculation(netIncome);
        double stdDevMonthX = stdDevCalculation(months);

        double b = (pearsonCC * stdDevIncomeY)/stdDevMonthX;

        double a = mean(netIncome) - (b * mean(months));

        Toast.makeText(getApplicationContext(), "stdDev Y = "+stdDevIncomeY+" a = "+a+"     b = "+b+"    pearsonCC: "+pearsonCC, Toast.LENGTH_LONG).show();

    }

    private void prepareDataSet() {
        incomeReport = transactionCRUD.getIncomeSum();
        expenseReport = transactionCRUD.getExpenseSum();

        for (int i = 0;i<expenseReport.size();i++){
            expenseByMonth.add(Double.valueOf(expenseReport.get(i)[1]));
            months.add((double) i);
        }

        for (int i = 0;i<incomeReport.size();i++){
            if (Double.valueOf(incomeReport.get(i)[1])!=null){

                incomeByMonth.add(0.0);
            }else {
                incomeByMonth.add(Double.valueOf(incomeReport.get(i)[1]));
            }

        }
    }

    private void netIncomeCalculation(ArrayList<Double> incomeByMonth, ArrayList<Double> expenseByMonth) {

        for (int i = 0; i < incomeByMonth.size(); i++) {
            netIncome.add(incomeByMonth.get(i) - expenseByMonth.get(i));
            //  Log.d("TAG", "--------netIncome: "+netIncome.get(i));
        }
    }

    protected ArrayList<Double> valueMinusMeanValueCalculation(ArrayList<Double> value) {
        double mean = mean(value);
        ArrayList<Double> valueMinusMeanValue = new ArrayList<>();
        for (int i =0; i<value.size();i++){
            valueMinusMeanValue.add(value.get(i)-mean);
        }
        return valueMinusMeanValue;

    }

    protected double mean(ArrayList<Double> Numbers) {
        double sum = sum(Numbers);
        double avg = sum / Numbers.size();
        return avg;
    }

    protected double sum(ArrayList<Double> Numbers) {
        double sum = 0;
        for (int i = 0; i < Numbers.size(); i++) {
            sum = sum + Numbers.get(i);
        }
        return sum;
    }

    protected ArrayList<Double> multiply(ArrayList<Double> Numbers1, ArrayList<Double> Numbers2){
        ArrayList<Double> multipliedValue = new ArrayList<>();
        for(int i = 0; i<Numbers1.size(); i++){
            multipliedValue.add(Numbers1.get(i)*Numbers2.get(i));
        }
        return multipliedValue;
    }

    protected ArrayList<Double> squareValueCalculation(ArrayList<Double> Numbers){
        ArrayList<Double> squaredValues = new ArrayList<>();
        for(int i = 0; i<Numbers.size(); i++){
            squaredValues.add(Numbers.get(i)*Numbers.get(i));
        }
        return squaredValues;
    }

    protected double stdDevCalculation(ArrayList<Double> Numbers){
//        Log.d("TAG", "--------stdDev: "+netIncome.get(i));
        return Math.sqrt(sum(squareValueCalculation(valueMinusMeanValueCalculation(Numbers)))/(Numbers.size()-1));
    }
}
