<template>
    <div>
        <button id="retourChallengesPage" @click="goBack">&lt; Retour</button>
        <div id="global-container">
            <div id="container-1">
                <div v-if="challenge" class="container-challenge">
                    <h1>{{ challenge.name }}</h1>
                </div>
                <div id="container-md-infos">
                    <div id="right-container">
                        <div v-if="challenge" class="container-challenge">
                            <h3>Informations</h3>
                            <ul id="chall-infos">
                                <li>Status : {{ challenge.status }}</li>
                                <li>Difficulté : {{ challenge.difficulty }}</li>
                                <li>Langage : {{ challenge.language }}</li>
                                <li>Mis en ligne le {{ new Date(Date.parse(challenge.date)).toLocaleDateString("fr-FR")
                                    }}
                                </li>
                                <li>{{ challenge.description }}</li>
                                <li>{{ challenge.points }} points</li>
                            </ul>
                        </div>
                        <div v-if="challenge" class="container-challenge">
                            <h3>Tags</h3>
                            <ul class="tags-list">
                                <li v-for="tag in tags" :key="tag.id" class="tag-item"
                                    :style="{ backgroundColor: tag.color }">
                                    {{ tag.name }}
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div v-if="challenge" class="container-challenge" id="markdown">
                        <MarkdownViewer :challengeId="challenge.id" />
                    </div>
                </div>
                <div v-if="challenge" class="container-challenge" id="flag-container">
                    <form @submit.prevent="submit">
                        <input type="text" id="inputFlag" v-model="flag" />
                        <button type="submit" id="submitSolution">Valider</button>
                    </form>
                    <div v-if="validationResult"
                        :class="{ 'validation-success': isValidationSuccess, 'validation-failure': !isValidationSuccess }">
                        <p>{{ validationMessage }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import MarkdownViewer from '@/components/MarkdownViewer.vue';
import '@/assets/styles/oneChallenge.css';
import {translateDifficulty, translateStatus} from "@/utils/functions";

export default {
    name: 'OneChallengePage',
    components: {
        MarkdownViewer
    },
    props: ['id'],
    data() {
        return {
            challenge: null,
            tags: [],
            flag: '',
            validationResult: false,
            validationMessage: '',
            isValidationSuccess: false
        };
    },
    mounted() {

        // Fetch the challenge details and tags
        this.fetchChallengeDetails();
        this.fetchChallengeTags();
    },
    methods: {

        /**
         * Fetch the challenge details
         */
        async fetchChallengeDetails() {
            try {
                const token = localStorage.getItem('token');
                const response = await fetch(`http://localhost:8081/api/challenges/${this.id}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ token }),
                });
                if (response.ok) {
                    this.challenge = await response.json();

                    // Translate the difficulties
                    this.challenge.difficulty = translateDifficulty(this.challenge.difficulty);

                    // Translate the statuses
                    this.challenge.status = translateStatus(this.challenge.status);
                } else {
                    console.error('Failed to fetch challenge details');
                }
            } catch (error) {
                console.error('Error fetching challenge details:', error);
            }
        },

        /**
         * Fetch the challenge tags
         */
        async fetchChallengeTags() {
            try {
                const response = await fetch(`http://localhost:8081/api/challenges/${this.id}/tags`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    this.tags = data.tags;
                } else {
                    console.error('Failed to fetch challenge tags');
                }
            } catch (error) {
                console.error('Error fetching challenge tags:', error);
            }
        },

        /**
         * Go back to the challenges page
         */
        goBack() {
            this.$router.push('/challenges');
        },

        /**
         * Submit the flag
         */
        async submit() {
            try {
                const token = localStorage.getItem('token');
                const response = await fetch(`http://localhost:8081/api/challenges/${this.id}/flag`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ token, flag: this.flag }),
                });
                const result = await response.json();
                this.validationResult = true;
                if (result.result === 'OK') {
                    this.isValidationSuccess = true;
                    this.validationMessage = 'Bravo! Vous avez réussi le défi.';
                } else {
                    this.isValidationSuccess = false;
                    this.validationMessage = 'Désolé, votre solution est incorrecte.';
                }
            } catch (error) {
                console.error('Error submitting flag:', error);
                this.validationResult = true;
                this.isValidationSuccess = false;
                this.validationMessage = 'Une erreur est survenue lors de la validation.';
            }
        }
    }
};
</script>