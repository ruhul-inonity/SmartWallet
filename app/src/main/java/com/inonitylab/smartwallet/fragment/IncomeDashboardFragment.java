package com.inonitylab.smartwallet.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.inonitylab.smartwallet.R;
import com.inonitylab.smartwallet.database.TransactionCRUD;

import java.util.ArrayList;
import java.util.List;

public class IncomeDashboardFragment extends Fragment {

    private ExpenseDashboardFragment.OnFragmentInteractionListener mListener;
    private PieChart pieChart;
    private TransactionCRUD transactionCRUD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_dashboard, container, false);

        pieChart = (PieChart) view.findViewById(R.id.piChartIncome);
        transactionCRUD = new TransactionCRUD(view.getContext());
        incomePieChart("2017/01/01","2017/01/30");

        return view;
    }

    public IncomeDashboardFragment() {
    }

    public static IncomeDashboardFragment newInstance(String param1, String param2) {
        IncomeDashboardFragment fragment = new IncomeDashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void incomePieChart(String fromDate,String toDate){
        List<PieEntry> entries = new ArrayList<>();
        ArrayList<String[]> report = transactionCRUD.getBalanceStatement(fromDate, toDate, "Income");

        for (int i = 0; i < report.size(); i++){
            if (Double.valueOf(report.get(i)[1]) > 0){
                String categoryName = report.get(i)[0];
                float totalAmount = Float.valueOf(report.get(i)[1]);
                Log.d("getBalanceStatement","........................... category name "+ categoryName+" balance "+ totalAmount);
                entries.add(new PieEntry(totalAmount,categoryName));    //entries.add(new PieEntry(4805f, "Blue"));
            }
        }
        PieDataSet set = new PieDataSet(entries, "Income");
        pieChart.setCenterText("January 2017");
        //add color to chart
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.DKGRAY);
        colors.add(Color.CYAN);
        colors.add(Color.LTGRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        set.setColors(colors);

        //spacing between slices
        set.setSliceSpace(4);
        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
