package com.fyp.biddingapp.Screens

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fyp.biddingapp.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class PieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)
        drawChart()
    }

    private fun drawChart() {
        val pieChart = findViewById<PieChart>(R.id.pieChart)
        pieChart.setUsePercentValues(true)
        val yvalues = ArrayList<PieEntry>()
        yvalues.add(PieEntry(8f, "January", 0))
        yvalues.add(PieEntry(15f, "February", 1))
        yvalues.add(PieEntry(12f, "March", 2))
        yvalues.add(PieEntry(25f, "April", 3))
        yvalues.add(PieEntry(23f, "May", 4))
        yvalues.add(PieEntry(17f, "June", 5))
        val dataSet = PieDataSet(yvalues, "Bid Results")
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        pieChart.data = data
        val description = Description()
        description.text = "Pie Chart"
        pieChart.description = description
        pieChart.centerText = "Bids report %"
        pieChart.setCenterTextSize(22f)
        pieChart.isDrawHoleEnabled = true
        pieChart.transparentCircleRadius = 58f
        pieChart.holeRadius = 58f
        dataSet.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.DKGRAY)
    }

}