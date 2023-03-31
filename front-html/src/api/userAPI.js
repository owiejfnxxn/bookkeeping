import request, {Method} from "@/plugins/request";

export function login (username,password){
    return  request({
        url: '/user/login',
        method: Method.POST,
        params:{
            username : username,
            password : password
        }
    })
}

export function register (username,password,email,code){
    return request({
        url: 'user/register',
        method: Method.POST,
        params:{
            username: username,
            password: password,
            email: email,
            code: code
        }
    })
}

export function userInfo(){
    return request({
        url: '/user',
        method: Method.GET,
        needToken: true
    })
}

export function sendCode(email){
    return request({
        url: '/user/sendCode',
        method: Method.POST,
        params:{
            email:email,
        },
    })
}
