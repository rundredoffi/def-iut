<template>
  <div class="login">
    <img alt="logo" src="@/assets/img/logo_title.png">
    <h1 class="main-title">Connexion</h1>
    <router-link to="/register">Créer mon compte</router-link>
    <form @submit.prevent="login">
      <label for="email">E-mail:</label>
      <input type="text" id="email" v-model="email" required>

      <label for="password">Password:</label>
      <input type="password" id="password" v-model="password" required>
      
      <p class="error"></p>
      <button class="accent1" type="submit">Se connecter</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {

    /**
     * Login the user
     */
    login() {
      const userCredentials = {
        email: this.email,
        password: this.password
      };

      fetch('http://localhost:8081/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(userCredentials)
      })
        .then(response => {
          if (response.status === 200) {
            return response.json();
          } else {
            throw new Error('Invalid email or password');
          }
        })
        .then(data => {
          localStorage.setItem('token', data.token);
          this.$router.push('/');
        })
        .catch(error => {
          document.querySelector('.error').textContent = error.message;
        });
    }
  }
};
</script>

<style scoped>
.error {
	margin-bottom: 15px;
}

#password {
	margin-bottom: 15px;
}
</style>