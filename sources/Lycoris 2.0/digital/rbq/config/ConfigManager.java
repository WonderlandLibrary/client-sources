/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.apache.commons.io.FilenameUtils
 */
package digital.rbq.config;

import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import digital.rbq.core.Autumn;
import digital.rbq.file.FileManager;
import digital.rbq.module.Module;
import digital.rbq.module.option.Option;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.utils.Logger;

public final class ConfigManager {
	private final List<Config> configs = new ArrayList<Config>();
	private final File directory = new File(FileManager.HOME_DIRECTORY, "Configs");

	public ConfigManager() {
		this.refresh();
	}

	public List<Config> getConfigs() {
		return this.configs;
	}

	public final void refresh() {
		if (this.directory.listFiles() != null) {
			for (File file : this.directory.listFiles()) {
				if (this.get(file.getName()) != null)
					continue;
				this.add(FilenameUtils.removeExtension((String) file.getName()));
			}
		}
	}

	public final void add(String name) {
		this.configs.add(new Config(name, new File(this.directory, name + ".json")));
	}

	public final boolean create(Config config) {
		if (!config.getFile().exists()) {
			try {
				if (config.getFile().createNewFile()) {
					this.add(config.getName());
					config.save();
					return true;
				}
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	public final boolean load(String name) {
		for (Config config : this.configs) {
			if (!config.getName().equalsIgnoreCase(name))
				continue;
			config.load();
			return true;
		}
		Logger.log("Failed to load config " + name);
		return false;
	}

	public final boolean save(String name) {
		if (this.configs.contains(this.get(name))) {
			for (Config config : this.configs) {
				if (!config.getName().equalsIgnoreCase(name))
					continue;
				config.save();
				return true;
			}
		} else {
			return this.create(new Config(name, new File(this.directory, name + ".json")));
		}
		Logger.log("Failed to save config " + name);
		return false;
	}

	public final boolean delete(String name) {
		if (this.configs.contains(this.get(name))) {
			for (Config config : this.configs) {
				if (!config.getName().equalsIgnoreCase(name) || !config.getFile().exists())
					continue;
				this.configs.remove(config);
				return config.getFile().delete();
			}
		}
		Logger.log("Failed to delete config " + name);
		return false;
	}

	public final Config get(String name) {
		for (Config config : this.configs) {
			if (!config.getName().equalsIgnoreCase(name))
				continue;
			return config;
		}
		return null;
	}

	public static class Config {
		private final String name;
		private final File file;

		public Config(String name, File file) {
			this.name = name;
			this.file = file;
		}

		public String getName() {
			return this.name;
		}

		public File getFile() {
			return this.file;
		}

		public void save() {
			JsonObject js = new JsonObject();
			for (Module module : Autumn.MANAGER_REGISTRY.moduleManager.getModules()) {
				JsonObject jsf = new JsonObject();
				jsf.addProperty("Enabled", Boolean.valueOf(module.isEnabled()));
				jsf.addProperty("Hidden", Boolean.valueOf(module.isHidden()));
				if (module.getOptions() != null) {
					JsonObject optionsObject = new JsonObject();
					for (Option<?> option : module.getOptions()) {
						if (option instanceof DoubleOption) {
							optionsObject.addProperty(option.getLabel(), (Number) option.getValue());
							continue;
						}
						if (option instanceof BoolOption) {
							optionsObject.addProperty(option.getLabel(), ((BoolOption) option).getValue());
							continue;
						}
						if (!(option instanceof EnumOption))
							continue;
						optionsObject.addProperty(option.getLabel(), ((Enum) option.getValue()).name());
					}
					jsf.add("Options", (JsonElement) optionsObject);
				}
				js.add(module.getLabel(), (JsonElement) jsf);
			}
			Autumn.MANAGER_REGISTRY.fileManager.write(this.getFile(),
					new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement) js));
		}

		public void load() {
			File file = this.getFile();

			try {
				Reader reader = new FileReader(file.toPath().toFile());
				Throwable var3 = null;

				try {
					JsonObject object = (new JsonParser()).parse(reader).getAsJsonObject();
					UnmodifiableIterator var5 = Autumn.MANAGER_REGISTRY.moduleManager.getModules().iterator();

					while (var5.hasNext()) {
						Module module = (Module) var5.next();
						if (object.has(module.getLabel())) {
							JsonObject featureObject = object.get(module.getLabel()).getAsJsonObject();
							if (featureObject.has("Enabled")) {
								module.setEnabled(featureObject.get("Enabled").getAsBoolean());
							}

							if (featureObject.has("Hidden")) {
								module.setHidden(featureObject.get("Hidden").getAsBoolean());
							}

							if (featureObject.has("Options")) {
								featureObject.get("Options").getAsJsonObject().entrySet().forEach((entry) -> {
									Option option = module.getOptionByLabel((String) entry.getKey());
									if (option instanceof BoolOption) {
										option.setValue(((JsonElement) entry.getValue()).getAsBoolean());
									} else if (option instanceof EnumOption) {
										option.setValue(((EnumOption) option)
												.getValueOrNull(((JsonElement) entry.getValue()).getAsString()));
									} else if (option instanceof DoubleOption) {
										try {
											option.setValue(
													Double.parseDouble(((JsonElement) entry.getValue()).getAsString()));
										} catch (NumberFormatException var4) {
											Logger.log("Failed to load config!");
										}
									}

								});
							}
						}
					}
				} catch (Throwable var16) {
					var3 = var16;
					throw var16;
				} finally {
					if (reader != null) {
						if (var3 != null) {
							try {
								reader.close();
							} catch (Throwable var15) {
								var3.addSuppressed(var15);
							}
						} else {
							reader.close();
						}
					}

				}
			} catch (IOException var18) {
			}

		}

	}
}
