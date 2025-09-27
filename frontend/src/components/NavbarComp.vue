<template>
	<div>
		<div class="navbar">
			<div id="left-navbar">
				<router-link to="/">
					<div id="logo-title">
						<img alt="Vue logo" src="@/assets/img/logo.png">
						Déf'IUT
					</div>
				</router-link>
			</div>
			<div id="right-navbar">
				<div>
					<router-link to="/admin" v-if="isAdmin">Administration</router-link>
					<router-link to="/challenges">Défis</router-link>
					<button @click="logout">Déconnexion</button>
				</div>
				<router-link to="/account">
					<img alt="Compte" src="@/assets/img/account.png">
				</router-link>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'NavbarComp',
	data() {
		return {
			isAdmin: false,
		};
	},
	mounted() {
		this.checkAdmin();
	},
	methods: {
		
		/**
		 * Logout the user
		 */
		logout() {
			fetch('http://localhost:8081/api/users/logout', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({token: localStorage.getItem('token')})
			})
			localStorage.clear();
			this.$router.push('/login');
		},
		
		/**
		 * Check if the user is an admin
		 */
		checkAdmin() {
			// Check if we have a token inside localStorage, otherwise we don't have to even fetch the API
			if (localStorage.getItem('token')) {
				const token = localStorage.getItem('token');
				fetch('http://localhost:8081/api/user', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
					},
					body: JSON.stringify({token}),
				})
					.then((response) => response.json())
					.then((data) => {
						this.isAdmin = data.role === 'ADMIN';
					})
					.catch((error) => {
						console.error('Error fetching user:', error);
					});
			} else {
				return false;
			}
		}
	}
};
</script>

<style>
.navbar {
	display: flex;
	align-items: center;
	background-color: #66a3b2;
	color: white;
	justify-content: space-between;
}

.navbar img {
	width: 50px;
}

.navbar a {
	color: white;
	text-decoration: none;
	margin: 0 15px;
}

.navbar a:hover {
	text-decoration: underline;
}

#right-navbar {
	display: flex;
	align-items: center;
	user-select: none;
}

#right-navbar button {
	background-color: #66a3b2;
	border: none;
	font-family: 'Barlow Condensed', sans-serif;
	font-size: x-large;
}

#right-navbar a {
	font-family: 'Barlow Condensed', sans-serif;
	font-size: x-large;
}

#right-navbar button:hover {
	cursor: pointer;
	text-decoration: underline;
}

#logo-title {
	font-family: 'Bakbak One', sans-serif;
	display: flex;
	align-items: center;
	font-size: 45px;
}

#logo-title img {
	width: 50px;
	margin-left: 20px;
	margin-right: 15px;
}
</style>