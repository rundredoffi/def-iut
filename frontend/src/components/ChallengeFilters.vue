<template>
	<div class="filter-container">
		<div class="content-container">
			<div id="icons-container">
				<div class="content-container">
					<div class="search-filters">
						<div class="search-bar-wrapper">
							<input
                  type="text"
                  placeholder="Rechercher..."
                  class="search-bar"
                  v-model='searchQuery'
                  @input='emitFilters'
              />
							<button id="reset-button" class="clear-button" @click="resetFilters()">&#10005;</button>
						</div>
						<button id="filter-button" class="accent2" @click="showHideFilterMenu()">Filtres</button>
						<button id="cards-button" class="accent2" @click="viewCards()">
							<img class="icon-challenges" src="@/assets/img/cards.png" alt="Cards View"/>
						</button>
						<button id="list-button" class="accent2" @click="viewList()">
							<img class="icon-challenges" src="@/assets/img/list.png" alt="List View"/>
						</button>
					</div>
				</div>
			</div>
			<div id="filter-menu" class="filter-menu-challenges-page">
				<div class="filter">
					<p>Difficulté</p>
					<select
              name="difficulty"
              id="difficulty"
              v-model='selectedDifficulty'
              @change="emitFilters"
          >
						<option value="ALL">Toutes les difficultés</option>
						<option v-for="difficulty in difficultyFilters" v-bind:key="difficulty" v-bind:value="difficulty">{{difficulty}}
						</option>
					</select>
				</div>
				<div class="filter">
					<p>Language</p>
					<select
              name="language"
              id="language"
              v-model='selectedLanguage'
              @change="emitFilters"
          >
						<option value="ALL">Tous les langages</option>
						<option v-for="language in languageFilters" v-bind:key="language" v-bind:value="language">
							{{ language }}
						</option>
					</select>
				</div>
				<div class="filter">
					<p>Status</p>
					<select
              name="status"
              id="status"
              v-model='selectedStatus'
              @change="emitFilters"
          >
						<option value="ALL">Tout status</option>
						<option v-for="status in statusFilters" v-bind:key="status" v-bind:value="status">{{ status }}
						</option>
					</select>
				</div>
				<div class="filter">
					<p>Mis en ligne récemment</p>
          <input
              type="checkbox"
              name="recent"
              id="recent"
              v-model='showRecentlyOnly'
              @change="emitFilters"
          />
        </div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: "FilterComp",
	props: {
		difficultyFilters: {
			type: Array,
			required: true,
		},
		languageFilters: {
			type: Array,
			required: true,
		},
		statusFilters: {
			type: Array,
			required: true,
		},
	},
  data(){
    return{
      selectedDifficulty:'ALL',
      selectedLanguage:'ALL',
      selectedStatus:'ALL',
      showRecentlyOnly:false,
      searchQuery:''
    }
  },
	methods: {
		/**
		 * Show or hide the filter menu
		 */
		showHideFilterMenu() {
			let filterMenu = document.getElementById('filter-menu');
			filterMenu.style.display = filterMenu.style.display === 'none' ? 'block' : 'none';
		},

		/**
		 * Reset filters
		 */
		resetFilters() {
			this.selectedDifficulty = 'ALL';
      this.selectedLanguage = 'ALL';
      this.selectedStatus = 'ALL'
      this.showRecentlyOnly = false;
      this.emitFilters();
		},
    emitFilters() {
      this.$emit('filter-updated',{
        difficulty: this.selectedDifficulty,
        language: this.selectedLanguage,
        status: this.selectedStatus,
        recent: this.showRecentlyOnly,
        search : this.searchQuery
      })
    },
		/**
		 * Change the view to list
		 */
		viewList() {
			let listButton = document.getElementById('list-button');
			listButton.style.display = 'none';
			
			let cardsButton = document.getElementById('cards-button');
			cardsButton.style.display = 'block';
			
			let challengeListItems = document.querySelectorAll('.challenge-list-ul li');
			challengeListItems.forEach((challengeItem) => {
				challengeItem.classList.remove('challenge-card');
				challengeItem.classList.add('challenge-view-list');
			});
		},
		
		/**
		 * Change the view to cards
		 */
		viewCards() {
			let listButton = document.getElementById('list-button');
			listButton.style.display = 'block';
			
			let cardsButton = document.getElementById('cards-button');
			cardsButton.style.display = 'none';
			
			let challengeListItems = document.querySelectorAll('.challenge-list-ul li');
			challengeListItems.forEach((challengeItem) => {
				challengeItem.classList.remove('challenge-view-list');
				challengeItem.classList.add('challenge-card');
			});
		},
	},
	mounted() {
		// Hide filter menu by default
		document.getElementById('filter-menu').style.display = 'none';
	}
}
</script>

<style scoped>
#filter-menu {
	width: 300px;
	background-color: white;
	border-radius: 10px;
	box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2);
	padding-bottom: 5px;
}

#filter-menu select {
	width: 80%;
	height: 43px;
	border-radius: 10px;
	border: 1px solid #ccc;
	padding: 10px;
}

.filter-container {
	width: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
}

.content-container {
	display: flex;
	flex-direction: column;
	width: fit-content;
}

.search-bar-wrapper {
	position: relative;
	display: flex;
	align-items: center;
	width: 300px;
}

.search-bar {
	height: 50px;
	border-radius: 10px;
	border: 1px solid #ccc;
	padding-right: 35px;
	box-sizing: border-box;
	margin-bottom: 0;
}

.clear-button {
	position: absolute;
	right: 5px;
	height: 25px;
	width: 30px;
	border: none;
	background-color: white;
	cursor: pointer;
	font-size: 16px;
	color: #888;
	display: flex;
	align-items: center;
	justify-content: center;
	border-radius: 50%;
}

.clear-button:hover {
	color: #888;
	background-color: rgba(255, 255, 255, 0.05);
}

#icons-container {
	justify-content: center;
}

#icons-container button:not(.clear-button) {
	cursor: pointer;
	border-radius: 10px;
	margin: 5px;
	background-color: #66a3b2;
	height: 50px;
}

.search-filters {
	width: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	box-sizing: border-box;
	gap: 15px;
}


.icon-challenges {
	width: 20px;
	height: 20px;
}

.filter {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 10px;
	text-align: left;
}
</style>