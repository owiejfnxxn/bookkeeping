import request,{Method} from "@/plugins/request";
//收入和支出

/**
 * 根据月份查询收入和支出
 * @param {month} int 月份
 * @param {page} int 页码
 * @param {size} int 每页条数
 * @return {totalMoney} List
 * @return {incomeTotal} double
 * @return {expenditureTotal} double
 */
export function detailsMonthPageSize(month,page,size){
    return request({
        url: "details/month/"+month+"/"+page+"/"+size,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据年份查询收入和支出
 *  @param {year} int 年份
 *  @param {page} int 页码
 *  @param {size} int 每页条数
 * @return {totalMoney} List
 *  @return {incomeTotal} double
 *  @return {expenditureTotal} double
 */
export function detailsYearPageSize(year,page,size){
    return request({
        url: "details/year/"+year+"/"+page+"/"+size,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据账单类型跟账单种类查询总金额
 * @param {type} int 账单类型（收入或支出）
 * @param {kind} int 账单种类（购物，饮食）
 * @return {data} double 总金额
 */
export function detailsTypeCategory(bill_type,kind){
    return request({
        url: "details/"+bill_type+"/"+kind,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据周查询收入和支出
 * @param {week} int 周数（一年中的第几周）
 * @return {totalMoney} List
 * @return {incomeTotal} double
 * @return {expenditureTotal} double
 */
export function detailsWeek(week){
    return request({
        url: "details/week/"+week,
        method:Method.GET,
        needToken:true
    })
}

/**
 * 根据时间段查询收入和支出
 * @return {list} List
 * @return {incomeTotal} double
 * @return {expenditureTotal} double
 * @param start_time
 * @param end_time
 * @param page
 * @param size
 */
export function detailsStartEnd(start_time,end_time,page,size){
    return request({
        url:"details",
        method:Method.GET,
        params:{
            start_time:start_time,
            end_time:end_time,
            page:page,
            size:size
        },
        needToken:true
    })
}

/**
 * 根据账单id类型查询全部记录
 * @param category
 * @returns {*}
 */
export function detailsbyCategory(categoryid){
    return request({
        url:"details/transform/"+categoryid,
        method:Method.GET,
        needToken:true
    })
}

