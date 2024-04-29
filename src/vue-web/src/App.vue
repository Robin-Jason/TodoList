<template>
  <div id="app">
    <nav>
      <router-link to="/add">AddUsers</router-link> |
      <router-link to="/login">Log in</router-link> |
      <router-link to="/info">User Info</router-link> |
      <router-link to="/todolist">ToDo List</router-link> |
      <router-link to="/addlist">Add Items</router-link> |
      <router-link to="/update">Update Items</router-link>
    </nav>
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
<script setup>
</script>