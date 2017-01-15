package com.inonitylab.smartwallet.activity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.adapter.Demobase;
import com.inonitylab.smartwallet.database.DBHelper;
import com.inonitylab.smartwallet.database.TransactionCRUD;

import java.util.ArrayList;

public class ForecastActivity extends Demobase {
    TransactionCRUD transactionCRUD;
    ArrayList<Double> incomeReport = new ArrayList<>();
    ArrayList<Double> expenseReport = new ArrayList<>();

    ArrayList<Double> incomeByMonth = new ArrayList<>();
    ArrayList<Double> expenseByMonth = new ArrayList();
    ArrayList<Double> netIncome = new ArrayList<>();
    ArrayList<Double> months = new ArrayList<>();
    private CombinedChart mChart;
    private final int itemcount = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);
        Log.d("Forecast","...................olla from forecast");
        transactionCRUD = new TransactionCRUD(this);


        prepareDataSet();

        showChart();
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

    private void showChart() {

        mChart = (CombinedChart) findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE
        });


        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonths[(int) value % mMonths.length];
            }
        });

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());
        data.setValueTypeface(mTfLight);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }



    private void prepareDataSet() {
        incomeReport = transactionCRUD.getIncome();
        expenseReport = transactionCRUD.getExpense();

        for (int i = 0; i<expenseReport.size(); i++){
            expenseByMonth.add((Double) expenseReport.get(i));
            incomeByMonth.add((Double) incomeReport.get(i));
            months.add((double) i);
            Log.d("FROMFOREAST","-------------------expense "+expenseByMonth.get(i));
            Log.d("FROMFORECAST", "---------------- income "+incomeByMonth.get(i));
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
    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < itemcount; index++){
            float point = getRandom(15, 5);
            entries.add(new Entry(index + 0.5f, point));
        }

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        for (int index = 0; index < itemcount; index++) {
            float barPoint = getRandom(5,10);
            entries1.add(new BarEntry(0, barPoint));

            // stacked
            entries2.add(new BarEntry(0, new float[]{getRandom(13, 12), getRandom(13, 12)}));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }
/*    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < netIncome.size(); index++){
            float point = getRandom(15, 5);
           // double p = netIncome.get(index);
           // float point = (float)p;
            entries.add(new Entry(index + 0.5f, point));
        }

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();


       *//* for (int i = 0; i<expenseReport.size(); i++){
            expenseByMonth.add((Double) expenseReport.get(i));
            //incomeByMonth.add((Double) incomeReport.get(i));
           // months.add((double) i);
        }*//*
        for (int index = 0; index < itemcount; index++) {
            //double x = expenseReport.get(index);
            float barPoint = getRandom(5,15);
            entries1.add(new BarEntry(0, barPoint));

            // stacked
           // entries2.add(new BarEntry(0, new float[]{getRandom(13, 12), getRandom(13, 12)}));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }*/

}