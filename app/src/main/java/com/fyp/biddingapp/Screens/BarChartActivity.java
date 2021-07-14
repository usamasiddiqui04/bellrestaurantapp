package com.fyp.biddingapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.fyp.biddingapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

public class BarChartActivity extends AppCompatActivity {

    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = findViewById(R.id.chart);

        // calling method to get bar entries.
        getBarEntries();
        XAxis xAxis = barChart.getXAxis();
        final String[] labels = new String[] {"Dummy", "Jan", "Feb", "March", "April", "May",
                "June"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Bid Results");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);



    }

    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar 
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, getRandomNumber(0,9)));
        barEntriesArrayList.add(new BarEntry(2f, getRandomNumber(0,9)));
        barEntriesArrayList.add(new BarEntry(3f, getRandomNumber(0,9)));
        barEntriesArrayList.add(new BarEntry(4f, getRandomNumber(0,9)));
        barEntriesArrayList.add(new BarEntry(5f, getRandomNumber(0,9)));
        barEntriesArrayList.add(new BarEntry(6f, getRandomNumber(0,9)));
    }

    private int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
}