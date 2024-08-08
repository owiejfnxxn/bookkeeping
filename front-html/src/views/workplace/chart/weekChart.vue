<template>
    <el-container>
        <el-main style="background-color: white;min-height: 600px">
            <el-date-picker
                v-model="datePickerValue"
                type="week"
                format="yyyy 年 第 WW 周"
                placeholder="选择日期"
                @change="handleDatePicker">
            </el-date-picker>
            <center style="margin-top: 25px">
                <div ref="chartContainer" style="height: 500px; width: 1200px;">
                    <el-empty v-if="datePickerValue === ''"></el-empty>
                </div>

            </center>

        </el-main>
    </el-container>
</template>

<script>
    import echarts from "echarts";
    import {Message} from "element-ui";
    import dayjs from "dayjs";
    import * as chartApi from "@/api/homeAPI/chartApi";
    import {chartType} from "@/api/homeAPI/chartApi";

    export default {
        name: "",
        data() {
            return {
                datePickerValue: "",
            }
        },

        methods: {
            initChart(option) {
                this.chartInstance = echarts.init(this.$refs.chartContainer);
                this.chartInstance.setOption(option);
            },
            handleDatePicker(value) {
                //开始时间
                let startTime = dayjs(this.datePickerValue).startOf("week").format("YYYY-MM-DD");
                //结束时间
                let endTime = dayjs(this.datePickerValue).endOf("week").format("YYYY-MM-DD");
                //获取数据
                chartApi.getChartData(startTime,endTime,chartType.line,'week').then(res=> {
                    let optionData = res.data.data;
                    this.initChart(this.dealWithOptionData(optionData));
                }).catch(err=> {
                    Message.error(err);
                })
            },

            dealWithOptionData(optionData){
                let option={
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left'
                    },
                    series: [{
                        name: optionData.series[0].name,
                        type: optionData.series[0].type,
                        data: optionData.series[0].data,
                    },{
                        name: optionData.series[1].name,
                        type: optionData.series[1].type,
                        data: optionData.series[1].data,
                    }],
                    xAxis: {
                        type: optionData.xaxis.type,
                        data: optionData.xaxis.data,
                    },
                    yAxis: {
                        type: optionData.yaxis.type,
                        data: optionData.yaxis.data,
                    }
                }
                return option;

            }
        },
        created() {
            this.datePickerValue = dayjs();
            this.handleDatePicker(this.datePickerValue);
        }
    }
</script>

<style scoped>

</style>
