// "use strict"
// exports.__esModule = true;
// import {defineStore} from "pinia/dist/pinia.cjs";
//
// const useUserStore = defineStore('userInfo',{
//     persist: {
//         enable: true,
//         strategies:[
//             {
//                 key: 'userInfo',
//                 storage: localStorage,
//             }
//         ]
//     },
//     state:() =>  {
//         return{
//             id:undefined,
//             nickname:'unknown',
//             name:'unknown',
//             avatar:'unknown',
//             grossIncome:123.0,
//             totalExpenditure: 123,
//         }
//     },
//     getters:{
//         isLogin(){
//             return this.id !== undefined;
//         }
//     },
//     mutations: {
//
//     },
//     actions: {
//         saveUserInfo(data){
//             this.id = data.id;
//             this.nickname = data.nickname;
//             this.name = data.name;
//             this.avatar = data.avatar;
//             this.grossIncome = data.grossIncome;
//             this.totalExpenditure = data.totalExpenditure;
//         },
//         removeUserInfo(){
//             this.id = undefined;
//             this.nickname = undefined;
//             this.name = undefined;
//             this.avatar = undefined;
//             this.grossIncome = undefined;
//             this.totalExpenditure = undefined;
//         }
//     }
// });
// export default useUserStore;
