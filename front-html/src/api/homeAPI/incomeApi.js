import request,{Method} from "@/plugins/request";
//收入

/**
 * 根据月份获取总收入
 * @return {grossIncome} double 总收入
 * */
export function detailsIncomeMonth (){
    return request({
        url: 'details/income/month',
        method: Method.GET,
        needToken: true,
    })
}


/**
 * 根据月份获取收入细明
 *@param {month} int 月份
 *@param {page} int 页码
 *@param {size} int 每页条数
 *@return {incomeList} List 收入明细
 * @template {
 * "id":1,
 * "incomeType":"工资",
 * "incomeAmount":10000,
 * "incomeDate":"2020-01-01"
 * }
 * */
export function detailsIncomeMonthPageSize(month,page,size){
    return request({
        url: 'income/'+month+'/'+page+'/'+size,
        method: Method.GET,
        needToken: true,
    })
}

/**
 * 添加收入明细
 *@param {incomeType} string 收入类型
 *@param {incomeAmount} double 收入金额
 *@param {incomeDate} date 收入日期
 *@return null
 * */
export function detailsIncome(incomeType,incomeAmount,incomeDate){
    return request({
        url:'income',
        method: Method.POST,
        params:{
            incomeType: incomeType,
            incomeAmount: incomeAmount,
            incomeDate: incomeDate
        },
        needToken: true
    })
}

/**
 * 修改收入明细
 * @param {id} int 收入明细id
 * @param {incomeType} string 收入类型
 * @param {incomeAmount} double 收入金额
 * @param {incomeDate} date 收入日期
 *
 * @return {id,incomeType,incomeAmount,incomeDate} int,string,double,date
 */

export function detailsIncomeIdPut(id,incomeType,incomeAmount,incomeDate){
    return request({
        url: "income/"+id,
        method: Method.PUT,
        params:{
            incomeType : incomeType,
            incomeAmount : incomeAmount,
            incomeDate : incomeDate
        },
        needToken: true
    })
}

/**
 * 删除收入明细
 * @param {id} int 收入明细id
 * @return null
 */
export function detailIncomeIdDelete(id){
    return request({
        url: "income/"+id,
        method:Method.DELETE,
        needToken: true
    })
}

/**
 * 根据时间段获取总收入
 * @param {start_date} date 起始时间
 * @param {end_date} date 结束时间
 * @return {totalIncome} double
 */
export function totalIncomeStartEnd(start_date,end_date){
    return request({
        url: "income",
        method:Method.GET,
        params:{
            start_date:start_date,
            end_date:end_date
        },
        needToken: true
    })
}
