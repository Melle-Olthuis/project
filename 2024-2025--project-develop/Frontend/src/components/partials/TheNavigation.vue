<template>
  <nav class="navigation-bar">
    <div class="nav-left">
      <ul class="nav-links">
        <li><router-link to="/" active-class="active-link" exact>Home</router-link></li>
        <li><router-link to="/about" active-class="active-link">About</router-link></li>
        <li><router-link to="/login" active-class="active-link">Login</router-link></li>
      </ul>
    </div>
    <div class="nav-right">
      <img src="@/assets/img/weather.png" alt="Weather Icon" class="weather-icon" />
      <span>{{ currentTemperature }}</span>
    </div>
  </nav>
</template>

<script setup>
const currentTemperature = '15°C'; // Example temperature data
</script>

<style scoped>
.navigation-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: grey;
  color: white;
}

.nav-links {
  list-style: none;
  display: flex;
  gap: 15px;
  margin: 0;
  padding: 0;
}

.nav-links a {
  text-decoration: none;
  color: white;
  font-weight: bold;
}

.nav-links a:hover {
  text-decoration: underline;
}

.nav-links a.active-link {
  color: yellow; /* Highlight the active link */
  text-decoration: underline;
}

.weather-icon {
  width: 30px;
  height: 30px;
}
</style>
