package de.tired.api.util.misc;

import de.tired.api.performanceMode.PerformanceGui;
import de.tired.api.performanceMode.UsingType;
import de.tired.api.util.misc.TimeStorage;
import de.tired.interfaces.IHook;
import de.tired.api.guis.altmanager.Account;
import de.tired.api.guis.altmanager.AltManagerRegistry;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.Setting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.api.guis.clickgui.setting.impl.TextBoxSetting;
import de.tired.module.Module;
import de.tired.tired.Tired;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.*;
import java.util.Arrays;

public enum FileUtil implements IHook {

	FILE_UTIL;
	private final String mainPath = MC.mcDataDir + "/" + Tired.INSTANCE.CLIENT_NAME;

	@SneakyThrows
	public void saveColor() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/colors")).exists())
			(new File(mainPath + "/colors")).createNewFile();

		FileWriter writer = new FileWriter(mainPath + "/colors");
		for (Module module : Tired.INSTANCE.moduleManager.getModuleList()) {
			for (Setting s : Tired.INSTANCE.settingsManager.getSettingsByMod(module)) {
				if (s instanceof ColorPickerSetting) {
					writer.write(module.getName() + ":" + s.getName() + ":" + ((ColorPickerSetting) s).getColorPickerColor().getRGB() + "\n");
				}
			}
		}
		writer.flush();
		writer.close();
	}

	@SneakyThrows
	public void savePerformanceMode(UsingType usingType) {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/performanceMode")).exists())
			(new File(mainPath + "/performanceMode")).createNewFile();

		FileWriter writer = new FileWriter(mainPath + "/performanceMode");


		usingType = PerformanceGui.usingType;

		if (usingType == UsingType.NORMAL_PERFORMANCE) {
			writer.write("NormalPerformance");
		}

		if (usingType == UsingType.HIGH_PERFORMANCE) {
			writer.write("HighPerformance");
		}

		writer.flush();
		writer.close();
	}

	@SneakyThrows
	public void loadTime() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/time")).exists())
			(new File(mainPath + "/time")).createNewFile();

		BufferedReader reader = new BufferedReader(new FileReader((mainPath + "/time")));
		String line;

		while ((line = reader.readLine()) != null) {

			TimeStorage.currentTime = Long.parseLong(line);

		}
		reader.close();
	}

	public String getTextOBF(String text) {
		return text.toLowerCase().replace("a", "**B").replace("b", "*%%CLIENTDATA").replace("c", "MDFG==/´´´´´´´´´").replace("d", "TIREDOBJ").replace("e", "&)=(&JNHMN").replace("f", "!NNN?WE?SSSSS");
	}

	@SneakyThrows
	public void getClientData() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/TIREDData.clientdata")).exists())
			(new File(mainPath + "/TIREDData.clientdata")).createNewFile();


		FileWriter writer = new FileWriter(mainPath + "/TIREDData.clientdata");
		writer.write(getTextOBF("albindo"));
		writer.flush();
		writer.close();
	}

	@SneakyThrows
	public void saveTime() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/time")).exists())
			(new File(mainPath + "/time")).createNewFile();

		FileWriter writer = new FileWriter(mainPath + "/time");

		writer.write(TimeStorage.currentTime + "");

		writer.flush();
		writer.close();
	}


	@SneakyThrows
	public void loadAlt() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/alts.tired")).exists())
			(new File(mainPath + "/alts.tired")).createNewFile();

		BufferedReader reader = new BufferedReader(new FileReader((mainPath + "/alts.tired")));
		String line;

		while ((line = reader.readLine()) != null) {
			String[] args = line.split(":");
			AltManagerRegistry.registry.add(new Account(args[2], args[3], args[0] + ":" + args[1]));

		}
		reader.close();

	}

	@SneakyThrows
	public void saveAlt() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/alts.tired")).exists())
			(new File(mainPath + "/alts.tired")).createNewFile();

		FileWriter writer = new FileWriter(mainPath + "/alts.tired");
		for (Account account : AltManagerRegistry.getRegistry()) {
			if (!account.isTheAltening()) {
				try {
					writer.write(account.getHead() + ":" + account.getUserName() + ":" + account.getPassword() + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		writer.flush();
		writer.close();
	}

	@SneakyThrows
	public void saveModule() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/mods.tired")).exists())
			(new File(mainPath + "/mods.tired")).createNewFile();

		FileWriter writer = new FileWriter(mainPath + "/mods.tired");

		for (Module module : Tired.INSTANCE.moduleManager.getModuleList()) {
			writer.write(module.getName() + ":" + module.state + "\n");
		}

		writer.flush();
		writer.close();

	}

	@SneakyThrows
	public void loadModule() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/mods.tired")).exists())
			(new File(mainPath + "/mods.tired")).createNewFile();

		BufferedReader reader = new BufferedReader(new FileReader((mainPath + "/mods.tired")));
		String line;

		while ((line = reader.readLine()) != null) {
			String[] args = line.split(":");

			Module module = Tired.INSTANCE.moduleManager.moduleBy(args[0]);
			if (module == null) return;
			if (args[1].equalsIgnoreCase("true")) {
				module.enableMod();
			}
			if (args[1].equalsIgnoreCase("false")) {
				module.disableModule();
			}
		}
		reader.close();
	}

	@SneakyThrows
	public void loadColors() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/colors")).exists())
			(new File(mainPath + "/colors")).createNewFile();

		BufferedReader reader = new BufferedReader(new FileReader((mainPath + "/colors")));
		String line;

		while ((line = reader.readLine()) != null) {

			Module module = Tired.INSTANCE.moduleManager.moduleBy(line.split(":")[0]);

			if (module == null) {
				return;
			}

			Setting s = Tired.INSTANCE.settingsManager.settingBy(line.split(":")[1], module);

			int color = Integer.parseInt(line.split(":")[2]);
			Color c = valueOf(color);

			if (s instanceof ColorPickerSetting) {
				((ColorPickerSetting) s).setColorPickerColor(c);

			}
		}
		reader.close();
	}

	public static Color valueOf(int color) {
		float r = ((color >> 16) & 0xff) / 255.0f;
		float g = ((color >> 8) & 0xff) / 255.0f;
		float b = ((color) & 0xff) / 255.0f;
		float a = ((color >> 24) & 0xff) / 255.0f;
		return new Color(r, g, b, a);
	}

	public void loadSettings() {
		try {
			final File file = new File(mainPath + "/" + "settings.tired");
			if (!file.exists()) {
				final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
				printWriter.println();
				printWriter.close();
			} else if (file.exists()) {
				final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				for (String readString; (readString = bufferedReader.readLine()) != null; ) {
					String[] split = readString.split(":");
					if (split.length <= 1) continue;
					Setting s = Tired.INSTANCE.settingsManager.settingBy(split[1], split[0]);
					if (s == null) continue;

					if (s instanceof BooleanSetting) {
						((BooleanSetting) s).setValue(Boolean.parseBoolean(split[2]));
					} else if (s instanceof NumberSetting) {
						((NumberSetting) s).setValue(Double.parseDouble(split[2]));
					} else if (s instanceof TextBoxSetting) {
						final String toString = String.valueOf(split[2]);
						((TextBoxSetting) s).setValue(toString);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveSettings() {

		File file = new File(mainPath + "/" + "settings.tired");

		try {

			PrintWriter printWriter = new PrintWriter(new FileWriter(file));

			for (Setting s : Tired.INSTANCE.settingsManager.getSettings()) {
				if (s == null) continue;

				String endString = s.getModule().getName() + ":";
				if (s instanceof BooleanSetting) {
					endString += s.getName() + ":" + ((BooleanSetting) s).getValue();
				} else if (s instanceof NumberSetting) {
					endString += s.getName() + ":" + ((NumberSetting) s).getValue();
				} else if (s instanceof TextBoxSetting) {
					endString += s.getName() + ":" + s.getValue();
				}

				printWriter.println(endString);
			}

			printWriter.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	@SneakyThrows
	public void saveKeybinds() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/Keybinds")).exists())
			(new File(mainPath + "/Keybinds")).createNewFile();

		FileWriter writer = new FileWriter(mainPath + "/Keybinds");
		for (Module module : Tired.INSTANCE.moduleManager.getModuleList()) {
			writer.write(module.getName() + "->" + module.getKey() + "\n");
		}
		writer.flush();
		writer.close();
	}


	@SneakyThrows
	public void loadPerformanceMode() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/performanceMode")).exists())
			(new File(mainPath + "/performanceMode")).createNewFile();

		BufferedReader reader = new BufferedReader(new FileReader((mainPath + "/performanceMode")));
		String line;

		while ((line = reader.readLine()) != null) {

			if (line.equalsIgnoreCase("NormalPerformance")) {
				PerformanceGui.usingType = UsingType.NORMAL_PERFORMANCE;
			}
			if (line.equalsIgnoreCase("HighPerformance")) {
				PerformanceGui.usingType = UsingType.HIGH_PERFORMANCE;
			}

		}
		reader.close();
	}

	@SneakyThrows
	public void loadKeybinds() {
		if (!(new File(mainPath)).exists())
			(new File(mainPath)).mkdir();

		if (!(new File(mainPath + "/Keybinds")).exists())
			(new File(mainPath + "/Keybinds")).createNewFile();

		BufferedReader reader = new BufferedReader(new FileReader((mainPath + "/Keybinds")));
		String line;

		while ((line = reader.readLine()) != null) {
			String[] args = line.split("->");

			Module module = Tired.INSTANCE.moduleManager.moduleBy(args[0]);
			if (module == null) return;
			module.setKey(Integer.parseInt(args[1]));
		}
		reader.close();
	}

}
