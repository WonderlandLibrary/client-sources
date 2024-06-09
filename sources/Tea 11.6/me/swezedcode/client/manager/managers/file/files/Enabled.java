package me.swezedcode.client.manager.managers.file.files;

import java.io.File;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.FileCustom;
import me.swezedcode.client.module.Module;

public class Enabled extends FileCustom {
	private static String fileName = "enabled.txt";

	public static void loadEnabled() {
		Manager.getManager().getFileManager();
		if (!new File(FileManager.getDirectory(), Enabled.fileName).exists()) {
			FileManager.createFile(Enabled.fileName);
			return;
		}
		if (FileManager.readFile(Enabled.fileName).isEmpty()) {
			return;
		}
		final String contents = FileManager.readFile(Enabled.fileName);
		final JsonObject json = (JsonObject) new JsonParser().parse(contents);
		for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
			for (final Module mod : Manager.getManager().getModuleManager().getModules()) {
				if (mod.getName() == "Xray") {
					mod.setToggled(false);
				}
				if (mod.getName().equalsIgnoreCase(entry.getKey())) {
					mod.setToggled(Boolean.parseBoolean(entry.getValue().toString()));
				}
			}
		}
	}

	public static void saveEnabled() {
		final JsonObject json = new JsonObject();
		for (final Module mod : Manager.getManager().getModuleManager().getModules()) {
			json.addProperty(mod.getName(), mod.isToggled());
		}
		FileManager.writeFile(Enabled.fileName, json.toString());
	}

	public Enabled() {
		super("enabled");
	}
}