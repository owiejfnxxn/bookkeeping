import request,{Method} from "@/plugins/request";

/**
 * 获取图数据
 */
export function getChartData(start_time,end_time,chart_type,time_type){
    return request({
        url: "/chart/"+chart_type+"/"+time_type+"/"+start_time+"/"+end_time,
        method: Method.GET,
        needToken: true
    })
}

export const chartType = {
    pie: "pie",
    bar: "bar",
    line: "line"
}
