<template>
	<div>
		<h1 class="main-title">Défis</h1>
		<div class="challenge-list">
			<!-- div contenant le menu des filtres -->
			<ChallengeFilters :difficulty-filters="difficultyFilters" :language-filters="languageFilters" :status-filters="statusFilters"/>
			<!-- div contenant la grille des défis -->
			<ul class="challenge-list-ul">
				<ChallengeCard :challenges=challenges></ChallengeCard>
			</ul>
			
			<div id="no-results">
				Aucun résultat trouvé.
			</div>
		</div>
	</div>
</template>

<script>
import '@/assets/styles/challenges.css'
import {translateDifficulty, translateStatus} from "@/utils/functions";
import ChallengeCard from "@/components/ChallengeCard";
import ChallengeFilters from "@/components/ChallengeFilters";

export default {
	name: 'ChallengesPage',
	components: {
		ChallengeCard,
		ChallengeFilters,
	},
	data() {
		return {
			difficultyFilters: [],
			languageFilters: [],
			statusFilters: [],
			challenges: [],
		};
	},
	mounted() {
		
		// Listen to the change event on the filters
		document.querySelector('.search-bar').addEventListener('input', updateDisplay);
		document.getElementById('difficulty').addEventListener('change', updateDisplay);
		document.getElementById('language').addEventListener('change', updateDisplay);
		document.getElementById('status').addEventListener('change', updateDisplay);
		document.getElementById('recent').addEventListener('change', updateDisplay);
		
		const token = localStorage.getItem('token');
		fetch('http://localhost:8081/api/challenges/list', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({token}),
		})
			.then((response) => response.json())
			.then((data) => {
				data.challenges.forEach((challenge) => {
					
					// Add the challenge to the list of challenges to display them in the template part
					this.challenges.push(challenge);
					
					// Translate difficulty and status
					challenge.difficulty = translateDifficulty(challenge.difficulty);
					challenge.status = translateStatus(challenge.status);
					
					// Add the language to the list of languages to display them in the language filter in the template part
					if (!this.languageFilters.includes(challenge.language)) {
						this.languageFilters.push(challenge.language);
					}
					
					// Add the difficulty to the list of difficulties to display them in the difficulty filter in the template part
					if (!this.difficultyFilters.includes(challenge.difficulty)) {
						this.difficultyFilters.push(challenge.difficulty);
					}
					
					// Add the status to the list of status to display them in the status filter in the template part
					if (!this.statusFilters.includes(challenge.status)) {
						this.statusFilters.push(challenge.status);
					}
				});
			})
			.catch((error) => {
				console.error('Error fetching challenges: ', error);
			});
		
		/**
		 * Update the display of the challenges according to the filters and the search input
		 */
		function updateDisplay() {
			let difficultyFilterValue = document.getElementById('difficulty').value.toLowerCase();
			let languageFilterValue = document.getElementById('language').value.toLowerCase();
			let statusFilterValue = document.getElementById('status').value.toLowerCase();
			let searchInputValue = document.querySelector('.search-bar').value.toLowerCase();
			let noResultsMessage = document.getElementById('no-results');
			let isCheckboxChecked = document.getElementById('recent').checked;
			
			let noResults = true;
			
			let challengeListItemsFilter = document.querySelectorAll('.challenge-list-ul li');
			
			challengeListItemsFilter.forEach((challengeItem) => {
				let challengeText = challengeItem.innerText.toLowerCase();
				
				let challengeDate = extractDateFromChallengeItem(challengeItem);
				let dateWithin30Days = isCheckboxChecked ? isWithinLast30Days(challengeDate) : true;
				let challengeLanguage = extractLanguageFromChallengeItem(challengeItem).toLowerCase();
				let challengeDifficulty = extractDifficultyFromChallengeItem(challengeItem).toLowerCase();
				let challengeStatus = extractStatusFromChallengeItem(challengeItem).toLowerCase()
				
				let shouldDisplay =
					(difficultyFilterValue === 'all' || difficultyFilterValue === challengeDifficulty) &&
					(languageFilterValue === 'all' || languageFilterValue === challengeLanguage) &&
					(statusFilterValue === 'all' || statusFilterValue === challengeStatus) &&
					dateWithin30Days;
				
				// Manage the search with the input and the filters
				if (searchInputValue !== '') {
					shouldDisplay = shouldDisplay && challengeText.includes(searchInputValue);
				} else if (challengeText.includes(searchInputValue)) {
					shouldDisplay = shouldDisplay && challengeText.includes(searchInputValue);
				}
				
				if (shouldDisplay) {
					challengeItem.style.display = 'flex';
					noResults = false;
				} else {
					challengeItem.style.display = 'none';
				}
			});
			noResultsMessage.style.display = noResults ? 'block' : 'none';
		}
	}
}

/**
 * Extract the date from the challenge item
 */
function extractDateFromChallengeItem(challengeItem) {
	let text = challengeItem.querySelectorAll('p')[4].innerText;
	let date = text.split('le ')[1];
	return date;
}

/**
 * Extract the difficulty from the challenge item
 */
function extractDifficultyFromChallengeItem(challengeItem) {
	let text = challengeItem.querySelectorAll('p')[2].innerText;
	let difficulty = text.split(' : ')[1];
	return difficulty;
}

/**
 * Extract the language from the challenge item
 */
function extractLanguageFromChallengeItem(challengeItem) {
	let text = challengeItem.querySelectorAll('p')[3].innerText;
	let language = text.split(' : ')[1];
	return language;
}

/**
 * Extract the status from the challenge item
 */
function extractStatusFromChallengeItem(challengeItem) {
	let text = challengeItem.querySelectorAll('p')[1].innerText;
	let status = text.split(' : ')[1];
	return status;
}

/**
 * Check if a date is within the last 30 days
 */
function isWithinLast30Days(dateString) {
	let year = dateString.split('-')[0];
	let month = dateString.split('-')[1] - 1;
	let day = dateString.split('-')[2];
	let date = new Date(year, month, day);
	let currentDate = new Date();
	let differenceInDays = (currentDate - date) / (1000 * 60 * 60 * 24);
	return differenceInDays <= 30;
}
</script>