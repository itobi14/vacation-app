<template>

  <div class="content">
    <h2>404 Page Not Found</h2>
    <p>Click <router-link to="/">here</router-link> to go back to the home page.</p>
  </div>

</template>

<script>
export default {
  name: "NotFoundComponent"
}
</script>

<style scoped>

.content {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: .5rem;
  height: 60svh;
}

</style>