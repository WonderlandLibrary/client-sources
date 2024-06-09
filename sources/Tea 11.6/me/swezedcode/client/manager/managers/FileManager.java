package me.swezedcode.client.manager.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.FileUtils;

import me.swezedcode.client.gui.alts.Alt;
import me.swezedcode.client.gui.alts.Manager;
import me.swezedcode.client.gui.other.ColorPickerGui;
import me.swezedcode.client.manager.managers.file.files.BlurEnabled;
import me.swezedcode.client.manager.managers.file.files.Colours;
import me.swezedcode.client.manager.managers.file.files.Enabled;
import me.swezedcode.client.manager.managers.file.files.Gui;
import me.swezedcode.client.manager.managers.file.files.Keybinds;
import me.swezedcode.client.manager.managers.file.files.TabGuiEnabled;
import me.swezedcode.client.utils.Wrapper;

public class FileManager {
	private static File directory;

	static {
		FileManager.directory = new File(Wrapper.getMinecraft().mcDataDir.getAbsolutePath(), "Tea");
	}

	public static void createFile(final String fileName) {
		try {
			final BufferedWriter writer = new BufferedWriter(
					new FileWriter(new File(getDirectory().getAbsolutePath(), fileName)));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File getDirectory() {
		return FileManager.directory;
	}

	public static String readFile(final String fileName) {
		try {
			final File file = new File(getDirectory().getAbsolutePath(), fileName);
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(new DataInputStream(new FileInputStream(file))));
			final String contents = FileUtils.readFileToString(file);
			reader.close();
			return contents;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void saveKeys() {
		Keybinds.saveKeys();
	}

	public static void saveAlts() {
		Keybinds.saveKeys();
	}

	public static void saveGui() {
		Gui.saveSettings();
	}

	public static void loadGui() {
		Gui.loadSettings();
	}
	
	public static void loadEnabled() {
		Enabled.loadEnabled();
	}
	
	public static void saveEnabled() {
		Enabled.saveEnabled();
	}

	public static List<Alt> getAlts() {
		return Manager.altList;
	}

	public static void writeFile(final String fileName, final String str) {
		try {
			final BufferedWriter writer = new BufferedWriter(
					new FileWriter(new File(getDirectory().getAbsolutePath(), fileName)));
			writer.write(str);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setup() {
		FileManager.directory = new File(Wrapper.getMinecraft().mcDataDir.getAbsolutePath(), "Tea");
		if (!FileManager.directory.exists()) {
			FileManager.directory.mkdirs();
		}
		Enabled.loadEnabled();
		Keybinds.loadKeys();
		BlurEnabled.loadEnabled();
		TabGuiEnabled.loadEnabled();
		Colours.loadEnabled();
	}
}
