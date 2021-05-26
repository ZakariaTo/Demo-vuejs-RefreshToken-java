import Vue from "vue";
import VueRouter from "vue-router";

import Login from "../components/Login";
import Home from "../components/Home";
import CustomComponent from "../components/CustomComponent";
import Header from "../components/Header";
import Profile from "../components/Profile";
import Counter from "../components/Counter";
import store from "../store/store";
import Users from "../components/Users";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/home",
    components: { default: Home, header: Header },
    children: [
      {
        // UserProfile will be rendered inside User's <router-view>
        // when /user/:id/profile is matched
        path: "",
        component: CustomComponent,
      },
      {
        // UserPosts will be rendered inside User's <router-view>
        // when /user/:id/posts is matched
        path: "profile",
        component: Profile,
      },
      {
        path: "counter",
        component: Counter,
      },
      {
        path: "users",
        component: Users,
        meta: {
          requiresAdmin: true,
        },
      },
    ],
    meta: {
      requiresAuth: true,
    },
  },
  // {
  //   path: "/DuplicatedData",
  //   name: "DuplicatedData",
  //   components: { default: CustomComponent, header: Header },
  //   meta: {
  //     requiresAuth: true,
  //   },
  // },
];

const router = new VueRouter({
  routes, // short for `routes: routes`
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (store.getters.isLoggedIn) {
      next();
      return;
    }
    next({
      path: "/",
      params: { nextUrl: to.fullPath },
    });
  } else {
    next();
  }
});

// router.beforeEach((to, from, next) => {
//   if (to.matched.some((record) => record.meta.requiresAuth)) {
//     if (localStorage.getItem("token") == null) {
//       next({
//         path: "/",
//         params: { nextUrl: to.fullPath },
//       });
//     } else {
//       let user = JSON.parse(localStorage.getItem("user"));
//       if (to.matched.some((record) => record.meta.is_admin)) {
//         if (user.is_admin == 1) {
//           next();
//         } else {
//           next({ name: "userboard" });
//         }
//       } else {
//         next();
//       }
//     }
//   } else {
//     next();
//   }
// });

//   router.beforeEach((to, from, next) => {
//     if(to.matched.some(record => record.meta.requiresAuth)) {
//         if (localStorage.getItem('jwt') == null) {
//             next({
//                 path: '/login',
//                 params: { nextUrl: to.fullPath }
//             })
//         } else {
//             let user = JSON.parse(localStorage.getItem('user'))
//             if(to.matched.some(record => record.meta.is_admin)) {
//                 if(user.is_admin == 1){
//                     next()
//                 }
//                 else{
//                     next({ name: 'userboard'})
//                 }
//             }else {
//                 next()
//             }
//         }
//     } else if(to.matched.some(record => record.meta.guest)) {
//         if(localStorage.getItem('jwt') == null){
//             next()
//         }
//         else{
//             next({ name: 'userboard'})
//         }
//     }else {
//         next()
//     }
// })

export default router;
