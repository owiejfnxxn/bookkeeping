import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import './assets/logo.png'
import './assets/css/global.css'
import './assets/icon/iconfont.css'
import 'element-ui/lib/theme-chalk/index.css'
// import {createPinia} from "pinia/dist/pinia";
// import uuid from 'vue-uuid'
import axios from "axios";
Vue.config.productionTip = false
axios.defaults.baseUrl="http://localhost:8181"
// const pinia = createPinia();
// Vue.use(pinia)
Vue.use(Element)
// Vue.use(uuid)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
