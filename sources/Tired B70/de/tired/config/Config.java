package de.tired.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.Setting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.module.Module;
import de.tired.tired.Tired;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Config {

	private final String name;

	public String version;

	public String creationTime;

	public Config(String name) {
		this.name = name;
	}

	public JsonObject serialize() {
		JsonObject jsonObject = new JsonObject();
		Tired.INSTANCE.moduleManager.getModuleList().forEach(module -> {
			if (!module.getName().equalsIgnoreCase("configs")) {
				JsonObject moduleObject = new JsonObject();
				moduleObject.addProperty("State", module.isState());
				JsonObject settingsObject = new JsonObject();

				Tired.INSTANCE.settingsManager.getSettingsByMod(module).forEach(setting -> {
					if (setting instanceof BooleanSetting) {
						settingsObject.addProperty(setting.getName(), ((BooleanSetting) setting).getValue());
					}
					if (setting instanceof ModeSetting) {
						settingsObject.addProperty(setting.getName(), ((ModeSetting) setting).getValue());
					}
					if (setting instanceof NumberSetting) {
						settingsObject.addProperty(setting.getName(), ((NumberSetting) setting).getValue());
					}
					if (setting instanceof ColorPickerSetting) {
						System.out.println(setting.getName() + " " + setting.getValue());
						settingsObject.addProperty(setting.getName(), "" + ((ColorPickerSetting) setting).getColorPickerColor());
					}

				});
				moduleObject.add("Settings", settingsObject);
				Calendar currentTimeNow = Calendar.getInstance();
				settingsObject.addProperty("Version: ", "b" + Tired.INSTANCE.VERSION);
				settingsObject.addProperty("Time: ", currentTimeNow.getTime() + "");
				jsonObject.add(module.getName(), moduleObject);
			}
		});
		return jsonObject;
	}

	public boolean deserialize(JsonElement jsonElement) {
		try {
			if (jsonElement instanceof JsonNull)
				return false;

			final List<Module> antiStackOverflow = Tired.INSTANCE.moduleManager.getModuleList();
			for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
				for (final Module module : antiStackOverflow) {
					if (entry.getKey().equalsIgnoreCase(module.getName())) {
						final JsonObject jsonModule = (JsonObject) entry.getValue();
						if (!module.getName().equalsIgnoreCase("configs")) {
							if (module.isState() != jsonModule.get("State").getAsBoolean()) {
								module.executeMod();
							}
							JsonObject settings = jsonModule.get("Settings").getAsJsonObject();
							for (Map.Entry<String, JsonElement> setting : settings.entrySet()) {
								if (Tired.INSTANCE.settingsManager.settingBy(setting.getKey(), module.getName()) != null) {
									Setting set = Tired.INSTANCE.settingsManager.settingBy(setting.getKey(), module.getName());

									if (set instanceof BooleanSetting) {
										((BooleanSetting) set).setValue(setting.getValue().getAsBoolean());
									}

									if (set instanceof ModeSetting) {
										((ModeSetting) set).setValue(setting.getValue().getAsString());
										if (Arrays.asList(((ModeSetting) set).getOptions()).contains(setting.getValue().getAsString())) {
											((ModeSetting) set).setModeIndex(Arrays.asList(((ModeSetting) set).getOptions()).indexOf(setting.getValue().getAsString()));
										}
									}
									if (set instanceof NumberSetting) {
										((NumberSetting) set).setValue(setting.getValue().getAsDouble());
									}

								} else {
									if (setting.getValue().getAsString().startsWith("b")) {
										version = setting.getValue().getAsString();
									} else {
										creationTime = setting.getValue().getAsString();
									}
								}
							}

						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean loadOnlyNoSet(JsonElement jsonElement) {
		try {
			if (jsonElement instanceof JsonNull)
				return false;

			final List<Module> antiStackOverflow = Tired.INSTANCE.moduleManager.getModuleList();
			for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
				for (final Module module : antiStackOverflow) {
					if (entry.getKey().equalsIgnoreCase(module.getName())) {
						final JsonObject jsonModule = (JsonObject) entry.getValue();
						if (!module.getName().equalsIgnoreCase("configs")) {

							JsonObject settings = jsonModule.get("Settings").getAsJsonObject();
							for (Map.Entry<String, JsonElement> setting : settings.entrySet()) {
								if (Tired.INSTANCE.settingsManager.settingBy(setting.getKey(), module.getName()) != null) {
									Setting set = Tired.INSTANCE.settingsManager.settingBy(setting.getKey(), module.getName());

									if (set instanceof ModeSetting) {
										setting.getValue().getAsString();
									}

								} else {
									if (setting.getValue().getAsString().startsWith("b")) {
										version = setting.getValue().getAsString();
									} else {
										creationTime = setting.getValue().getAsString();
									}
								}
							}

						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String name() {
		return name;
	}
}