package me.swezedcode.client.manager.managers.file.files;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.FileCustom;
import me.swezedcode.client.module.Module;

public class Keybinds extends FileCustom {
	private static String fileName = "keybinds.txt";

	public static void loadKeys() {
		Manager.getManager().getFileManager();
		if (!new File(FileManager.getDirectory(), Keybinds.fileName).exists()) {
			FileManager.createFile(Keybinds.fileName);
			return;
		}
		if (FileManager.readFile(Keybinds.fileName).isEmpty()) {
			return;
		}
		final String contents = FileManager.readFile(Keybinds.fileName);
		final JsonObject json = (JsonObject) new JsonParser().parse(contents);
		for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
			for (final Module mod : Manager.getManager().getModuleManager().getModules()) {
				if (mod.getName().equalsIgnoreCase(entry.getKey())) {
					mod.setKeycode(Integer.parseInt(entry.getValue().toString()));
				}
			}
		}
	}

    public static void saveKeys() {
        final JsonObject json = new JsonObject();
        for (final Module mod : Manager.getManager().getModuleManager().getModules()) {
            json.addProperty(mod.getName(), mod.getKeycode());
        }
        FileManager.writeFile(Keybinds.fileName, json.toString());
    }

	public Keybinds() {
		super("keybinds");
	}
}