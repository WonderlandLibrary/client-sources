package me.swezedcode.client.manager.managers.file.files;

import java.io.File;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.swezedcode.client.command.commands.CommandBlur;
import me.swezedcode.client.command.commands.CommandTabGui;
import me.swezedcode.client.gui.alts.AccountManagementException;
import me.swezedcode.client.gui.alts.Alt;
import me.swezedcode.client.gui.other.ColorPickerGui;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.FileCustom;
import me.swezedcode.client.module.Module;

public class Colours extends FileCustom {
	private static String fileName = "colours.txt";

	public static void loadEnabled() {
		Manager.getManager().getFileManager();
		if (!new File(FileManager.getDirectory(), Colours.fileName).exists()) {
			FileManager.createFile(Colours.fileName);
			return;
		}
		if (FileManager.readFile(Colours.fileName).isEmpty()) {
			return;
		}
		final String contents = FileManager.readFile(Colours.fileName);
		final JsonObject json = (JsonObject) new JsonParser().parse(contents);
		for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
			
		}
	}

	public static void saveEnabled() {
		final JsonObject json = new JsonObject();
		json.addProperty("Defualt:", ColorPickerGui.defualt);
		json.addProperty("Orange:", ColorPickerGui.orange);
		json.addProperty("Blue:", ColorPickerGui.blue);
		json.addProperty("Red:", ColorPickerGui.red);
		json.addProperty("Cyan:", ColorPickerGui.cyan);
		json.addProperty("Yellow:", ColorPickerGui.yellow);
		FileManager.writeFile(Colours.fileName, json.toString());
	}

	public Colours() {
		super("colours");
	}
}