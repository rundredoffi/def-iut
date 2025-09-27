<template>
	<div>
		<h1 class="main-title">Accueil</h1>
		<!-- Main Content Container -->
		<div class="content-container">
			<div class="account-challenge-div">
				<!-- Account Info Box -->
				<div class="box account-info accent2">
					<h2 class="box-title">Informations du compte</h2>
					<div>
						<ul class="account-info-list">
							<li class="account-info-item">
								<span class="info-label">Rôle : </span>
								<span class="info-value">{{ userRole }}</span>
							</li>
							<li class="account-info-item">
								<span class="info-label">Nom : </span>
								<span class="info-value">{{ userName }}</span>
							</li>
							<li class="account-info-item">
								<span class="info-label">Email : </span>
								<span class="info-value">{{ userEmail }}</span>
							</li>
							<li class="account-info-item">
								<span class="info-label">Score : </span>
								<span class="info-value">{{ userScore }}</span>
							</li>
						</ul>
					</div>
				</div>
				
				<!-- Ongoing Challenges Box -->
				<div class="box challenges accent2">
					<h2 class="box-title">Défis en cours</h2>
					<ul class="challenges-list" v-if="startedChallenges.length > 0">
						<li class="challenge-item" v-for="(challenge) in startedChallenges" :key="challenge.id">
							<div class="challenge-info">
								<div class="challenge-name-box">
									<b>{{ challenge.name }}</b>
								</div>
								<div class="challenge-difficulty-box">
									<b>Difficulté : </b> {{ challenge.difficulty }}
								</div>
								<div class="challenge-language-box">
									<b>Langage : </b> {{ challenge.language }}
								</div>
								<div class="challenge-date-box">
									<b>Mis en ligne le </b> {{ dateFormat(challenge.date) }}
								</div>
							</div>
						</li>
					</ul>
					<ul class="challenges-list" v-else>
						<li id="no-challenge">Aucun défi en cours.</li>
					</ul>
				</div>
			</div>
			
			<!-- Leaderboard Box -->
			<div class="box leaderboard accent2">
				<h2 class="box-title">Classement des joueurs</h2>
				<div>
					<ol class="leaderboard-list">
						<li class="leaderboard-item" v-for="(user, index) in users" :key="user.id">
							<div class="leaderboard-rank-box"><b>{{ index + 1 }}</b></div>
							<div class="leaderboard-info">
								<span class="leaderboard-name">{{ user.name }}</span>
								<span class="leaderboard-score"> - {{ user.score }} points</span>
								<span
									:class="['badge', user.badge === '1er' ? 'badge-1er' : user.badge === '2eme' ? 'badge-2eme' : user.badge === '3eme' ? 'badge-3eme' : '']"
									v-if="user.badge" :title="user.badge">{{ user.badge }}</span>
							</div>
						</li>
					</ol>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import '@/assets/styles/home.css'
import {translateDifficulty, dateFormat} from "@/utils/functions";

export default {
	name: 'HomePage',
	data() {
		return {
			// Account info
			userRole: '',
			userName: '',
			userEmail: '',
			userScore: '',
			
			// Leaderboard
			users: [],
			
			// Started challenges
			startedChallenges: [],
		};
	},
	mounted() {
		const token = localStorage.getItem('token');
		
		// Fetch account info
		fetch('http://localhost:8081/api/user', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({token}),
		})
			.then((response) => response.json())
			.then((data) => {
				this.userRole = data.role;
				this.userName = data.name;
				this.userEmail = data.email;
				this.userScore = data.score;
			})
			.catch((error) => {
				console.error('Error fetching account info:', error);
			});
		
		// Fetch leaderboard
		fetch('http://localhost:8081/api/users/score')
			.then((response) => response.json())
			.then((data) => {
				
				// Order users by score
				data.users.sort((a, b) => b.score - a.score);
				
				// Assign badges
				data.users.forEach((user, index) => {
					if (index === 0) {
						user.badge = '1er';
					} else if (index === 1) {
						user.badge = '2eme';
					} else if (index === 2) {
						user.badge = '3eme';
					}
				});
				
				// Send users to data to display them in the <template> part
				this.users = data.users;
			})
			.catch((error) => {
				console.error('Error fetching leaderboard:', error);
			});
		
		// Fetch started challenges
		fetch('http://localhost:8081/api/challenges/started', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({token}),
		})
			.then((response) => response.json())
			.then((data) => {
				this.startedChallenges = data.challenges;
				
				// Translate the difficulties
				for (let challenge of this.startedChallenges) {
					challenge.difficulty = translateDifficulty(challenge.difficulty);
				}
			})
			.catch((error) => {
				console.error('Error fetching started challenges:', error);
			});
	},
	methods: {
		dateFormat,
	}
};
</script>
