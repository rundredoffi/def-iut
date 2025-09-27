// Translation maps in French
const difficultyMap = {
	EASY: "Facile",
	MEDIUM: "Moyen",
	HARD: "Difficile"
}

const statusMap = {
	'NOT STARTED': 'Non commencé',
	'STARTED': 'Commencé',
	'COMPLETED': 'Terminé'
}

/**
 * Format the date to French format
 * @param dateString
 * @returns {string}
 */
export function dateFormat(dateString) {
	return new Date(dateString).toLocaleDateString('fr-FR');
}

/**
 * Returns the French translation of a difficulty
 * @param difficulty String (usually in difficultyMap)
 * @returns string Translated difficulty if translation found, otherwise return the same string
 */
export function translateDifficulty(difficulty) {
	return difficultyMap[difficulty.toUpperCase()] || difficulty;
}

/**
 * Returns the French translation of a status
 * @param status String (usually in statusMap)
 * @returns string Translated status if translation found, otherwise return the same string
 */
export function translateStatus(status) {
	return statusMap[status.toUpperCase()] || status;
}