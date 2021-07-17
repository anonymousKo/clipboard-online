import Vue from 'vue'
import Router from 'vue-router'
import User from '@/components/User'

Vue.use(Router)
Vue.use(User)
export default new Router({
  routes: [
    {
      path: '/',
      name: 'user',
      component: User
    }
  ]
})
