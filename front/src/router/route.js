// 注册全局首位，超时退出

import router from './index'
// 引入main.js文件，可以调用全局this
// import that from '../main'
import { Message } from 'element-ui';

let routeName = null;
router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  if (to.path === '/') return next({ path: '/login' })
  if (window.localStorage.getItem("Token") === null) {
    Message.warning({
      message: '还未登录，请先登录!'
    });
    setTimeout(function () {
      next({
        path: '/login'
      })
    }, 800)
  } else {
    next()
  }
});