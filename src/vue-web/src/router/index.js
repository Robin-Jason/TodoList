import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AddUser from "@/views/AddUser.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/add',
    name: 'adduser',
    component: AddUser
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/info',
    name: 'info',
    component: () => import(/* webpackChunkName: "about" */ '../views/UserInfo.vue')
  },
  {
    path: '/todolist',
    name: 'todolist',
    component: () => import(/* webpackChunkName: "about" */ '../views/TodoList.vue')
  },
  {
    path: '/addlist',
    name: 'addlist',
    component: () => import(/* webpackChunkName: "about" */ '../views/AddList.vue')
  },
  {
    path: '/update',
    name: 'update',
    component: () => import(/* webpackChunkName: "about" */ '../views/UpdateList.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
