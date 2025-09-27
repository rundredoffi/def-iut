<template>
	<div class="register">
		<img alt="Vue logo" src="@/assets/img/logo_title.png">
		<h1 class="main-title">Inscription</h1>
		<router-link to="/login">Se connecter</router-link>
		<form @submit.prevent="register">
			<label for="username">Username:</label>
			<input type="text" id="username" v-model="username" required>
			
			<label for="email">E-mail:</label>
			<input type="email" id="email" v-model="email" required>
			
			<label for="password">Password:</label>
			<input type="password" id="password" v-model="password" required>
			
			<label for="confirmPassword">Confirm Password:</label>
			<input type="password" id="confirmPassword" v-model="confirmPassword" required>
			<div class="error">{{ error }}</div>
			<button class="accent1" type="submit">Créer mon compte</button>
		</form>
	</div>
</template>

<script>
export default {
	name: 'RegisterPage',
	data() {
		return {
			username: '',
			email: '',
			password: '',
			confirmPassword: '',
			error: ''
		};
	},
	methods: {
		
		/**
		 * Register the user
		 */
		register() {
			
			// Check first if username is correct (not empty)
			if (this.username.trim().length === 0) {
				this.error = 'Username cannot be empty'
			} else {
				// Perform the registration logic here
				fetch('http://localhost:8081/api/users', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({
						name: this.username,
						email: this.email,
						password: this.password
					})
				})
					.then(response => {
						if (response.ok) {
							// Registration successful, redirect to login page
							this.$router.push('/login');
						} else {
							this.error = 'Registration failed';
						}
					})
					.catch(error => {
						console.error('Error during registration:', error);
					});
			}
		}
	}
};
</script>

<style scoped>
.error {
	margin-bottom: 15px;
}
</style>

