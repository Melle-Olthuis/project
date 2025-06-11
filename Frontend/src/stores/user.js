import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  const username = ref(localStorage.getItem('username') || '')
  const token = ref(localStorage.getItem('token') || '')

  function setUser(newUsername, newToken) {
    username.value = newUsername
    token.value = newToken
    localStorage.setItem('username', newUsername)
    localStorage.setItem('token', newToken)
  }

  function logout() {
    username.value = ''
    token.value = ''
    localStorage.removeItem('username')
    localStorage.removeItem('token')
  }

  return { username, token, setUser, logout }
})
