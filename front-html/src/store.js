import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export const useUserStore = new Vuex.Store({
            state :{
            id: 1,
            nickname: '',
            name: '',
            avatar: '',
            grossIncome: 1.000,
            totalExpenditure: 1.000,
    },
    mutations:{
        saveUserInfo(userInfo){
            this.id = userInfo.id;
            this.nickname = userInfo.nickname;
            this.name = userInfo.name;
            this.avatar = userInfo.avatar;
            this.grossIncome = userInfo.grossIncome;
            this.totalExpenditure = userInfo.totalExpenditure;
        },
        removeUserInfo(){
            this.id = undefined;
            this.nickname = undefined;
            this.name = undefined;
            this.avatar = undefined;
            this.grossIncome = undefined;
            this.totalExpenditure = undefined;
        }
    },
    getters:{
        isLogin(){
            return this.id !== undefined
        }
    },
    actions:{

    },
    modules:{

    }
});

export const useHomeStore = new Vuex.Store({

});
export default new Vuex.Store({
    state :{
        tableSelected:'EITable',
        datePickerType:'week',
        datePicker:Date.now(),
        totalIncome:0,
        totalExpenditure:0
    },
    mutations:{
        updateDatePickerType(type){
            this.state.datePickerType = type;
        }
    },
    getters:{
    },
    actions:{
    },
    modules:{
    }
});
