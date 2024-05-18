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
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.FileCustom;
import me.swezedcode.client.module.Module;

public class TabGuiEnabled extends FileCustom {
	private static String fileName = "tabgui.txt";

	public static void loadEnabled() {
		Manager.getManager().getFileManager();
		if (!new File(FileManager.getDirectory(), TabGuiEnabled.fileName).exists()) {
			FileManager.createFile(TabGuiEnabled.fileName);
			return;
		}
		if (FileManager.readFile(TabGuiEnabled.fileName).isEmpty()) {
			return;
		}
		final String contents = FileManager.readFile(TabGuiEnabled.fileName);
		final JsonObject json = (JsonObject) new JsonParser().parse(contents);
		for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
			if (CommandTabGui.on) {
				CommandTabGui.setOn(Boolean.parseBoolean(entry.getValue().toString()));
			}
			if(!CommandBlur.on){
				CommandTabGui.setOn(Boolean.parseBoolean(entry.getValue().toString()));
			}
		}
	}

	public static void saveEnabled() {
		final JsonObject json = new JsonObject();
		json.addProperty("TabGui:", CommandTabGui.on);
		FileManager.writeFile(TabGuiEnabled.fileName, json.toString());
	}

	public TabGuiEnabled() {
		super("tabgui");
	}
}