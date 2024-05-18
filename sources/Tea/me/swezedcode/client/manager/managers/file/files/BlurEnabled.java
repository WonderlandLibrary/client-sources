package me.swezedcode.client.manager.managers.file.files;

import java.io.File;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.swezedcode.client.command.commands.CommandBlur;
import me.swezedcode.client.gui.alts.AccountManagementException;
import me.swezedcode.client.gui.alts.Alt;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.FileCustom;
import me.swezedcode.client.module.Module;

public class BlurEnabled extends FileCustom {
	private static String fileName = "blur.txt";

	public static void loadEnabled() {
		Manager.getManager().getFileManager();
		if (!new File(FileManager.getDirectory(), BlurEnabled.fileName).exists()) {
			FileManager.createFile(BlurEnabled.fileName);
			return;
		}
		if (FileManager.readFile(BlurEnabled.fileName).isEmpty()) {
			return;
		}
		final String contents = FileManager.readFile(BlurEnabled.fileName);
		final JsonObject json = (JsonObject) new JsonParser().parse(contents);
		for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
			if (CommandBlur.on) {
				CommandBlur.setOn(Boolean.parseBoolean(entry.getValue().toString()));
			}
			if(!CommandBlur.on){
				CommandBlur.setOn(Boolean.parseBoolean(entry.getValue().toString()));
			}
		}
	}

	public static void saveEnabled() {
		final JsonObject json = new JsonObject();
		json.addProperty("Blur:", CommandBlur.on);
		FileManager.writeFile(BlurEnabled.fileName, json.toString());
	}

	public BlurEnabled() {
		super("blur");
	}
}