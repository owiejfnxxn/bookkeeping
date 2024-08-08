import request,{Method} from "@/plugins/request";


/**
 * 获取全部总类
 */
export function getAllCategory(){
    return request({
        url: 'category',
        method: Method.GET,
        needToken: true,
    })
}

/**
 * 添加分类
 */
export function addCategory(data){
    return request({
        url: '/category',
        method: Method.POST,
        needToken: true,
        headers:{
            'Content-Type':'application/json'
        },
        data:{
            categoryName: data.category_name,
            categoryType: data.category_type,
        }
    })
}

/**
 * 删除分类
 */
export function deleteCategory(data){
    return request({
        url: '/category',
        method: Method.DELETE,
        needToken: true,
        headers:{
            'Content-Type':'application/json'
        },
        data:{
            categoryId: data.category_id,
            categoryName: data.category_name,
            categoryType: data.category_type,
        }
    })
}
