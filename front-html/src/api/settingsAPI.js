import request, {Method} from "@/plugins/request";

export function getDefSettings() {
    return request({
        url: "/settings/default",
        method: Method.GET,
        needToken: true
    });
}

export function getSettings() {
    return request({
        url: "/settings",
        method: Method.GET,
        needToken: true
    });
}

export function SaveSettings(datas) {
    return request({
        url: "/settings",
        method: Method.PUT,
        headers:{
            'Content-Type':'application/json'
        },
        needToken: true,
        data: {
            pageSize : datas.pageSize,
            defaultTimeType : datas.defaultTimeType,
        }
    });
}

export function SavePageNum(pageNum) {
    return request({
        url: "/settings/pageNum/"+pageNum,
        method: Method.PUT,
        headers:{
            'Content-Type':'application/json'
        },
        needToken: true,
    });
}

export function SaveDefaultTimeType(defaultTimeType) {
    return request({
        url: "/settings/defaultTimeType/"+defaultTimeType,
        method: Method.PUT,
        headers:{
            'Content-Type':'application/json'
        },
        needToken: true,
    });
}

/**
 * 执行迁移
 */

export function doTransform(from,to,list){
    return request({
        url:"settings/transform/"+from+"/"+to,
        method:Method.POST,
        headers:{
            'Content-Type':'application/json'
        },
        needToken:true,
        data: list
    })
}