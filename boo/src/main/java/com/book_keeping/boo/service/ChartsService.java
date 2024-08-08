package com.book_keeping.boo.service;

import com.book_keeping.boo.commons.Bill_type;
import com.book_keeping.boo.controller.response.entity.charts.Bar;
import com.book_keeping.boo.controller.response.entity.charts.BaseChart;
import com.book_keeping.boo.controller.response.entity.charts.Line;
import com.book_keeping.boo.controller.response.entity.charts.Pie;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ChartsService {
    @Autowired
    RecordService recordService;

    // 获取图表数据
    public BaseChart getChartsData(String chart_type, String time_type, String start_time, String end_time, Integer userId) {
        BaseChart baseChart = new BaseChart();
        switch (chart_type) {
            case "line":
                baseChart = getLineData(start_time, time_type, end_time, userId);
                break;
            case "pie":
                baseChart = getPieData(start_time, time_type, end_time, userId);
                break;
            case "bar":
                baseChart = getBarData(start_time, time_type, end_time, userId);
                break;
            default:
                break;
        }
        return baseChart;
    }

    // 获取折线图数据
    public Line getLineData(String start_time, String time_type, String end_time, Integer userId) {
        ArrayList<String> xAxisData = new ArrayList<>();

        String[] seriesName = {"收入", "支出"};

        ArrayList<ArrayList<Double>> seriesData = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> incomeData = new ArrayList<Double>();
        ArrayList<Double> expendData = new ArrayList<Double>();
//        incomeData.add(120.0);
//        incomeData.add(132.2);
//        incomeData.add(101.1);
//        incomeData.add(134.5);
//        incomeData.add(90.0);
//        incomeData.add(230.0);
//        incomeData.add(210.0);
//        expendData.add(220.0);
//        expendData.add(182.2);
//        expendData.add(191.1);
//        expendData.add(234.5);
//        expendData.add(290.0);
//        expendData.add(330.0);
//        expendData.add(310.0);
//        seriesData.add(incomeData);
//        seriesData.add(expendData);

        if (time_type.equals("year")) {
            getDataMonthByMonth(start_time, end_time, userId, incomeData, expendData, xAxisData);
        } else {
            getDataDayByDay(start_time, end_time, userId, incomeData, expendData, xAxisData);
        }
        seriesData.add(incomeData);
        seriesData.add(expendData);
        return new Line(xAxisData, seriesName, seriesData);
    }

    public void getDataDayByDay(String start_time, String end_time, Integer userId, ArrayList<Double> incomeData, ArrayList<Double> expendData, ArrayList<String> xAxisData) {
        LocalDate start = LocalDate.parse(start_time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(end_time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        while (start.isBefore(end.plusDays(1))) {
            // 获取每一天的x轴数据
            xAxisData.add(start.toString());
            // 获取每一天的收入数据
            Double money = recordService.TotalMoneyByUserIdAndBetweenDate(1, start.toString(), start.toString(), Bill_type.收入.getType());
            incomeData.add(money == null ? 0 : money);
            // 获取每一天的支出数据
            money = recordService.TotalMoneyByUserIdAndBetweenDate(1, start.toString(), start.toString(), Bill_type.支出.getType());
            expendData.add(money == null ? 0 : money);
            System.out.println(start);
            start = start.plusDays(1);
        }
    }

    public void getDataMonthByMonth(String start_time, String end_time, Integer userId, ArrayList<Double> incomeData, ArrayList<Double> expendData, ArrayList<String> xAxisData) {
        LocalDate start = LocalDate.parse(start_time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(end_time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for (int i = 1; i <= 12; i++) {
            // 获取每一月的x轴数据
            xAxisData.add(i + "月");
            // 获取每一月的收入数据
            Double money = recordService.TotalMoneyByUserIdAndBetweenDate(1, start.withMonth(i).withDayOfMonth(1).toString(), start.withMonth(i).withDayOfMonth(start.withMonth(i).lengthOfMonth()).toString(), Bill_type.收入.getType());
            incomeData.add(money == null ? 0 : money);
            // 获取每一月的支出数据
            money = recordService.TotalMoneyByUserIdAndBetweenDate(1, start.withMonth(i).withDayOfMonth(1).toString(), start.withMonth(i).withDayOfMonth(start.withMonth(i).lengthOfMonth()).toString(), Bill_type.支出.getType());
            expendData.add(money == null ? 0 : money);
        }
    }

    // 获取饼图数据
    public Pie getPieData(String start_time, String time_type, String end_time, Integer userId) {
        return null;
    }

    // 获取柱状图数据
    public Bar getBarData(String start_time, String time_type, String end_time, Integer userId) {
        return null;
    }
}
