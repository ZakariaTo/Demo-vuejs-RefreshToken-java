<template>
  <v-container fluid>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="6">
        <v-select :items="items" label="Select a database name" @change="selectedValue"></v-select>
      </v-col>
    </v-row>
    <TableComponent v-if="isDbNameSelected" :db-name="dbNameSelected" />
  </v-container>
</template>

<script>
import TableComponent from "./TableComponent";
export default {
  name: "CustomComponent",
  methods: {
    selectedValue(value) {
      console.log("seletc value:", value);
      this.dbNameSelected = value;
      this.isDbNameSelected = true;
    }
  },
  data: function() {
    return {
      items: ["item1", "item2", "item3"],
      dbNameSelected: "",
      isDbNameSelected: false
    };
  },
  components: {
    TableComponent
  }
};
</script>
