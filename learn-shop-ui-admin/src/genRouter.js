import router from './router'
import store from './store'
import {Message} from 'element-ui'
import {getToken} from '@/utils/auth' // 验权

const whiteList = ['/login', '/authredirect'] // 不重定向白名单

router.beforeEach((to, from, next) => {
  if (getToken()) { // 判断是否有token
    if (to.path === '/login') {
      next()
    } else {
      if (store.getters.roles.length === 0) {
        // console.log('roles====0')
        store.dispatch('GetInfoActions').then(res => { // 拉取用户信息
          var data = res.data
          // const roles = res.data.roles // note: roles must be a array! such as: ['editor','develop']
          // const menus = res.data.menus
          // console.log('roles?', roles)
          // console.log('menus', menus)
          store.dispatch('GenRoutesActions', data).then(() => { // 根据roles权限生成可访问的路由表
            // console.log('addrouters', store.getters.addRouters)
            router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
            next({...to, replace: true}) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          })
        }).catch(() => {
          store.dispatch('FedLogOutActions').then(() => {
            Message.error('验证失败,请重新登录')
            next({path: '/login'})
          })
        })
      } else {
        // console.log('====1')
        next() // 当有用户权限的时候，说明所有可访问路由已生成 如访问没权限的全面会自动进入404页面
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
    }
  }
})

