import { ref, watch } from 'vue'
import { defineStore } from 'pinia'

export const useThemeStore = defineStore('theme', () => {
  const isDark = ref(localStorage.getItem('dark') === 'true')

  watch(isDark, (val) => {
    localStorage.setItem('dark', val)
    document.documentElement.classList.toggle('dark', val)
  }, { immediate: true })

  function toggleTheme() {
    isDark.value = !isDark.value
  }

  return { isDark, toggleTheme }
})
