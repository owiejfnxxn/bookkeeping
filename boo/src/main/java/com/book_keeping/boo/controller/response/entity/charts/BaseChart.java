package com.book_keeping.boo.controller.response.entity.charts;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class BaseChart {
    @Data
    public class xAxis {
        // x轴 类型
        private String type;
        // x轴 数据
        private ArrayList<String> data;

        public xAxis(String type,ArrayList<String> data) {
            this.type = type;
            this.data = data;
        }
    }
    @Data
    public class yAxis {
        // y轴 类型
        private String type;

        public yAxis(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
    @Data
    public class series {
        // 系列名称
        private String name;
        // 系列类型
        private String type;
        // 系列数据
        private ArrayList<Double> data;

        public series(String name, String type,ArrayList<Double> data) {
            this.name = name;
            this.type = type;
            this.data = data;
        }
    }
    // 行
    protected xAxis xAxis;
    // 列
    protected yAxis yAxis;
    // 系列
    protected ArrayList<series> series;
    // 一些默认值
    {
        this.yAxis = new yAxis("value");
        this.series = new ArrayList<>();
    }
}
