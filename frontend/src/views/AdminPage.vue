<template>
    <div>
        <div class="container">
            <h1 class="main-title">Page d'administration</h1>
            <div class="content-container">
                <div class="box">
                    <h2 class="box-title">Logs</h2>
                    <ul class="logs-list">
                        <li v-for="log in logs" :key="log.id" class="log-item">
                            {{ log }}
                        </li>
                    </ul>
                </div>
            </div>
            <div class="content-container">
                <div class="box">
                    <h2 class="box-title">Liste des comptes utilisateurs</h2>
                    <ul class="users-list">
                        <li v-for="user in users" :key="user.id" @click="selectUser(user)"
                            :class="{ selected: selectedUser.id === user.id }">
                            {{ user.id }} - {{ user.name }} - {{ user.email }} - {{ user.score }} points - {{ user.role
                            }}
                        </li>
                    </ul>
                </div>
                <div class="box">
                    <h2 class="box-title">Modifier les informations d'un utilisateur</h2>
                    <p>Sélectionnez un utilisateur dans la liste ci-contre.</p>
                    <form @submit.prevent="updateUser">
                        <button type="button" @click="resetForm">Réinitialiser le formulaire</button>
                        <div class="user-modify-form">
                            <div>
                                <label>ID: {{ selectedUser.id }}</label>
                            </div>
                            <div>
                                <label for="userName">Nom:</label>
                                <input type="text" id="userName" v-model="selectedUser.name" />
                            </div>
                            <div>
                                <label for="userEmail">Email:</label>
                                <input type="email" id="userEmail" v-model="selectedUser.email" />
                            </div>
                            <div>
                                <label for="userRole">Rôle:</label>
                                <select id="userRole" v-model="selectedUser.role">
                                    <option value="ADMIN">Admin</option>
                                    <option value="USER">Utilisateur</option>
                                </select>
                            </div>
                            <div>
                                <label for="userScore">Score:</label>
                                <input type="number" id="userScore" v-model="selectedUser.score" min="0" />
                            </div>
                        </div>
                        <button type="submit">Enregistrer les modifications</button>
                        <button id="ban-user" type="button" @click="deleteUser">Supprimer l'utilisateur</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import '@/assets/styles/admin.css';

export default {
    name: 'AdminPage',
    data() {
        return {
            users: [],
            logs: [],
            selectedUser: {
                id: '',
                name: '',
                email: '',
                role: '',
                score: 0,
            }
        };
    },
    methods: {

        /**
         * Fetch the list of users
         */
        fetchUsers() {
            const token = localStorage.getItem('token');
            fetch('http://localhost:8081/api/admin/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ token }),
            })
                .then(response => response.json())
                .then(data => {
                    this.users = data.users;
                })
                .catch(error => {
                    console.error('Error fetching users:', error);
                });
        },

        /**
         * Fetch the logs
         */
        fetchLogs() {
            const token = localStorage.getItem('token');
            fetch('http://localhost:8081/api/logs', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ token }),
            })
                .then(response => response.json())
                .then(data => {
                    this.logs = data.logs;
                })
                .catch(error => {
                    console.error('Error fetching logs:', error);
                });
        },

        /**
         * Select a user
         * @param {Object} user The user to select
         */
        selectUser(user) {
            this.selectedUser = { ...user };
        },

        /**
         * Update the selected user
         */
        updateUser() {
            const token = localStorage.getItem('token');
            fetch('http://localhost:8081/api/admin/user/update', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    token: token,
                    id: this.selectedUser.id,
                    name: this.selectedUser.name,
                    email: this.selectedUser.email,
                    role: this.selectedUser.role,
                    score: this.selectedUser.score,
                }),
            })
                .then(() => {
                    this.fetchUsers();
                })
                .catch(error => {
                    console.error('Erreur de mise à jour:', error);
                });
        },

        /**
         * Reset the form
         */
        resetForm() {
            this.selectedUser = {
                id: '',
                name: '',
                email: '',
                role: '',
                score: 0,
            };
        },

        /**
         * Delete the selected user
         */
        deleteUser() {
            fetch('http://localhost:8081/api/admin/user/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    token: localStorage.getItem('token'),
                    id: this.selectedUser.id,
                }),
            })
                .then(data => {
                    if (data.status === 200) {
                        this.fetchUsers();
                        this.resetForm();
                    }
                })
                .catch(error => {
                    console.error('Erreur de suppression:', error);
                });
        }
    },
    mounted() {

        // Fetch the list of users and logs
        this.fetchUsers();
        this.fetchLogs();
    },
};
</script>