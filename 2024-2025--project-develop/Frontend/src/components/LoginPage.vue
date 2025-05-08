<template>
  <div class="login-page">
    <h2>Login</h2>
    <span>{{ welcomeMessage }}</span>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">Login</button>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
// const props = defineProps({
//   welcomeMessage: {
//     type: String,
//     default: '',
//   },
// });

//const welcomeMessage = props.welcomeMessage;
const username = ref('');
const password = ref('');
const errorMessage = ref('');

const handleSubmit = () => {
  if (!username.value || !password.value) {
    errorMessage.value = 'Please fill in all fields.';
  } else if (username.value.length < 3) {
    errorMessage.value = 'Username must be at least 3 characters long.';
  } else if (password.value.length < 6) {
    errorMessage.value = 'Password must be at least 6 characters long.';
  } else {
    errorMessage.value = '';
    // Handle login logic here
  }
};
</script>

<style scoped>
.login-page {
  background-color: var(--color-background);
  padding: var(--default-padding);
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 1rem;
}

input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid var(--color-secondary);
  border-radius: 4px;
}

button {
  background-color: var(--color-primary);
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: hsla(160, 100%, 37%, 0.8);
}

.error {
  color: red;
  margin-top: 1rem;
}

span {
  font-weight: bold;
  display: block;
  text-align: center;
  margin-bottom: 1rem;
}
</style>
