
<template>
  <v-sheet height="400" class="overflow-hidden" style="position: relative;">
    <v-container class="fill-height">
      <v-row align="center" justify="center">
        <v-btn color="pink" dark @click.stop="drawer = !drawer">Add User</v-btn>
      </v-row>
    </v-container>

    <v-navigation-drawer v-model="drawer" absolute temporary>
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field v-model="email" :rules="emailRules" label="E-mail" required></v-text-field>

        <v-text-field
          v-model="password"
          :counter="10"
          :rules="nameRules"
          label="Password"
          required
          type="password"
        ></v-text-field>

        <v-select
          v-model="select"
          :items="roles"
          :rules="[v => !!v || 'Item is required']"
          :item-text="'roleName'"
          :item-value="'id'"
          label="roles"
          multiple
          required
        ></v-select>

        <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">Validate</v-btn>

        <v-btn color="error" class="mr-4" @click="reset">Reset Form</v-btn>
      </v-form>
    </v-navigation-drawer>
  </v-sheet>
</template>
<script>
import Axios from "axios";

export default {
  name: "UserDrawer",
  data: () => ({
    drawer: null,
    valid: true,
    password: "",
    nameRules: [
      v => !!v || "Password is required",
      v => (v && v.length > 8) || "Password must be great than 8 characters"
    ],
    email: "",
    emailRules: [
      v => !!v || "E-mail is required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],
    select: null,
    items: ["Item 1", "Item 2", "Item 3", "Item 4"],
    roles: []
  }),
  methods: {
    validate() {
      console.log(
        "Emai: ",
        this.email,
        " password: ",
        this.password,
        "selected : ",
        this.select
      );
      // rolesId
      this.$refs.form.validate();
      Axios.post("http://localhost:8080/api/private/users/", {
        email: this.email,
        password: this.password,
        rolesId: this.select
      })
        .then(() => {
          const objSnackBar = {
            text: "User created succefully",
            color: "success"
          };
          this.$store.dispatch("flashMessages", objSnackBar);
          this.$refs.form.reset();
        })
        .catch(err => {
          console.log("error", err);
          const objSnackBar = {
            text: "Email Already Exist",
            color: "#fc030f"
          };
          this.$store.dispatch("flashMessages", objSnackBar);
        });
    },
    reset() {
      this.$refs.form.reset();
    }
  },
  created() {
    Axios.get("http://localhost:8080/api/private/roles/")
      .then(res => {
        console.log("roles response ", res);
        this.roles = res.data;
      })
      .catch(err => console.log("error getting roles : ", err));
  }
  // computed: {
  //   roles: function() {
  //     Axios.get("http://localhost:8080/api/private/roles/")
  //       .then(res => console.log("roles response ", res))
  //       .catch(err => console.log("error getting roles : ", err));
  //     return [];
  //   }
  // }
};
</script>
