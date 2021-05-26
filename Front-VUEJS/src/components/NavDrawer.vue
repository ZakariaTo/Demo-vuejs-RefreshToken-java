<template>
  <v-navigation-drawer permanent expand-on-hover>
    <v-list>
      <v-list-item class="px-2">
        <v-list-item-avatar>
          <v-img :src="'/images/'+user.avatar"></v-img>
        </v-list-item-avatar>
      </v-list-item>

      <v-list-item link>
        <v-list-item-content>
          <!-- <v-list-item-title class="title">{{user.avatar}}</v-list-item-title> -->
          <v-list-item-subtitle>{{ user.email }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-divider></v-divider>

    <v-list nav dense>
      <v-list-item link>
        <v-list-item-icon>
          <v-icon>mdi-folder</v-icon>
        </v-list-item-icon>
        <v-list-item-title>
          <router-link to="/home">Duplicated Data</router-link>
        </v-list-item-title>
      </v-list-item>
      <v-list-item link>
        <v-list-item-icon>
          <v-icon>mdi-account-multiple</v-icon>
        </v-list-item-icon>
        <v-list-item-title>
          <router-link to="/home/profile">Profile</router-link>
        </v-list-item-title>
      </v-list-item>
      <v-list-item link>
        <v-list-item-icon>
          <v-icon>mdi-account-multiple</v-icon>
        </v-list-item-icon>
        <v-list-item-title>
          <router-link to="/home/counter">Counter</router-link>
        </v-list-item-title>
      </v-list-item>
      <v-list-item link v-for="role in user.roles" :key="role.id">
        <v-list-item-icon v-if="role.roleName.toUpperCase() === 'ADMIN'">
          <v-icon>mdi-account-multiple</v-icon>
        </v-list-item-icon>
        <v-list-item-title v-if="role.roleName.toUpperCase() === 'ADMIN'">
          <router-link to="/home/users">Users</router-link>
        </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  computed: {
    user: function() {
      console.log("Getting user from the store...");
      console.log("User in the store : ", this.$store.state.user);
      return this.$store.state.user;
    },
    token: function() {
      console.log("Getting token from the store...");
      return this.$store.state.token;
    }
  }
};
</script>
