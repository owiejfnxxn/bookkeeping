import * as EIApi from "@/api/homeAPI/expenditureIncomeApi";

export default function fetchData(api,params,successCallback){
    EIApi[api](...params).then(res =>{
        const data=res.data.data;
        successCallback(data);
    })
}
