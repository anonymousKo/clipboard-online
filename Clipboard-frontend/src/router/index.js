import Vue from 'vue'
import Router from 'vue-router'
import Clipboard from '@/components/Clipboard'

Vue.use(Router)
Vue.use(Clipboard)
export default new Router({
  routes: [
    {
      path: '/',
      name: 'clipboard',
      component: Clipboard
    }
  ]
})
