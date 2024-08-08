import Vue from 'vue'
import Vuex from 'vuex'
import {Message} from "element-ui";

Vue.use(Vuex)

export const useUserStore = new Vuex.Store({
    state: {
        id: 1,
        nickname: '',
        name: '',
        avatar: '',
        grossIncome: 1.000,
        totalExpenditure: 1.000,
    },
    mutations: {
        saveUserInfo(userInfo) {
            this.id = userInfo.id;
            this.nickname = userInfo.nickname;
            this.name = userInfo.name;
            this.avatar = userInfo.avatar;
            this.grossIncome = userInfo.grossIncome;
            this.totalExpenditure = userInfo.totalExpenditure;
        },
        removeUserInfo() {
            this.id = undefined;
            this.nickname = undefined;
            this.name = undefined;
            this.avatar = undefined;
            this.grossIncome = undefined;
            this.totalExpenditure = undefined;
        }
    },
    getters: {
        isLogin() {
            return this.id !== undefined
        }
    },
    actions: {},
    modules: {}
});

export const useHomeStore = new Vuex.Store({});
export default new Vuex.Store({
    state: {
        tableSelected: 'EITable',
        datePickerType: null,
        pageSize:9,
        datePicker: Date.now(),
        totalIncome: 0,
        totalExpenditure: 0,
        categoryList: [],
        defPickerType: 'week',
    },
    mutations: {
        updateDatePickerType(state,type) {
            state.datePickerType = type;
        },
        updateSettings(state, settings) {
            state.pageSize = settings.pageSize;
            state.defPickerType = settings.defaultTimeType;
            state.datePickerType = settings.defaultTimeType;
        },
        updateCategoryList(state, category) {
            let category2 = {
                category_name: category.categoryName,
                category_type: category.categoryType === '0' ? '支出' : '收入',
                category_id : category.categoryId
            }
            state.categoryList.unshift(category2);
        },
        deleteCategoryList(state, category) {
            let index = state.categoryList.findIndex(item => item.category_id === category.category_id);
            state.categoryList.splice(index, 1);
        }
    },
    getters: {},
    actions: {},
    modules: {}
});
