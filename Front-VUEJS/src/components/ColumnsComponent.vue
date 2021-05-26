<template>
  <div>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="6">
        <v-select :items="items" label="Select columns" multiple chips @change="selectedValue"></v-select>
      </v-col>
    </v-row>
    <DuplicatedData v-if="isColumnSelected" :columns="columns" />
  </div>
</template>
<script>
import DuplicatedData from "./DuplicatedData";
export default {
  name: "ColumnsComponent",
  props: {
    table: String
  },
  methods: {
    selectedValue(value) {
      console.log("columns selected", value);
      this.columns = value;
      this.isColumnSelected = true;
    }
  },
  data: function() {
    return {
      columns: [],
      isColumnSelected: false
    };
  },
  computed: {
    otherComputedFuction: function() {
      return 12528 + "542" === "12528542";
    },
    items: function() {
      if (this.table === "table1DB1") return ["user", "id", "username"];
      if (this.table === "table2DB1") return ["roleName", "id"];
      if (this.table === "table3DB1") return ["row1", "row2", "row3", "row4"];
      return [];
    }
  },
  components: {
    DuplicatedData
  }
};
</script>