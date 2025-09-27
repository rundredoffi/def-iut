<template>
	<li class="challenge-card" v-for="challenge in challenges" :key="challenge.id" @click="navigateToChallenge(challenge.id)" :class="`difficulty-${challenge.difficulty.toLowerCase()}`">
		<p>{{ challenge.name }}</p>
		<p>Status : {{ challenge.status }}</p>
		<p>Difficulté : {{ challenge.difficulty }}</p>
		<p>Langage : {{ challenge.language }}</p>
		<p>Mis en ligne le {{ dateFormat(challenge.date) }}</p>
	</li>
</template>

<script>
import {dateFormat} from "@/utils/functions";
export default {
	name: "ChallengeCard",
	props: {
		challenges: {
			type: Object,
			required: true,
		}
	},
	methods: {
		/**
		 * Navigate to the challenge page
		 */
		navigateToChallenge(id) {
			this.$router.push('/challenges/' + id, {params: {id: id}});
		},
		dateFormat,
	}
}
</script>

<style scoped>

.challenge-card {
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	align-items: center;
	width: 300px;
	height: 300px;
	margin: 10px;
	padding: 10px;
	border-radius: 10px;
	box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2);
}

.difficulty-facile {
	background-color: rgba(0, 255, 0, 0.3);
}

.difficulty-moyen {
	background-color: rgba(255, 255, 0, 0.3);
}

.difficulty-difficile {
	background-color: rgba(255, 0, 0, 0.3);
}
</style>