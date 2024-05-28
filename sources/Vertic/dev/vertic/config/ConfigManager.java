package dev.vertic.config;

import dev.vertic.Client;
import dev.vertic.Utils;
import dev.vertic.module.Module;
import dev.vertic.setting.Setting;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.setting.impl.ModeSetting;
import dev.vertic.setting.impl.NumberSetting;
import dev.vertic.ui.click.dropdown.CategoryFrame;
import dev.vertic.ui.click.dropdown.DropUI;

import java.io.*;
import java.util.ArrayList;

public class ConfigManager implements Utils {

    public final File clientDir;
    public final File configDir;
    public final ArrayList<File> configs = new ArrayList<>();

    public ConfigManager() {
        clientDir = new File(mc.mcDataDir, "vertic");
        configDir = new File(clientDir, "configs");

        if (!clientDir.exists())
            clientDir.mkdir();
        if (!configDir.exists())
            configDir.mkdir();
    }

    public void loadClient() {
        try {
            File config = new File(clientDir, "client.txt");
            if (config.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(config));
                ArrayList<String> read = new ArrayList<>();
                String thing;
                while ((thing = reader.readLine()) != null) {
                    read.add(thing);
                }
                reader.close();

                for (String s : read) {
                    String stuff[] = s.split(":");
                    if (stuff.length >= 3 && stuff[0].equalsIgnoreCase("clickgui")) {
                        String category = stuff[1];
                        DropUI dropdownGUI = Client.instance.getDropDownUI();

                        for (CategoryFrame cf : dropdownGUI.frames) {
                            if (cf.category.name().equalsIgnoreCase(category)) {
                                cf.frameX = Float.parseFloat(stuff[2]);
                                cf.frameY = Float.parseFloat(stuff[3]);
                                cf.expanded = Boolean.parseBoolean(stuff[4]);
                            }
                        }

                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveClient() {
        try {
            File config = new File(clientDir, "client.txt");
            if (!config.exists()) {
                config.createNewFile();
            }
            PrintWriter writer = new PrintWriter(config);
            ArrayList<String> write = new ArrayList<>();
            DropUI dropdownGUI = Client.instance.getDropDownUI();

            for (CategoryFrame cf : dropdownGUI.frames) {
                write.add("clickgui:" + cf.category + ":" + cf.frameX + ":" + cf.frameY + ":" + cf.expanded);
            }

            for (String s : write) {
                writer.println(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveConfig(final String name) {
        try {
            File config = new File(configDir, name + ".txt");
            if (!config.exists())
                config.createNewFile();
            PrintWriter writer = new PrintWriter(config);
            ArrayList<String> write = new ArrayList<>();

            for (Module m : Client.instance.getModuleManager().modules) {
                write.add("State:" + m.getName() + ":" + m.isEnabled());
                write.add("Keybind:" + m.getName() + ":" + m.getKey());
                for (Setting s : m.getSettings()) {
                    if (s instanceof BooleanSetting) {
                        BooleanSetting setting = (BooleanSetting) s;
                        write.add("Setting:" + m.getName() + ":" + setting.getName() + ":" + setting.isEnabled());
                    } else if (s instanceof NumberSetting) {
                        NumberSetting setting = (NumberSetting) s;
                        write.add("Setting:" + m.getName() + ":" + setting.getName() + ":" + setting.getValue());
                    } else if (s instanceof ModeSetting) {
                        ModeSetting setting = (ModeSetting) s;
                        write.add("Setting:" + m.getName() + ":" + setting.getName() + ":" + setting.getMode());
                    }
                }
            }

            for (String s : write) {
                writer.println(s);
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loadConfig(final String name) {
        try {
            File config = new File(configDir, name + ".txt");
            if (config.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(config));
                ArrayList<String> read = new ArrayList<>();
                String thing;
                while ((thing = reader.readLine()) != null) {
                    read.add(thing);
                }
                reader.close();

                for (String string : read) {
                    String[] strings = string.split(":");
                    String type = strings[0];
                    Module m = Client.instance.getModuleManager().getModuleNoSpace(strings[1]);

                    if (m != null) {
                        switch (type) {
                            case "State":
                                m.setState(Boolean.parseBoolean(strings[2]));
                                break;
                            case "Keybind":
                                m.setKey(Integer.parseInt(strings[2]));
                                break;
                            case "Setting":
                                Setting s = m.getSetting(strings[2]);
                                if (s != null) {
                                    if (s instanceof BooleanSetting) {
                                        BooleanSetting setting = (BooleanSetting) s;
                                        setting.setEnabled(Boolean.parseBoolean(strings[3]));
                                    } else if (s instanceof NumberSetting) {
                                        NumberSetting setting = (NumberSetting) s;
                                        setting.setValue(Double.parseDouble(strings[3]));
                                    } else if (s instanceof ModeSetting) {
                                        ModeSetting setting = (ModeSetting) s;
                                        setting.setMode(strings[3]);
                                    }
                                }
                                break;
                            default:
                        }
                    }
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> listConfigs() {
        final File[] allFilesInDir = configDir.listFiles();
        final ArrayList<String> configs = new ArrayList<>();
        for (final File f : allFilesInDir) {
            if (f.getName().endsWith(".txt")) {
                configs.add(f.getName().substring(0, f.getName().length() - 4));
            }
        }
        return configs;
    }

}
