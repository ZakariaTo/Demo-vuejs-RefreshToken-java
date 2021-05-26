import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router/router";
import Axios from "axios";
import store from "./store/store";

Vue.config.productionTip = false;

Vue.prototype.$http = Axios;

Vue.prototype.$http.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response.status === 403) {
      console.log("this: ", this);
      store.dispatch("refreshToken");
    }
  }
);

const token = localStorage.getItem("token");
if (token) {
  Vue.prototype.$http.defaults.headers.common["Authorization"] = token;
}

Vue.config.productionTip = false;

new Vue({
  vuetify,
  router,
  render: (h) => h(App),
  store,
}).$mount("#app");
