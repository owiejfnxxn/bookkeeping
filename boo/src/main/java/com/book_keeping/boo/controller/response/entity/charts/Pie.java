package com.book_keeping.boo.controller.response.entity.charts;

import java.util.ArrayList;

public class Pie extends BaseChart{
    public Pie(ArrayList<String> xAxisData, String[] seriesName, ArrayList<ArrayList<Double>> seriesData) {
        this.xAxis.setData(xAxisData);
    }
}
