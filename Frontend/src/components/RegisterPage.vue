<template>
  <div class="register-page">
    <h2>Registreren</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="username">Username:</label>
        <input id="username" v-model="username" required />
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input id="email" type="email" v-model="email" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input id="password" type="password" v-model="password" required />
      </div>
      <button type="submit">Register</button>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '../api';

const username = ref('');
const email = ref('');
const password = ref('');
const errorMessage = ref('');
const router = useRouter();

const handleSubmit = async () => {
  if (!username.value || !email.value || !password.value) {
    errorMessage.value = 'Vul alle velden in.';
    return;
  }
  try {
    await api.post('/api/auth/register', {
      username: username.value,
      email: email.value,
      password: password.value,
    });
    router.push('/login');
  } catch (err) {
    errorMessage.value = 'Registratie mislukt.';
  }
};
</script>

<style scoped>
.register-page {
  background-color: var(--color-background);
  padding: var(--default-padding);
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  margin: 0 auto;
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
</style>
