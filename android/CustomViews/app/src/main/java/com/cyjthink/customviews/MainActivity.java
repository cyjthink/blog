package com.cyjthink.customviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cyjthink.customviews.line_chart.LineChartView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.line_chart)
    LineChartView lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        lineChart.setmPointValues(new int[]{603, 706, 809, 919});
    }
}
