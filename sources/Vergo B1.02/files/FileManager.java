package xyz.vergoclient.files;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileManager {
	
	// Files used by the client
	public static File mainDir = new File("Vergo"),
			configDir = new File(mainDir, "configs"),
			keybindsDir = new File(mainDir, "keybinds"),
			libsDir = new File(mainDir, "libs"),
			assetsDir = new File(mainDir, "assets"),
			defaultKeybindsFile = new File(keybindsDir, "default.json"),
			altsFile = new File(mainDir, "alts.json"),
			discordLibWindows = new File(libsDir, "discord_game_sdk.dll"),
			clickguiTabs = new File(mainDir, "tabs.json");
	
	// Creates dirs if they don't exist
	public static void init() {
		if(!mainDir.exists())
			mainDir.mkdirs();

		if(!configDir.exists())
			configDir.mkdirs();

		if(!libsDir.exists())
			libsDir.mkdirs();

		if(!assetsDir.exists())
			libsDir.mkdirs();
	}
	
	// Writes a byte array to a file
	public static void writeToFile(File file, byte[] bytes) {
		try {
			FileUtils.writeByteArrayToFile(file, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Writes a string to a file
	public static void writeToFile(File file, String string) {
		writeToFile(file, string.getBytes());
	}
	
	// Writes an object serialized in json
	public static void writeToFile(File file, Object obj) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// Writes to the file
		writeToFile(file, gson.toJson(obj).toString().getBytes());
		
	}
	
	// Reads a string from a file
	public static String readFromFile(File file) {
		try {
			StringBuilder builder = new StringBuilder();
			for (String line : FileUtils.readLines(file, Charset.defaultCharset()))
				builder.append(line);
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// Reads a json file and returns an object
	public static <T> T readFromFile(File file, T t) {
		try {
			return (T) new Gson().fromJson(readFromFile(file), t.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	// Downloads a file
	public static void downloadFile(String url, File file) {
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			byte[] bytes = IOUtils.toByteArray(connection.getInputStream());
			connection.getInputStream().close();
			writeToFile(file, bytes);
		} catch (Exception e) {
			
		}
	}

	public static String readInputStream(InputStream inputStream) {
		StringBuilder stringBuilder = new StringBuilder();

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = bufferedReader.readLine()) != null)
				stringBuilder.append(line).append('\n');

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
	
}
