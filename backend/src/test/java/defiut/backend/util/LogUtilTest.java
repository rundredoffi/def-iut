package defiut.backend.util;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour LogUtil
 * Cette classe contient tous les tests unitaires nécessaires pour assurer
 * une couverture à 100% de la classe LogUtil.
 *
 * @author AI Assistant
 * @version 1.0
 * @see LogUtil
 */
class LogUtilTest {

	/**
	 * Chemin du répertoire de logs pour les tests
	 */
	private static final String TEST_LOG_DIR = "./logs";

	/**
	 * Chemin complet du fichier de logs pour les tests
	 */
	private static final String TEST_LOG_FILE = "./logs/logs.txt";

	/**
	 * Méthode exécutée avant chaque test.
	 * Nettoie l'environnement de test en supprimant les fichiers de logs existants.
	 *
	 * @throws IOException si une erreur survient lors de la suppression des fichiers
	 */
	@BeforeEach
	void setUp() {
		cleanupTestFiles();
	}

	/**
	 * Méthode exécutée après chaque test.
	 * Nettoie l'environnement de test en supprimant les fichiers de logs créés.
	 *
	 * @throws IOException si une erreur survient lors de la suppression des fichiers
	 */
	@AfterEach
	void tearDown() {
		cleanupTestFiles();
	}

	/**
	 * Méthode utilitaire pour nettoyer les fichiers de test.
	 * Supprime uniquement le fichier de logs en préservant .gitkeep
	 */
	private void cleanupTestFiles() {
		try {
			// Supprime uniquement le fichier de logs
			Path logFile = Paths.get(TEST_LOG_FILE);
			Files.deleteIfExists(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Teste le constructeur privé de LogUtil.
	 * Vérifie que le constructeur privé est accessible et peut être instancié
	 * pour une couverture de code complète.
	 *
	 * @throws Exception si une erreur survient lors de l'accès au constructeur
	 */
	@Test
	void testConstructeur() throws Exception {
		Constructor<LogUtil> constructor = LogUtil.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	/**
	 * Teste l'écriture d'un log dans un nouveau fichier.
	 * Vérifie la création du fichier et le format correct du message de log.
	 *
	 * @throws IOException si une erreur survient lors de l'écriture ou la lecture du fichier
	 */
	@Test
	void testEcriture() throws IOException {
		String testMessage = "Test log message";
		LogUtil.writeLog(testMessage);

		assertTrue(Files.exists(Paths.get(TEST_LOG_FILE)), "Le fichier de log devrait être créé");

		String content = Files.readString(Paths.get(TEST_LOG_FILE));
		assertTrue(content.contains(testMessage), "Le message devrait être présent dans le fichier");
		assertTrue(content.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} - " + testMessage + "\\n"),
				"Le format du message de log devrait être correct");
	}

	/**
	 * Teste l'écriture de plusieurs logs dans un fichier existant.
	 * Vérifie que les messages sont correctement ajoutés à la suite.
	 *
	 * @throws IOException si une erreur survient lors de l'écriture ou la lecture du fichier
	 */
	@Test
	void testEcritureMultiple() throws IOException {
		String message1 = "Premier message";
		String message2 = "Deuxième message";

		LogUtil.writeLog(message1);
		LogUtil.writeLog(message2);

		String content = Files.readString(Paths.get(TEST_LOG_FILE));
		assertTrue(content.contains(message1), "Le premier message devrait être présent");
		assertTrue(content.contains(message2), "Le deuxième message devrait être présent");
	}

	/**
	 * Teste l'ouverture d'un reader sur un fichier inexistant.
	 * Vérifie que null est retourné quand le fichier n'existe pas.
	 *
	 * @throws IOException si une erreur inattendue survient
	 */
	@Test
	void testLectureFichierInexistant() throws IOException {
		assertNull(LogUtil.openReader(), "Devrait retourner null pour un fichier inexistant");
	}

	/**
	 * Teste l'ouverture d'un reader sur un fichier existant.
	 * Vérifie que le reader est créé et peut lire le contenu correctement.
	 *
	 * @throws IOException si une erreur survient lors de la lecture du fichier
	 */
	@Test
	void testLecture() throws IOException {
		String testMessage = "Test message pour lecture";
		LogUtil.writeLog(testMessage);

		BufferedReader reader = LogUtil.openReader();
		assertNotNull(reader, "Le reader ne devrait pas être null");

		String line = reader.readLine();
		assertTrue(line.contains(testMessage), "Le message lu devrait correspondre au message écrit");

		reader.close();
	}
}