<template>
  <v-toolbar>
    <v-toolbar-title>DuplicatedData</v-toolbar-title>
    <v-spacer></v-spacer>

    <v-btn icon>
      <router-link to="/home">
        <v-icon>mdi-home</v-icon>
      </router-link>
    </v-btn>
    <!-- <v-btn icon>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn icon>
      <v-icon>mdi-heart</v-icon>
    </v-btn>-->
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn v-bind="attrs" v-on="on" icon>
          <v-icon>mdi-account</v-icon>
        </v-btn>
      </template>
      <v-list>
        <!-- <v-list-item v-for="(item, index) in items" :key="index">
          <v-list-item-title class="accountMenu">{{
            item.title
          }}</v-list-item-title>
        </v-list-item>-->
        <v-list-item>
          <v-list-item-title class="accountMenu">Profile</v-list-item-title>
        </v-list-item>
        <v-list-item>
          <v-list-item-title class="accountMenu"
            >Change Password</v-list-item-title
          >
        </v-list-item>
        <v-list-item>
          <v-list-item-title @click="handlelogout" class="accountMenu"
            >Logout</v-list-item-title
          >
        </v-list-item>
      </v-list>
    </v-menu>
  </v-toolbar>
</template>

<script>
export default {
  methods: {
    handlelogout() {
      this.$store.dispatch("logout").then(() => {
        const objSnackBar = {
          text: "You are succeffuly loggedOut",
          color: "success",
        };
        this.$store.dispatch("flashMessages", objSnackBar);
        this.$router.push("/");
      });
    },
  },
  data: () => ({
    items: [
      { title: "Profile" },
      { title: "Change password" },
      { title: "Logout" },
    ],
  }),
};
</script>
