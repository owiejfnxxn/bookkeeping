package com.book_keeping.boo.controller.response.entity.charts;

import java.util.ArrayList;

public class Line extends BaseChart{

    /**
     * @param xAxisData  x轴数据
     * @param seriesName 系列名称
     * @param seriesData 系列数据
     */
    public Line(ArrayList<String> xAxisData, String[] seriesName, ArrayList<ArrayList<Double>> seriesData) {
        this.xAxis = new xAxis("category", xAxisData);
        this.series = new ArrayList<series>();
        // 将seriesData转换成series
        for (int i = 0; i < seriesName.length; i++) {
            series series = new series(seriesName[i], "line", seriesData.get(i));
            this.series.add(series);
        }
    }
}
