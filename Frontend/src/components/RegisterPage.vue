<template>
  <div class="register-page">
    <h2>Registreren</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="username">Email:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div class="form-group">
        <label for="password">Wachtwoord:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">Account aanmaken</button>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

const router = useRouter()
const userStore = useUserStore()

const username = ref('')
const password = ref('')
const errorMessage = ref('')

const handleSubmit = async () => {
  if (!username.value || !password.value) {
    errorMessage.value = 'Vul alle velden in.'
    return
  }
  try {
    const response = await axios.post('http://localhost:8080/api/auth/register', {
      username: username.value,
      password: password.value
    })
    userStore.setUser(response.data.username, response.data.token || '')
    router.push('/')
  } catch (error) {
    if (error.response && error.response.status === 409) {
      errorMessage.value = 'Gebruikersnaam bestaat al.'
    } else {
      errorMessage.value = 'Registreren mislukt.'
    }
  }
}
</script>

<style scoped>
.register-page {
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
</style>
