package defiut.backend.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for managing application logs
 */
public final class LogUtil {
	/**
	 * File where logs are stored
	 */
	private static final File logs = new File("./logs/logs.txt");

	/**
	 * Private constructor to prevent instantiation
	 */
	private LogUtil() {
	}

	/**
	 * Writes a log entry with timestamp to the log file
	 * Creates log file and directory if they don't exist
	 *
	 * @param log The message to be logged
	 */
	public static void writeLog(String log) {
		try {
			if (!logs.exists()) {
				logs.getParentFile().mkdirs();
				logs.createNewFile();
			}
			LocalDateTime dateTime = LocalDateTime.now();
			String line = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + log;
			try (FileWriter writer = new FileWriter(logs, true);
			     BufferedWriter bw = new BufferedWriter(writer)) {
				bw.write(line);
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens a BufferedReader for reading the log file
	 *
	 * @return BufferedReader for the log file, or null if file doesn't exist
	 * @throws IOException if an I/O error occurs while opening the file
	 */
	public static BufferedReader openReader() throws IOException {
		if (!logs.exists()) return null;
		return new BufferedReader(new FileReader(logs));
	}
}