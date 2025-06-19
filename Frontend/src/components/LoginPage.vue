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
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '../stores/user';

const router = useRouter();
const userStore = useUserStore();

const welcomeMessage = 'Voer je gegevens in om in te loggen';
const username = ref('');
const password = ref('');
const errorMessage = ref('');

const handleSubmit = async () => {
  if (!username.value || !password.value) {
    errorMessage.value = 'Please fill in all fields.';
    return;
  }

  try {
    const response = await axios.post('/api/auth/login', {
      username: username.value,
      password: password.value,
    });

    userStore.setUser(response.data.username, response.data.token || '');
    router.push('/');
  } catch (error) {
    if (error.response && error.response.status === 401) {
      errorMessage.value = 'Invalid credentials.';
    } else {
      errorMessage.value = 'Login failed. Please try again later.';
    }
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
