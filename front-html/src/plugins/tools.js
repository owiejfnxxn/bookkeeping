import * as EIApi from "@/api/homeAPI/expenditureIncomeApi";
import echarts from "echarts";

export default function fetchData(api, params, successCallback) {
    EIApi[api](...params).then(res => {
        const data = res.data.data;
        successCallback(data);
    })
}


/**
 * @description 通用图表封装函数
 * @param {Ref} ref div的引用
 * @param {String} chartType 图表类型
 * @param {Object} data 图表数据
 * @param {String} title 图表标题
 * @param {String} xAxisName x轴名称
 * @param {String} yAxisName y轴名称
 * @param {Boolean} isStack 是否堆叠
 * @param {Object} options 其他配置项
 * @return {Object} echarts实例
 */
export function createChart(ref, chartType, data, title, xAxisName, yAxisName, isStack, options) {
    const chart = echarts.init(ref);
    const defaultOptions = {
        title: {
            text: title,
            left: 'center'
        },
        xAxis: {
            type: 'category',
            data: data.categories,
            name: xAxisName || '',
            nameLocation: 'middle',
            nameGap: 22
        },
        yAxis: {
            type: 'value',
            name: yAxisName || '',
            nameLocation: 'middle',
            nameGap: 45
        },
        tooltip: {
            trigger: 'axis'
        },
        series: data.series.map((serie) => ({
            name: serie.name,
            type: chartType,
            stack: isStack ? 'stack' : '',
            data: serie.data,
        })),
    };

    const mergedOptions = Object.assign(defaultOptions, options);
    chart.setOption(mergedOptions);

    return chart;
}
