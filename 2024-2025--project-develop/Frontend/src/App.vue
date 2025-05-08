<script setup>
import TheNavigation from './components/partials/TheNavigation.vue';

// const welcomeMessage = 'Voer je gegevens in om door te gaan!';
</script>

<template>
  <TheNavigation />
  <header>
    <img alt="Vue logo" class="logo" src="./assets/img/logo.svg" width="125" height="125" />
  </header>

  <main><router-view /></main>
</template>

<style scoped>
header {
  line-height: 1.5;
}
</style>
