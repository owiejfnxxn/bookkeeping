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
import axios from "axios";
import ECharts from 'echarts'

Vue.config.productionTip = false
axios.defaults.baseUrl = "http://47.94.97.177:8181"

Vue.use(Element)
Vue.use(ECharts);

// font-awesome
/* import the fontawesome core */
import {library} from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

/* import specific icons */
import {far} from '@fortawesome/free-regular-svg-icons'
import {fas} from '@fortawesome/free-solid-svg-icons'
/* add icons to the library */
library.add(far, fas)

/* add font awesome icon component */
Vue.component('font-awesome-icon', FontAwesomeIcon)


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
