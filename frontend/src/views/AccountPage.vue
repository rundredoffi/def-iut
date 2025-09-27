<template>
	<div>
		<h1 class="main-title">Mon Compte</h1>
		<div class="content-container">
			<div class="box">
				<h2 class="box-title">Liste des défis réussis</h2>
				<ChallengeFilters
            :difficulty-filters="difficultyFilters"
            :language-filters="languageFilters"
            :status-filters="[]"
            @filter-updated="updateFilters"
        />
				
				<ul class="challenges-list successful-challenge-div" v-if="filteredChallenges.length > 0">
					<li class="challenge-item" v-for="(challenge) in filteredChallenges" :key="challenge.id">
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
				<ul v-if="filteredChallenges.length === 0" class="challenges-list">
					<li class="no-challenge-completed">Aucun défi réussi.</li>
				</ul>
				<ul v-if="filteredChallenges.length !== 0 && noResults" id="no-results" class="challenges-list">
					<li class="no-challenge-completed">Aucun résultat trouvé.</li>
				</ul>
			</div>
		
		</div>
		
		<div class="content-container">
			<div class="box">
				<h2 class="box-title">Mes points</h2>
				<ul class="user-points">
					<li>
						<b>Points : </b>{{ userPoints }}
					</li>
				</ul>
				<h2 class="box-title">Mes badges de classement</h2>
				<ul class="badge-list" v-if="userBadges.length > 0">
					<li v-for="badge in userBadges" :key="badge.rank">
						<span :class="['badge', 'badge-' + badge.rank]" :title="badge.rank">{{ badge.rank }}</span>
						<span>{{ dateFormat(badge.date) }}</span>
					</li>
				</ul>
				<ul class="badge-list" v-else>
					<li class="no-badge">Aucun badge obtenu.</li>
				</ul>
				<h2 class="box-title">Mes récompenses</h2>
				<ul class="badge-list" v-if="userAchievements.length > 0">
					<AchievementCard v-for="achievement in userAchievements"
									:key="achievement.name"
									:badge="achievement" />
				</ul>
				<ul class="badge-list" v-else>
					<li id="no-badge">Aucune récompense obtenue.</li>
				</ul>
			</div>
			
			<div class="box">
				<h2 class="box-title">Informations sur le compte</h2>
				<ul class="account-info-list">
					<li><b>Rôle : </b>{{ userRole }}</li>
					<li>
						<b>Nom : </b>
						<span v-if="!isEditing">{{ userName }}</span>
						<input v-else type="text" v-model="userName" class="user-info-bar"/>
					</li>
					<li>
						<b>Email : </b>
						<span v-if="!isEditing">{{ userEmail }}</span>
						<input v-else type="text" v-model="userEmail" class="user-info-bar"/>
					</li>
					<li><b>Score : </b>{{ userScore }}</li>
				</ul>
				<button v-if="!isEditing" class="accent2" @click="isEditing = true">Modifier mon profil</button>
				<div v-else>
					<button class="accent2" @click="confirmEdit">Confirmer</button>
					<button @click="isEditing = false">Annuler</button>
				</div>
				<button id="delete-button" @click="showDeleteConfirmation">Supprimer mon compte</button>
				<div class="delete-modal" v-if="showDeleteModal">
					<p>Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.</p>
					<button @click="deleteUser" class="accent2">Oui</button>
					<button @click="cancelDelete">Non</button>
				</div>
			</div>
		</div>
		
		<div class="content-container">
			<div class="box">
				<h2 class="box-title">Mes défis en cours</h2>
				<ul class="challenges-list" v-if="ongoingChallenges.length > 0">
					<li class="challenge-item" v-for="(challenge) in ongoingChallenges" :key="challenge.id">
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
	</div>
</template>

<script>
import '@/assets/styles/account.css';
import {translateDifficulty, dateFormat} from "@/utils/functions";
import AchievementCard from "@/components/AchievementCard";
import ChallengeFilters from "@/components/ChallengeFilters";

export default {
	name: 'AccountPage',
	components: {
		AchievementCard,
		ChallengeFilters,
	},
	data() {
		return {
			difficultyFilters: [],
			languageFilters: [],
			successfulChallenges: [],
			userPoints: 0,
			userBadges: [],
			userAchievements: [],
			ongoingChallenges: [],
			userRole: '',
			userName: '',
			showDeleteModal: false,
			userEmail: '',
			userScore: 0,
			isEditing: false,
			noResults: true,
      selectedDifficulty: 'ALL',
      selectedLanguage: 'ALL',
      selectedStatus: 'ALL',
      showRecentOnly: false,
      searchQuery: '',
		};
	},
  computed: {
    filteredChallenges() {
      return this.successfulChallenges.filter(challenge => {
        const matchesDifficulty = this.selectedDifficulty === 'ALL' || challenge.difficulty === this.selectedDifficulty;
        const matchesLanguage = this.selectedLanguage === 'ALL' || challenge.language === this.selectedLanguage;
        const matchesStatus = this.selectedStatus === 'ALL' || challenge.status === this.selectedStatus;
        const matchesRecent = !this.showRecentOnly || this.isWithinLast30Days(challenge.date);
        const matchesSearch = this.searchQuery === '' || challenge.name.toLowerCase().includes(this.searchQuery.toLowerCase());
        return matchesDifficulty && matchesLanguage && matchesStatus && matchesRecent && matchesSearch;
      });
    },
  },
	methods: {
    dateFormat,
    /**
     * Check if a date is within the last 30 days
     *
     * @param dateString
     * @returns {boolean}
     */
    isWithinLast30Days(dateString) {
      let today = new Date();
      let challengeDate = new Date(dateString);
      let differenceInMilliseconds = today - challengeDate;
      let differenceInDays = differenceInMilliseconds / (1000 * 3600 * 24);
      return differenceInDays <= 30;
    },
    /**
     * Update the challenges according to the filters set
     *
     * @param difficulty
     * @param language
     * @param status
     * @param recent
     * @param search
     */
    updateFilters({ difficulty, language, status, recent, search }) {
      this.selectedDifficulty = difficulty;
      this.selectedLanguage = language;
      this.selectedStatus = status;
      this.showRecentOnly = Boolean(recent);
      this.searchQuery = search;
      this.noResults = this.filteredChallenges.length === 0;
    },
		/**
		 * Show or hide to delete confirmation modal
		 */
		showDeleteConfirmation() {
			this.showDeleteModal = true;
		},
		
		/**
		 * Hide delete confirmation modal and cancel deletion
		 */
		cancelDelete() {
			this.showDeleteModal = false;
		},
		
		/**
		 * Delete user profile
		 */
		deleteUser() {
			const token = localStorage.getItem('token');
			fetch('http://localhost:8081/api/user/delete', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({
					token: token
				}),
			})
				.then(response => {
					if (response.status === 200) {
						fetch('http://localhost:8081/api/users/logout', {
							method: 'POST',
							headers: {
								'Content-Type': 'application/json',
							},
							body: JSON.stringify({
								token: token
							}),
						}).then(response => {
							if (response.status === 200) {
								this.$router.push('/login');
							}
						});
					} else {
						// Handle error response
						console.error('Failed to delete user:', response.statusText);
					}
				})
				.catch(error => {
					console.error('Error deleting user:', error);
				});
		},
		
		/**
		 * Show or hide the filter menu
		 */
		showHideFilterMenu() {
			let filterMenu = document.getElementById('filter-menu');
			filterMenu.style.display = filterMenu.style.display === 'none' ? 'block' : 'none';
		},
		/**
		 * Confirm the changes made to the user profile
		 */
		confirmEdit() {
			const token = localStorage.getItem('token');
			fetch('http://localhost:8081/api/user/update', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({
					token: token,
					name: this.userName,
					email: this.userEmail,
				}),
			})
				.then(response => {
					if (response.status === 200) {
						this.isEditing = false;
					}
				});
		},
		/**
		 * Fetch user badges
		 */

		fetchUserBadges() {
			const token = localStorage.getItem('token');
			fetch('http://localhost:8081/api/users/badges', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({token}),
			})
				.then(response => response.json())
				.then(data => {
					for (let badge of data.badges) {
						if (badge.rank === 1) {
							badge.rank = '1er';
						} else if (badge.rank === 2) {
							badge.rank = '2eme';
						} else {
							badge.rank = '3eme';
						}
						this.userBadges.push(badge);
					}
				})
				.catch(error => {
					console.error('Error fetching user badges:', error);
				});
		}
	},
	mounted() {
		const token = localStorage.getItem('token');
		
		// Remove useless button
		if (document.getElementById('list-button')) {
			document.getElementById('list-button').style.display = 'none';
		}
		
		// Fetch successful challenges
		fetch('http://localhost:8081/api/challenges/completed', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({token}),
		})
			.then((response) => response.json())
			.then((data) => {
				this.successfulChallenges = data.challenges;
				
				for (let challenge of this.successfulChallenges) {
					
					// Translate the difficulty
					challenge.difficulty = translateDifficulty(challenge.difficulty);
					
					// Add the difficulty to the filters
					if (!this.difficultyFilters.includes(challenge.difficulty)) {
						this.difficultyFilters.push(challenge.difficulty);
					}
					
					// Add the language to the filters
					if (!this.languageFilters.includes(challenge.language)) {
						this.languageFilters.push(challenge.language);
					}
				}
			})
			.catch((error) => {
				console.error('Error fetching successful challenges:', error);
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
				this.ongoingChallenges = data.challenges;
				
				// Translate the difficulties
				for (let challenge of this.ongoingChallenges) {
					challenge.difficulty = translateDifficulty(challenge.difficulty);
				}
			})
			.catch((error) => {
				console.error('Error fetching ongoing challenges:', error);
			});
		
		// Fetch user points
		fetch('http://localhost:8081/api/users/points', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({token}),
		})
			.then((response) => response.json())
			.then((data) => {
				this.userPoints = data.points;
			})
			.catch((error) => {
				console.error('Error fetching user points:', error);
			});
		
		// Fetch account information
		fetch('http://localhost:8081/api/user', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({token}),
		})
			.then((response) => response.json())
			.then((data) => {
				this.userId = data.id;
				this.userRole = data.role;
				this.userName = data.name;
				this.userEmail = data.email;
				this.userScore = data.score;
				this.userAchievements = data.awards;
			})
			.catch((error) => {
				console.error('Error fetching account information:', error);
			});
		this.fetchUserBadges();
		
		// Hide the filter menu by default
		document.getElementById('filter-menu').style.display = 'none';
		
		// Listen to the change event on the filters
		document.querySelector('.search-bar').addEventListener('input', updateDisplay);
		document.getElementById('difficulty').addEventListener('change', updateDisplay);
		document.getElementById('language').addEventListener('change', updateDisplay);
		document.getElementById('recent').addEventListener('change', updateDisplay);
		
		/**
		 * Update the display of the challenges according to the filters and the search input
		 */
		function updateDisplay() {
			let difficultyFilterValue = document.getElementById('difficulty').value.toLowerCase();
			let languageFilterValue = document.getElementById('language').value.toLowerCase();
			let searchInputValue = document.querySelector('.search-bar').value.toLowerCase();
			let isCheckboxChecked = document.getElementById('recent').checked;
			
			let challengeListItemsFilter = document.querySelectorAll('.successful-challenge-div li');
			
			let noResults = true;
			challengeListItemsFilter.forEach((challengeItem) => {
				let challengeText = challengeItem.innerText.toLowerCase();
				
				let challengeData = extractDataFromChallengeItem(challengeItem);
				let challengeDate = challengeData.date;
				let dateWithin30Days = isCheckboxChecked ? this.isWithinLast30Days(challengeDate) : true;
				let challengeLanguage = challengeData.language.toLowerCase();
				let challengeDifficulty = challengeData.difficulty.toLowerCase();
				
				let shouldDisplay =
					(difficultyFilterValue === 'all' || difficultyFilterValue === challengeDifficulty) &&
					(languageFilterValue === 'all' || languageFilterValue === challengeLanguage) &&
					dateWithin30Days;
				
				// Manage the search with the input and the filters
				if (searchInputValue !== '') {
					shouldDisplay = shouldDisplay && challengeText.includes(searchInputValue);
				}
				
				if (shouldDisplay) {
					challengeItem.style.display = 'flex';
					noResults = false;
				} else {
					challengeItem.style.display = 'none';
				}
			});
			this.noResults = noResults;
		}
		
		/**
		 * Extract data from the challenge item
		 * Return a JS object with 3 properties : date, language, difficulty
		 */
		function extractDataFromChallengeItem(challengeItem) {
			let text = challengeItem.querySelectorAll('div')[4].innerText;
			let date = text.split('le ')[1];
			
			text = challengeItem.querySelectorAll('div')[3].innerText;
			let language = text.split(': ')[1];
			
			text = challengeItem.querySelectorAll('div')[2].innerText;
			let difficulty = text.split(': ')[1];
			
			return {date: date, language: language, difficulty: difficulty};
		}
	}
}
</script>