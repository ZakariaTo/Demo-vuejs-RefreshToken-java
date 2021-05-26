import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    status: "",
    token: localStorage.getItem("token") || "",
    user: {},
    snackbarObj: {
      snackbar: false,
      text: "",
      color: "",
    },
  },
  mutations: {
    auth_request(state) {
      state.status = "loading";
    },
    auth_success(state, object) {
      state.status = "success";
      state.token = object.token;
      state.user = object.user;
    },
    auth_error(state) {
      state.status = "error";
    },
    logout(state) {
      state.status = "";
      state.token = "";
    },
    flashMessage(state, object) {
      state.snackbarObj.snackbar = true;
      state.snackbarObj.text = object.text;
      state.snackbarObj.color = object.color;
    },
  },
  actions: {
    login({ commit }, user) {
      return new Promise((resolve, reject) => {
        commit("auth_request");
        axios({
          url: "http://localhost:8080/api/public/login",
          data: user,
          method: "POST",
        })
          .then((resp) => {
            const token = resp.headers.authorization;
            const refreshToken = resp.headers["refresh-token"];
            localStorage.setItem("token", token);
            localStorage.setItem("refresh-token", refreshToken);
            axios.defaults.headers.common["Authorization"] = token;
            //Call current user
            //
            axios({
              url: "http://localhost:8080/api/private/users/current",
              method: "GET",
            })
              .then((response) => {
                //const res = response.data;
                // const aUser = JSON.parse(response.data);
                // const something = JSON.parse(res);
                // console.log("Current User: ", something);
                const user = response.data;
                const object = { user, token };
                commit("auth_success", object);
                resolve(resp);
              })
              .catch((error) => {
                commit("auth_error");
                localStorage.removeItem("token");
                reject(error);
              });
          })
          .catch((err) => {
            commit("auth_error");
            localStorage.removeItem("token");
            reject(err);
          });
      });
    },
    logout({ commit }) {
      return new Promise((resolve) => {
        commit("logout");
        localStorage.removeItem("token");
        delete axios.defaults.headers.common["Authorization"];
        resolve();
      });
    },
    flashMessages({ commit }, objectsnackbar) {
      commit("flashMessage", objectsnackbar);
    },
    refreshToken({ commit }) {
      console.log("Refreshing token...");
      commit("auth_request");
      axios({
        url: "http://localhost:8080/api/public/refreshToken",
        method: "POST",
        data: { refreshToken: localStorage.getItem("refresh-token") },
      })
        .then((response) => {
          localStorage.setItem("token", response.data.token);
          localStorage.setItem("refresh-token", response.data.refreshToken);
        })
        .catch((error) => {
          if (error.response.status === 500) {
            const objSnackBar = {
              text: "Token expired you must login in again",
              color: "#fc030f",
            };
            commit("flashMessage", objSnackBar);
            this.dispatch("logout");
            //console.log("this :", this.);
          }
        });
    },
  },
  getters: {
    isLoggedIn: (state) => !!state.token,
    authStatus: (state) => state.status,
  },
  created() {
    console.log("Creating the store");
  },
});

export default store;
