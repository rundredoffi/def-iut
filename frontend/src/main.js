import { createApp } from 'vue'
import App from './App.vue'
import * as VueRouter from 'vue-router'
import LoginPage from './views/LoginPage.vue'
import RegisterPage from './views/RegisterPage.vue'
import HomePage from './views/HomePage.vue'
import ChallengesPage from './views/ChallengesPage.vue'
import AccountPage from './views/AccountPage.vue'
import AdminPage from './views/AdminPage.vue'
import OneChallengePage from './views/OneChallengePage.vue'

/**

 Check if the user is authenticated and whether they have the administrator role
 @returns {Promise<{isAuthenticated: boolean, isAdmin: boolean}>}
 An object containing:
 isAuthenticated: true if the user is logged in, false otherwise
 isAdmin: true if the user has the 'ADMIN' role, false otherwise
 */
async function UserInfo() {
    // Check if we have a token inside localStorage, otherwise we don't have to even fetch the API
    if (localStorage.getItem('token')) {
        const response = await fetch('http://localhost:8081/api/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                token: localStorage.getItem('token')
            })
        });

        if (response.status !== 200) {
            return { isAuthenticated: false, isAdmin: false };
        } else {
            const data = await response.json();
            return {
                isAuthenticated: true,
                isAdmin: data.role === 'ADMIN'
            };
        }
    } else { // No token = not connected & not admin
        return { isAuthenticated: false, isAdmin: false };
    }
}

// Create the router
const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes: [
        { path: '/login', component: LoginPage, meta: { hiddenNav: true } },
        { path: '/register', component: RegisterPage, meta: { hiddenNav: true } },
        { path: '/', component: HomePage, meta: { requiresAuth: true } },
        { path: '/challenges', component: ChallengesPage, meta: { requiresAuth: true } },
        { path: '/account', component: AccountPage, meta: { requiresAuth: true } },
        { path: '/admin', component: AdminPage, meta: { requiresAuth: true, requiresAdmin: true } },
        { path: '/challenges/:id', component: OneChallengePage, meta: { requiresAuth: true }, props: true }
    ]
});

// Configure the router to check if user is authenticated
router.beforeEach(async (to, from, next) => {
    const {isAuthenticated, isAdmin} = await UserInfo();

    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!isAuthenticated) {
            next('/login');
            return;
        }

        if (to.matched.some(record => record.meta.requiresAdmin) && !isAdmin) {
            next(from.fullPath); // on revient sur la page précédente
            return;
        }
    }

    next();
});

// Mount the app
createApp(App).use(router).mount('#app')