<template>
    <el-container>
        <el-main style="background-color: white;min-height: 600px">
            <el-date-picker
                    v-model="datePickerValue"
                    type="year"
                    format="yyyy 年"
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
    import dayjs from "dayjs";
    import * as chartApi from "@/api/homeAPI/chartApi";
    import {chartType} from "@/api/homeAPI/chartApi";
    import {Message} from "element-ui";
    import echarts from "echarts";

    export default {
        name: "",
        data() {
            return {
                datePickerValue: "",
            }
        },
        methods:{
            initChart(option) {
                this.chartInstance = echarts.init(this.$refs.chartContainer);
                this.chartInstance.setOption(option);
            },
            handleDatePicker(value) {
                let startTime = dayjs(value).startOf('year').format('YYYY-MM-DD');
                let endTime = dayjs(value).endOf('year').format('YYYY-MM-DD');
                chartApi.getChartData(startTime,endTime,chartType.line,'year').then(res=> {
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
            this.datePickerValue = dayjs().format("YYYY");
            this.handleDatePicker(this.datePickerValue);
        }
    }
</script>

<style scoped>

</style>
