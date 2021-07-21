package com.example.smarttumbler.statistic;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smarttumbler.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.smarttumbler.botol.Botol;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class StatisticFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_statistic, container, false);
        BarChart barChart = root.findViewById(R.id.barChart);

        ArrayList<BarEntry> airMinum = new ArrayList<>();
        airMinum.add(new BarEntry(2010,200));
        airMinum.add(new BarEntry(2011,200));
        airMinum.add(new BarEntry(2012,200));
        airMinum.add(new BarEntry(2013,1000));
        airMinum.add(new BarEntry(2014,3000));
        airMinum.add(new BarEntry(2015,2000));
        airMinum.add(new BarEntry(2016,1800));

        BarDataSet barDataSet = new BarDataSet(airMinum, "Jumlah Air Minum");
        barDataSet.setBarBorderColor(ColorTemplate.PASTEL_COLORS[1]);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText(".");
        barChart.animateY(2000);

        return root;

    }
}