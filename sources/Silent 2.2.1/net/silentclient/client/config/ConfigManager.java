package net.silentclient.client.config;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.gui.notification.NotificationManager;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModDraggable;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.mods.player.AutoTextMod.AutoTextCommand;
import net.silentclient.client.utils.FileUtils;
import net.silentclient.client.utils.MenuBlurUtils;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ConfigManager {

	public File configFile;
	private Set<String> configs;
	private boolean creatingDefaultConfigNeeded;

	public ConfigManager() {
		updateConfigs();

		String config = Client.getInstance().getGlobalSettings().getConfig();
		configFile = new File(Client.getInstance().configDir, config);


		if(!configFile.exists()) {
			creatingDefaultConfigNeeded = true;
		}

		this.load();
	}

	public void postInit() {
		if(creatingDefaultConfigNeeded) {
			configFile.delete();
			newConfig("Default.txt", false);
		}
	}

	public Set<String> getConfigFiles() {
		return this.configs;
	}

	public void updateConfigs() {
		Client.logger.info("Updating Config List");
		this.configs = Stream.of(Client.getInstance().configDir.listFiles())
				.filter(file -> !file.isDirectory())
				.map(File::getName)
				.collect(Collectors.toSet());
	}

	public void deleteConfig(String name) {
		try {
			new File(Client.getInstance().configDir, name).delete();
		} catch (Exception err) {
			err.printStackTrace();
		}
		updateConfigs();
	}

	public void loadConfig(String name) {
		Client.logger.info("Loading Config: " + name);
		Client.getInstance().getGlobalSettings().setConfig(name);
		configFile = new File(Client.getInstance().configDir, name);
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.load();
		this.save();
		for(Mod m : Client.getInstance().getModInstances().getMods()) {
			if(m.isEnabled()) {
				m.toggle();
				m.toggle();
			}
		}
		NotificationManager.clear();
		Client.getInstance().getGlobalSettings().save();
	}

	public String newConfig(String name, boolean clone) {
		Client.logger.info("Creating Config: " + name);
		File testConfig = new File(Client.getInstance().configDir, name);
		if(testConfig.exists()) {
			Client.logger.info("Creating Config Error: Config already exists.");
			return "Config already exists.";
		} else {
			if(clone) {
				try {
					testConfig.createNewFile();
				} catch (IOException e) {
					Client.logger.catching(e);
					return "Error: " + e.getMessage();
				}
			}
		}
		Client.getInstance().getGlobalSettings().setConfig(name);
		Client.getInstance().getGlobalSettings().save();
		configFile = testConfig;
		if(!clone) {
			Client.getInstance().getModInstances().getMods().forEach(mod -> mod.reset(true));
			try {
				FileUtils.exportResource("/assets/minecraft/silentclient/configs/Default.txt", String.format(Client.getInstance().configDir.toString() + "/%s", name));
			} catch (Exception e) {
				Client.logger.catching(e);
			}
		} else {
			this.save();
		}
		this.load();
		if(clone) {
			for(Mod m : Client.getInstance().getModInstances().getMods()) {
				if(m.isEnabled()) {
					m.toggle();
					m.toggle();
				}
			}
		}
		updateConfigs();
		return "success";
	}

	public void load() {
		try (BufferedReader reader = new BufferedReader(new FileReader(this.configFile))) {
			String s;
			Client.getInstance().getModInstances().getAutoText().commands.clear();
			while((s = reader.readLine()) != null) {
				String[] args = s.split(":");

				if (s.toLowerCase().startsWith("mod:")) {
					try {
						Mod m = Client.getInstance().getModInstances().getModByName(args[1]);
						if (m != null) {
							m.setEnabled(Boolean.parseBoolean(args[2]));
							m.setToggled(Boolean.parseBoolean(args[2]));
						}
					} catch (Exception err) {
						err.printStackTrace();
					}
				}
				
				if (s.toLowerCase().startsWith("pos:")) {
					try {
						Mod m = Client.getInstance().getModInstances().getModByName(args[1]);
						if (m != null && m instanceof ModDraggable) {
							ModDraggable md = (ModDraggable) m;
							md.setPos(ScreenPosition.fromRelativePosition(Double.parseDouble(args[2]), Double.parseDouble(args[3])));
						 	md.getPos().setRelative(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
						}
					} catch (Exception err) {
						err.printStackTrace();
					}
				}

				if (s.toLowerCase().startsWith("set:")) {
					Mod m = Client.getInstance().getModInstances().getModByName(args[2]);
					if (m != null) {
						Setting set = Client.getInstance().getSettingsManager().getSettingByName(m, args[1]);
						if (set != null) {
							if (set.isCheck()) {
								try {
									set.setValBoolean(Boolean.parseBoolean(args[3]));
									if(set.getName() == "Menu Background Blur") {
										if(Minecraft.getMinecraft().currentScreen != null) {
											if(!set.getValBoolean()) {
												MenuBlurUtils.unloadBlur(true);
											} else {
												MenuBlurUtils.loadBlur(true);
											}
										}
									}
								} catch (Exception err) {

								}
							}
							if(set.isKeybind()) {
								try {
									set.setKeybind(Integer.parseInt(args[3]));
								} catch (Exception err) {

								}
							}
							if(set.isCellGrid()) {
								try {
									set.setCells(stringToBooleanArray(args[3]));
								} catch (Exception ignored) {}
							}
							if (set.isCombo()) {
								try {
									if(set.getOptions().contains(args[3])) {
										set.setValString(args[3]);
									}
								} catch (Exception err) {

								}
							}
							if (set.isSlider()) {
								try {
									if(Double.parseDouble(args[3]) >= set.getMin() && Double.parseDouble(args[3]) <= set.getMax()) {
										set.setValDouble(Double.parseDouble(args[3]));
									}
								} catch (Exception err) {

								}
							}
							if(set.isInput()) {
								try {
									set.setValString(args[3]);
								} catch (Exception err) {

								}
							}
							if (set.isColor()) {
								try {
									set.setValColor(new Color(Integer.parseInt(args[3])));
								} catch (Exception err) {

								}
								try {
									set.setChroma(Boolean.parseBoolean(args[4]));
								} catch (Exception err) {
								}
								try {
									set.setOpacity(Integer.parseInt(args[5]));
								} catch (Exception err) {

								}
							}
						}
					}
				}
				if (s.toLowerCase().startsWith("atc:")) {
					try {
						Client.getInstance().getModInstances().getAutoText().addCommand(args[1], Integer.parseInt(args[2]));
					} catch (Exception err) {

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String booleanArrayToString(boolean[][] cells) {
		StringBuilder sb = new StringBuilder();
		for (boolean[] row : cells) {
			for (boolean cell : row) {
				sb.append(cell ? '1' : '0');
			}
			sb.append('/');
		}
		return sb.toString();
	}

	public static boolean[][] stringToBooleanArray(String input) {
		String[] rows = input.trim().split("/");
		int numRows = rows.length;
		int numCols = rows[0].length();
		boolean[][] result = new boolean[numRows][numCols];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				result[i][j] = rows[i].charAt(j) == '1';
			}
		}

		return result;
	}

	public void save() {
		Client.logger.info("Saving Config: " + this.configFile.getName());
		try(PrintWriter writer = new PrintWriter(this.configFile)) {
			for(Mod m : Client.getInstance().getModInstances().getMods()) {
				writer.println("MOD:" + m.getName() + ":" + m.isToggled());
				if(m instanceof ModDraggable) {
					ModDraggable md = (ModDraggable) m;
					writer.println("POS:" + m.getName() + ":" + md.getPos().getRelitiveX() + ":" + md.getPos().getRelitiveY());
				}
			}

			for(Setting set : Client.getInstance().getSettingsManager().getSettings()) {
				if (set.isCheck()) {
					writer.println("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
				}
				if (set.isCombo()) {
					writer.println("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
				}
				if (set.isSlider()) {
					writer.println("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
				}
				if (set.isColor()) {
					writer.println("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getClearColor().getRGB() +  ":" + set.isChroma() + ":" + set.getOpacity());
				}
				if(set.isInput()) {
					writer.println("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
				}
				if(set.isKeybind()) {
					writer.println("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getKeybind());
				}
				if(set.isCellGrid()) {
					writer.println("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + booleanArrayToString(set.getCells()));
				}
			}
			
			for(AutoTextCommand command : Client.getInstance().getModInstances().getAutoText().getCommands()) {
				writer.println("ATC:"+command.getCommand()+":"+command.getKey());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
