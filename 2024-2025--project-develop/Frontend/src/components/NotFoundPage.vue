<template>
  <div class="not-found-container">
    <h1>404 - Page Not Found</h1>
    <p>Sorry, the page you are looking for does not exist.</p>
    <router-link to="/">Go back to Home</router-link>
  </div>
</template>

<style scoped>
.not-found-container {
  text-align: center;
  padding: 2rem;
  color: var(--color-text);
}
</style>
