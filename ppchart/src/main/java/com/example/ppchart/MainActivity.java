package com.example.ppchart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<dataObject> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PPChart ppChart = new PPChart(this);
        for (int i = 1; i < 10; i++) {
            dataObject dataObject = new dataObject();
            dataObject.setHappenTime("2017-4-0" + i);
            dataObject.setNum(80);
            Log.i("wjh", "onCreate: " + dataObject.toString());
            data.add(dataObject);
        }

        Log.i("wjh", "size: "+data.size());
        ppChart.setData(data);

    }
}
