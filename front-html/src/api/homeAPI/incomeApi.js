import request,{Method} from "@/plugins/request";
//收入

/**
 * 根据年份获取收入明细
 */
export function detailsYearIncome(year,sortBy,page,size){
    return request({
        url: 'income/details/year/'+year+'/'+sortBy+'/'+page+'/'+size,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据月获取收入明细
 */
export function detailsMonthIncome(month,sortBy,page,size){
    return request({
        url: 'income/details/month/'+month+'/'+sortBy+'/'+page+'/'+size,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据时间段获取收入明细
 */
export function detailsTimeFrameIncome(start_time,end_time,sortBy,page,size){
    return request({
        url: 'income/details/time-frame'+'/'+start_time+'/'+end_time+'/'+sortBy+'/'+page+'/'+size,
        method: Method.GET,
        needToken: true
    })
}

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
 * 添加收入明细
 *@return null
 * @param addForm
 * */
export function detailsIncome(addForm){
    return request({
        url:'income',
        method: Method.POST,
        headers: {
            'Content-Type': 'application/json',
        },
        data:{
            incomeType: addForm.kind,
            incomeAmount: addForm.amount,
            incomeDate: addForm.date,
            incomeInfo: addForm.info
        },
        needToken: true
    })
}

/**
 * 修改收入明细
 *
 * @return {id,incomeType,incomeAmount,incomeDate} int,string,double,date
 * @param editForm
 */

export function detailsIncomeIdPut(editForm){
    return request({
        url: "income",
        method: Method.PUT,
        headers: {
            'Content-Type': 'application/json',
        },
        data:{
            incomeId : editForm.id,
            incomeType : editForm.kind,
            incomeAmount : editForm.amount,
            incomeDate : editForm.date,
            incomeInfo: editForm.info
        },
        needToken: true
    })
}

/**
 * 删除收入明细
 * @return null
 * @param id
 */
export function detailIncomeIdDelete(id){
    return request({
        url: "income",
        method:Method.DELETE,
        params:{
            id:id
        },
        needToken: true
    })
}
