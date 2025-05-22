import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue(), vueDevTools()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  // css: {
  //   preprocessorOptions: {
  //     scss: {
  //       additionalData: `
  //       @import "@/styles/_bootstrap.scss";
  //       @import "@/styles/_mixins.scss";
  //       `,
  //     },
  //   },
  // },
  server: {
    port: 3000, // Your frontend port
    proxy: {
      '/api': {
        target: 'https://localhost:58324', // Your backend URL and port
        changeOrigin: true,
        secure: false, // Only if you're using self-signed certs
      },
    },
  },
})
