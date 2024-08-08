import axios from "axios";
import {getStore,setStore} from "@/plugins/storage";
import {v4 as uuidv4} from "uuid";
import qs from "qs";
import {Message} from "element-ui";

const testUrl = "http://47.113.198.159:8181"
const service = axios.create({
    timeout: 10000,//超时时间
    baseURL: testUrl
})

//request拦截器
service.interceptors.request.use(
    (config)=> {
        let isPutPost = config.method==="put" || config.method ==="post";
        let isJson = config.headers["Content-Type"] === "application/json";
        let isFile = config.headers["Content-Type"] === "multipart/form-data";
        if(isPutPost && isJson){
            config.data = JSON.stringify(config.data);
        }
        if(isPutPost && !isFile && !isJson){
            config.data = qs.stringify(config.data,{
                arrayFormat: "repeat",
            })
        }

    //加上uuid
    let uuid = getStore("uuid");
    if(!uuid){
        uuid = uuidv4();
        setStore("uuid",uuid);
    }
    config.headers["uuid"]=uuid;

    //获取Token
    let accessToken = getStore("accessToken");
    if(accessToken && config.needToken){
        config.headers["accessToken"] = accessToken;
    }
    return config;
    },
    (error) => {
        Promise.reject(error);
    }
);

//response拦截器
service.interceptors.response.use(
    async (response) => {
                const res = response.data;
                if(res.code !== 200){
                    Message.warning(res.msg+" response warning");
                    return Promise.reject(response);
                }else{
                    return response;
                }
    },
    async (error) => {
            const errorResponse = error.response;
            const errorData =errorResponse === undefined? undefined: errorResponse.data;
            if(error.message){
                let _message =
                error.code === "ECONNABORTED"
                ? "连接超时，请稍后再试！"
                : "网络错误，请稍后再试！";
                 Message.warning((errorData === undefined ? _message: errorData.data) + 'response warning');
            }else {
                Message.warning('请求失败，网络错误');
            }
            return Promise.reject(error);
    }
);

export const Method = {
    GET: "get",
    POST: "post",
    PUT: "put",
    DELETE: "delete"
};

export default function request(option){
    return service(option);
}


