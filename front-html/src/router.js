import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/', redirect: '/user/login'
        },
        {
            path: '/user/login',
            component: () => import('./views/Login'),
        },
        {
            path: '/user/register',
            component: () => import('./views/Register')
        },
        {
            path: '/user/index',
            component: () => import('./views/index')
        },
        {
            path: '/user',
            component: () => import('./views/index'),
        },
        {
            path: '/test',
            component: () => import('./views/test')
        }
    ]
})
