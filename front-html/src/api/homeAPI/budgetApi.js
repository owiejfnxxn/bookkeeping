import request,{Method} from "@/plugins/request";
//预算

/**
 * 设置月/年预算
 */
export function setTimeTypeBudgetValue(time_type,time,value){
    return request({
        url: 'budget/'+time_type+'/'+time+'/'+value,
        method: Method.PUT,
        needToken: true
    })
}

/**
 * 设置消费种类的预算
 */
export function setKindBudgetValue(time_type,time,kind,value){
    return request({
        url:"budget/"+time_type+'/'+time+'/'+kind+'/'+value,
        method: Method.PUT,
        needToken: true
    })
}

/**
 * 获取总预算
 */
export function getTimeTypeBudgetValue(time_type,time){
    return request({
        url: 'budget/'+time_type+'/'+time,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 获取类型总预算
 */
export function getKindBudgetValue(time_type,time,kind){
    return request({
        url: 'budget/'+time_type+'/'+time+'/'+kind,
        method: Method.GET,
        needToken: true
    })
}

/**
 * 获取已消费额
 */
export function getTimeTypeBudgetExpenditure(time_type,time){
    return request({
        url: 'budget/expenditure/'+time_type+'/'+time,
        method:Method.GET,
        needToken: true
    })
}

/**
 * 获取种类已消费额
 */
export function getTimeTypeKindBudgetExpenditure(time_type,time,kind){
    return request({
        url: 'budget/expenditure/'+time_type+'/'+time+'/'+kind,
        method:Method.GET,
        needToken: true
    })
}
