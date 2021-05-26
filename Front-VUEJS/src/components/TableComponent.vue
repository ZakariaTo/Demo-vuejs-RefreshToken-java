<template>
  <div>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="6">
        <v-select :items="items" label="Select a table name" @change="selectedValue"></v-select>
      </v-col>

      <!-- <h1>dbName: {{ this.value }}</h1> -->
      <!-- <p v-if="isDbNameSelected">{{ dbNameSelected }}</p> -->
    </v-row>

    <ColumnsComponent v-if="isTableSelected" :table="tableNameSelected" />
  </div>
</template>
<script>
import ColumnsComponent from "./ColumnsComponent";
export default {
  name: "TableComponent",
  methods: {
    selectedValue(value) {
      this.tableNameSelected = value;
      this.isTableSelected = true;
    }
  },
  props: {
    dbName: {
      type: String
    }
  },
  data: function() {
    return {
      isTableSelected: false,
      tableNameSelected: ""
    };
  },
  computed: {
    value: function() {
      return this.dbName.trim().toLowerCase();
    },
    items: function() {
      console.log("dbName getted is : ", this.dbName.trim().toLowerCase());
      if (this.dbName.trim().toLowerCase() === "item1")
        return ["table1DB1", "table2DB1", "table3DB1"];
      if (this.dbName.trim().toLowerCase() === "item2")
        return ["table1DB2", "table2DB", "table3DB2"];
      if (this.dbName.trim().toLowerCase() === "item3")
        return ["table1DB3", "table2DB3"];
      return [];
    }
  },
  components: {
    ColumnsComponent
  }
};
</script>
