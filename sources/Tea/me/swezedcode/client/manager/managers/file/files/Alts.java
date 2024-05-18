package me.swezedcode.client.manager.managers.file.files;

import java.io.File;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.swezedcode.client.gui.alts.AccountManagementException;
import me.swezedcode.client.gui.alts.Alt;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.FileCustom;

public class Alts extends FileCustom {
	private static String fileName = "alts.txt";

	public static void loadAlts() {
		Manager.getManager().getFileManager();
		if (!new File(FileManager.getDirectory(), fileName).exists()) {
			FileManager.createFile(fileName);
			return;
		}
		if (FileManager.readFile(fileName).isEmpty()) {
			return;
		}
		FileManager.readFile(fileName);
		JsonParser parser = new JsonParser();

		JsonObject json = (JsonObject) parser.parse(FileManager.readFile(fileName));
		for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
			Manager.getManager().getFileManager().getAlts().add(new Alt((String) entry.getKey(), ((JsonElement) entry.getValue()).getAsString()));
		}
	}

	public static void saveAlts() throws AccountManagementException {
		JsonObject json = new JsonObject();
		for (Alt alt : Manager.getManager().getFileManager().getAlts()) {
			json.addProperty(alt.getUsername(), alt.getPassword());
		}
		FileManager.writeFile(fileName, json.toString());
	}

	public Alts() {
		super("alts");
	}
}