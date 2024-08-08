import request,{Method} from "@/plugins/request";
//支出

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
export function detailsExpenditureMonth(month,sortBy,page,size){
    return request({
        url: "expenditure/details/month/"+month+'/'+sortBy+'/'+page+'/'+size,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据时间段获取支出明细
 */
export function detailsExpenditureTimeFrame(start_time,end_time,sortBy,page,size){
    return request({
        url:"expenditure/details/time-frame/"+start_time+'/'+end_time+'/'+sortBy+'/'+page+"/"+size,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 根据年份获取支出明细
 */
export function detailsExpenditureYear(year,sortBy,page,size){
    return request({
        url:"expenditure/details/year/"+year+'/'+sortBy+"/"+page+'/'+size,
        method:Method.GET,
        needToken:true
    })
}

/**
 * 添加支出明细
 * @param {expenditureType} string 支出类型
 * @param {expenditureAmount} double 支出金额
 * @param {expenditureDate} date 支出日期
 * @return null
 */
export function detailsOut(addForm){
    return request({
        url: "expenditure",
        method: Method.POST,
        headers:{
            'Content-Type':'application/json'
        },
        data:{
            expenditureType: addForm.kind,
            expenditureAmount: addForm.amount,
            expenditureDate: addForm.date,
            expenditureInfo: addForm.info
        },
        needToken: true
    })
}

/**
 * 修改支出明细
 * @param {id,expenditureType,expenditureAmount,expenditureDate} int,string,double,date
 * @return {id,expenditureType,expenditureAmount,expenditureDate} int,string,double,date
 */
export function detailsOutIdPut(editForm){
    return request({
        url: "expenditure",
        method: Method.PUT,
        headers:{
            'Content-Type':'application/json'
        },
        data:{
            expenditureId: editForm.id,
            expenditureType: editForm.kind,
            expenditureAmount: editForm.amount,
            expenditureDate: editForm.date,
            expenditureInfo: editForm.info
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
        url: "expenditure",
        method:Method.DELETE,
        params:{
          id: id
        },
        needToken: true
    })
}

