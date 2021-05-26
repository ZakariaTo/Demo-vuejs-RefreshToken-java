<template>
  <v-app>
    <v-main>
      <v-snackbar
        timeout="2000"
        v-model="snackbarobj.snackbar"
        :color="snackbarobj.color"
        outlined
        right
        absolute
        top
      >{{ snackbarobj.text }}</v-snackbar>

      <router-view name="header"></router-view>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
//import CustomCompenent from "./components/CustomComponent";

export default {
  name: "App",
  created: function() {
    if (localStorage.getItem("token")) {
      console.log("Calling app vue created Hook");
      this.$http({
        url: "http://localhost:8080/api/private/users/current",
        method: "GET"
      })
        .then(resp => {
          console.log(resp);
        })
        .catch(err => {
          if (err.response.status === 403) {
            if (localStorage.getItem("refresh-token")) {
              this.$store.dispatch("refreshToken");
            }
          }
        });
    }
    // this.$http.interceptors.response.use(undefined, function(err) {
    //   return new Promise(function() {
    //     console.log("Logging the error");
    //     if (err.status === 401 && err.config && !err.config.__isRetryRequest) {
    //       const objSnackBar = {
    //         text: "Token expired you must login",
    //         color: "#fc030f"
    //       };
    //       this.$store.dispatch("flashMessages", objSnackBar);
    //       this.$store.dispatch("logout").then(() => {
    //         this.$router.push("/");
    //       });
    //     }
    //     throw err;
    //   });
    // }
    // );
  },
  computed: {
    snackbarobj: function() {
      return this.$store.state.snackbarObj;
    }
  }
};
</script>
