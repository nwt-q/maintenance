import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import verify from './common/verify.js'
import hint from './common/hint.js'
import util from './common/util.js'
import userRequest from './network/userRequest.js'
import * as echarts from 'echarts'

Vue.config.productionTip = false
Vue.use(ElementUI);

Vue.prototype.$verify = verify;
Vue.prototype.$hint = hint;
Vue.prototype.$util = util;
Vue.prototype.$echarts = echarts;

Vue.prototype.$http = userRequest;


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
