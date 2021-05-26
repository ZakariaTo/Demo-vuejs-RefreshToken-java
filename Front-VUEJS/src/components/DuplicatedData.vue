<template>
  <v-data-table :headers="headers" :items-per-page="5" class="elevation-1"></v-data-table>
</template>
<script>
export default {
  name: "DuplicatedData",
  props: {
    columns: Array
  },
  computed: {
    headers: function() {
      let myHeaders = [];
      this.columns.map(column => {
        myHeaders.push({
          text: column,
          value: column
        });
      });
      myHeaders.push({
        text: "duplicatedData",
        value: "duplicatedData"
      });
      return myHeaders;
    }
  }
};
</script>