<template>
  <div>
    <v-row>
      <v-col :lg="3" :md="6" :sm="3">
        <NavDrawer />
      </v-col>
      <v-col :lg="6" :md="6" :sm="6">
        <router-view></router-view>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import NavDrawer from "./NavDrawer";
export default {
  name: "Home",
  components: {
    NavDrawer,
  },
};
</script>
