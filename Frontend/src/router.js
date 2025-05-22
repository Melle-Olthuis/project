import { createRouter, createWebHistory } from 'vue-router';
import HomePage from './components/HomePage.vue';
import LoginPage from './components/LoginPage.vue';
import NotFoundPage from './components/NotFoundPage.vue'; // Import the 404 page

const routes = [
  { path: '/', component: HomePage, name: 'Home' },
  { path: '/login', component: LoginPage, name: 'Login' },
  { path: '/:pathMatch(.*)*', component: NotFoundPage, name: 'NotFound' }, // Catch-all route
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
