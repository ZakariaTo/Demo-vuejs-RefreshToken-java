<template>
  <div>
    <h1>Mu counter</h1>
    <span> count equal {{ this.counter }}</span>
    <button v-on:click="increment">increment</button>
    <button v-on:click="decrement">decrement</button>
  </div>
</template>
<script>
export default {
  name: "Counter",
  computed: {
    counter: function() {
      return this.$store.state.count;
    },
  },
  methods: {
    increment() {
      this.$store.commit("increment");
    },
    decrement() {
      this.$store.commit("decrement");
    },
  },
};
</script>
