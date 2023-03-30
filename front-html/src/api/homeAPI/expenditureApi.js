import request,{Method} from "@/plugins/request";
//支出

/**
 * 根据月份获取总支出
 * @return {totalExpenditure} double 总收入
 * */
export function detailsOutMonth(){
    return request({
        url: 'details/out/month',
        method: Method.GET,
        needToken: true,
    })
}

/**
 * 根据月份获取支出明细
 * @param {month} int 月份
 * @param {page} int 页码
 * @param {size} int 每页条数
 * @return {expenditureList} List 支出明细
 * @template{
 * "id":1,
 * "expenditureType":"房租",
 * "expenditureAmount":10000,
 * "expenditureDate":"2020-01-01"
 * }
 * */
export function detailsOutMonthPageSize(month,page,size){
    return request({
        url: "disbursement/"+month+'/'+page+'/'+size,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 添加支出明细
 * @param {expenditureType} string 支出类型
 * @param {expenditureAmount} double 支出金额
 * @param {expenditureDate} date 支出日期
 * @return null
 */
export function detailsOut(expenditureType,expenditureAmount,expenditureDate){
    return request({
        url: "disbursement",
        method: Method.POST,
        params:{
            expenditureType: expenditureType,
            expenditureAmount: expenditureAmount,
            expenditureDate: expenditureDate
        },
        needToken: true
    })
}

/**
 * 修改支出明细
 * @param {id,expenditureType,expenditureAmount,expenditureDate} int,string,double,date
 * @return {id,expenditureType,expenditureAmount,expenditureDate} int,string,double,date
 */
export function detailsOutIdPut(id,expenditureType,expenditureAmount,expenditureDate){
    return request({
        url: "disbursement/"+id,
        method: Method.PUT,
        params:{
            expenditureType: expenditureType,
            expenditureAmount: expenditureAmount,
            expenditureDate: expenditureDate
        },
        needToken: true
    })
}

/**
 * 删除支出明细
 * @param {id} int 支出明细id
 * @return null
 */
export function detailsOutIdDelete(id){
    return request({
        url: "disbursement/"+id,
        method:Method.DELETE,
        needToken: true
    })
}

/**
 * 根据时间段获取总支出
 * @param {state_date} date 起始时间
 * @param {end_date} date 结束时间
 * @return {totalExpenditure} double
 */

export function totalDisbursementStartAndEnd(start_time,end_time){
    return request({
        url: "disbursement",
        method:Method.GET,
        params:{
            start_time:start_time,
            end_time:end_time
        },
        needToken:true
    })
}
