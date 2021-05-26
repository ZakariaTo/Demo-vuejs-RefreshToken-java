<template>
  <div class="half">
    <!-- <div class="bg order-1 order-md-2" style="background-image: url('images/bg_1.jpg');"></div> -->
    <div class="contents order-2 order-md-1">
      <div class="container">
        <div class="row align-items-center justify-content-center">
          <div class="col-md-6">
            <div class="form-block">
              <div class="text-center mb-5">
                <h3>
                  Login to
                  <strong>Vue JS</strong>
                </h3>
                <ul v-if="this.errors.length">
                  <li
                    v-for="item in this.errors"
                    :key="item"
                    style="list-style-type: none; "
                  >
                    <v-alert class="mb-4" type="error">{{ item }}</v-alert>
                  </li>
                </ul>
              </div>
              <!-- <form action="#" method="post"> -->
              <v-form ref="form" v-model="valid" lazy-validation>
                <div class="form-group first">
                  <!-- <label for="email">Email</label> -->
                  <v-text-field
                    v-model="email"
                    :rules="emailRules"
                    label="Email"
                    required
                  ></v-text-field>
                </div>
                <div class="form-group last mb-3">
                  <!-- <label for="password">Password</label> -->
                  <v-text-field
                    v-model="password"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="passwordRules"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Password"
                    @click:append="show1 = !show1"
                  ></v-text-field>
                </div>

                <div class="d-sm-flex mb-5 align-items-center">
                  <label class="control control--checkbox mb-3 mb-sm-0">
                    <span class="caption">Remember me</span>
                    <input type="checkbox" />
                    <div class="control__indicator"></div>
                  </label>
                  <span class="ml-auto">
                    <a href="#" class="forgot-pass">Forgot Password</a>
                  </span>
                </div>
                <button
                  class="btn btn-block btn-primary"
                  v-on:click="handleSubmit($event)"
                  :disabled="!valid"
                >
                  Log In
                </button>
                <!-- <input type="submit" value="Log In" class="btn btn-block btn-primary" v-on:click="handleSubmit($event)"/> -->
                <!-- </form> -->
              </v-form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data: function() {
    return {
      show1: false,
      valid: true,
      email: "",
      password: "",
      emailRules: [(v) => !!v || "Emai is required"],
      passwordRules: [(v) => !!v || "Password is required"],
      errors: [],
    };
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      if (this.email == "" || this.password == "") {
        this.errors.push("Email and Password are required");
        return;
      }
      this.$store
        .dispatch("login", {
          email: this.email,
          password: this.password,
        })
        .then(() => {
          const objSnackBar = {
            text: "You are succeffuly loggedIn",
            color: "success",
          };
          this.$store.dispatch("flashMessages", objSnackBar);

          this.$router.push("/home");
        })
        .catch(() => {
          this.errors = [];
          this.errors.push("Email or Password is wrong");
        });
    },
  },
};
</script>
