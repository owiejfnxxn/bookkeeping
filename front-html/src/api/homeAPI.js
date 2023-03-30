import request,{Method} from "@/plugins/request";

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
        url: 'details/income/'+month+'/'+page+'/'+size,
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
        url: "details/out/"+month+'/'+page+'/'+size,
        method: Method.GET,
        needToken: true
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
        url:'details/income',
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
 * 添加支出明细
 * @param {expenditureType} string 支出类型
 * @param {expenditureAmount} double 支出金额
 * @param {expenditureDate} date 支出日期
 * @return null
 */
export function detailsOut(expenditureType,expenditureAmount,expenditureDate){
    return request({
        url: "details/out",
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
        url: "details/income/"+id,
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
 * 修改支出明细
 * @param {id,expenditureType,expenditureAmount,expenditureDate} int,string,double,date
 * @return {id,expenditureType,expenditureAmount,expenditureDate} int,string,double,date
 */
export function detailsOutIdPut(id,expenditureType,expenditureAmount,expenditureDate){
    return request({
        url: "details/out"+id,
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
 * 删除收入明细
 * @param {id} int 收入明细id
 * @return null
 */
export function detailIncomeIdDelete(id){
    return request({
        url: "details/income/"+id,
        method:Method.DELETE,
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
        url: "details/out"+id,
        method:Method.DELETE,
        needToken: true
    })
}

/**
 * 根据月份查询收入和支出
 * @param {month} int 月份
 * @param {page} int 页码
 * @param {size} int 每页条数
 * @return {incomeList} List
 * @return {expenditure} List
 * @return {incomeTotal} double
 * @return {expenditureTotal} double
 */
export function detailsMonthPageSize(month,page,size){
    return request({
        url: "details/"+month+"/"+page+"/"+size,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据年份查询收入和支出
 *  @param {year} int 年份
 *  @param {page} int 页码
 *  @param {size} int 每页条数
 *  @return {incomeList} List
 *  @return {expenditure} List
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
 * @param {category} int 账单种类（购物，饮食）
 * @return {data} double 总金额
 */
export function detailsTypeCategory(type,category){
    return request({
        url: "details/"+type+"/"+category,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据周查询收入和支出
 * @param {week} int 周数（一年中的第几周）
 * @return {incomeList} List
 * @return {expenditure} List
 * @return {incomeTotal} double
 * @return {expenditureTotal} double
 */
export function detailsWeek(week){
    return request({
        url: "week/"+week,
        method:Method.GET,
        needToken:true
    })
}

/**
 * 根据时间段获取总支出
 * @param {state_date} date 起始时间
 * @param {end_date} date 结束时间
 * @return {totalExpenditure} double
 */
