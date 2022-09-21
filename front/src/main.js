import Vue from 'vue'
import App from './App'
import router from './router'
import axios from '../src/axios/axiosHelper'
import store from './vuex/store'
import VCharts from 'v-charts'
import Util from './common/js/utils'
import './router/route'
import './common/css/common.css'
import './common/js/drag'
// 引入element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueCookies from 'vue-cookies'
import 'jquery'
Vue.config.productionTip = false;
Vue.use(VCharts);
Vue.use(ElementUI);
Vue.use(Util);
Vue.use(VueCookies);
Vue.prototype.axios = axios;
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app")
