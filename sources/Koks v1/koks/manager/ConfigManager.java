package koks.manager;

import koks.Koks;
import koks.files.FileManager;
import koks.modules.Module;
import koks.modules.impl.utilities.ClickGUI;
import koks.modules.impl.utilities.HUD;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 21:44
 */
public class ConfigManager {

    public Minecraft mc = Minecraft.getMinecraft();
    public File DIR = new File(mc.mcDataDir + "/" + Koks.getKoks().CLIENT_NAME + "/Configs");

    public String currentConfig = "";

    public boolean configExist(String name) {
        File file = new File(DIR, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
        return file.exists();
    }

    public ArrayList<File> getConfigs() {
        ArrayList<File> files = new ArrayList<>();
        for (File file : DIR.listFiles()) {
            if (!files.contains(file)) {
                files.add(file);
            }
        }
        return files;
    }

        public void loadConfig (String name){
            currentConfig = name;
            for (Module module : Koks.getKoks().moduleManager.getModules()) {
                if (module.getModuleCategory() != Module.Category.VISUALS && module != Koks.getKoks().moduleManager.getModule(HUD.class)) {
                    module.setToggled(false);
                    module.setBypassed(false);
                }
            }

            File file = new File(DIR, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] args = line.split(":");
                    Module module = Koks.getKoks().moduleManager.getModule(args[1]);
                    if(module == null)continue;
                    if (args[0].equalsIgnoreCase("Module")) {
                        module.setToggled(Boolean.parseBoolean(args[2]));
                        module.setBypassed(Boolean.parseBoolean(args[3]));
                    } else if (args[0].equalsIgnoreCase("Setting")) {
                        Value value = Koks.getKoks().valueManager.getValue(module, args[2]);
                        if (value.shouldSave()) {

                            if (value instanceof BooleanValue) {
                                ((BooleanValue<Boolean>) value).setToggled(Boolean.parseBoolean(args[3]));
                            } else if (value instanceof ModeValue) {
                                if (((ModeValue) value).getObjects() == null) {
                                    ((ModeValue) value).setSelectedMode(args[3]);
                                } else {
                                    for (BooleanValue booleanValue : ((ModeValue) value).getObjects()) {
                                        if (booleanValue.getModule() == module) {
                                            if (booleanValue.getName().equalsIgnoreCase(args[3])) {
                                                booleanValue.setToggled(Boolean.parseBoolean(args[4]));
                                            }
                                        }
                                    }
                                }
                            } else if (value instanceof NumberValue) {
                                if (((NumberValue) value).getMinDefaultValue() != null) {
                                    if (((NumberValue) value).getDefaultValue() instanceof Float) {
                                        ((NumberValue) value).setMinDefaultValue(Float.parseFloat(args[3]));
                                        ((NumberValue) value).setDefaultValue(Float.parseFloat(args[4]));
                                    } else if (((NumberValue) value).getDefaultValue() instanceof Long) {
                                        ((NumberValue) value).setMinDefaultValue(Long.parseLong(args[3]));
                                        ((NumberValue) value).setDefaultValue(Long.parseLong(args[4]));
                                    } else if (((NumberValue) value).getDefaultValue() instanceof Integer) {
                                        ((NumberValue) value).setMinDefaultValue(Integer.parseInt(args[3]));
                                        ((NumberValue) value).setDefaultValue(Integer.parseInt(args[4]));
                                    } else if (((NumberValue) value).getDefaultValue() instanceof Double) {
                                        ((NumberValue) value).setMinDefaultValue(Double.parseDouble(args[3]));
                                        ((NumberValue) value).setDefaultValue(Double.parseDouble(args[4]));
                                    }

                                } else {
                                    if (((NumberValue) value).getDefaultValue() instanceof Float) {
                                        ((NumberValue) value).setDefaultValue(Float.parseFloat(args[3]));
                                    } else if (((NumberValue) value).getDefaultValue() instanceof Long) {
                                        ((NumberValue) value).setDefaultValue(Long.parseLong(args[3]));
                                    } else if (((NumberValue) value).getDefaultValue() instanceof Integer) {
                                        ((NumberValue) value).setDefaultValue(Integer.parseInt(args[3]));
                                    } else if (((NumberValue) value).getDefaultValue() instanceof Double) {
                                        ((NumberValue) value).setDefaultValue(Double.parseDouble(args[3]));
                                    }
                                }
                            }
                        }
                    }
                }
                bufferedReader.close();
            } catch (Exception ignored) {

            }

        }

        public void deleteAllConfigs () {
            for (File file : DIR.listFiles()) {
                file.delete();
            }
        }

        public void deleteConfig (String name){
            File file = new File(DIR, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
            file.delete();
        }

        public void createConfig (String name){
            try {

                if (!DIR.exists()) DIR.mkdir();

                File file = new File(DIR, name + "." + Koks.getKoks().CLIENT_NAME.toLowerCase());
                FileWriter fileWriter = new FileWriter(file);

                for (Module module : Koks.getKoks().moduleManager.getModules()) {
                    boolean bypassed = (module.isBypassed() || module.isToggled()) && module.getModuleCategory() != Module.Category.VISUALS && module != Koks.getKoks().moduleManager.getModule(HUD.class) && module != Koks.getKoks().moduleManager.getModule(ClickGUI.class);

                    if (bypassed) {
                        fileWriter.write("Module:" + module.getModuleName() + ":" + module.isToggled() + ":" + bypassed + "\n");
                    }
                }

                for (Value value : Koks.getKoks().valueManager.getValues()) {
                    Module module = value.getModule();
                    boolean bypassed = (module.isBypassed() || module.isToggled()) && module.getModuleCategory() != Module.Category.VISUALS && module != Koks.getKoks().moduleManager.getModule(HUD.class) && module != Koks.getKoks().moduleManager.getModule(ClickGUI.class);
                    if (bypassed) {
                        if (value instanceof BooleanValue) {
                            fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + ((BooleanValue) value).isToggled() + "\n");
                        } else if (value instanceof ModeValue) {
                            if(((ModeValue) value).getObjects() == null) {
                                fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + ((ModeValue) value).getSelectedMode() + "\n");
                            }else{
                                for(BooleanValue booleanValues : ((ModeValue) value).getObjects()) {
                                    if (booleanValues.getModule() == module) {
                                        fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + booleanValues.getName() + ":" + booleanValues.isToggled() + "\n");
                                    }
                                }
                            }
                        } else if (value instanceof NumberValue) {
                            if (((NumberValue) value).getMinDefaultValue() != null) {
                                fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + ((NumberValue) value).getMinDefaultValue() + ":" + ((NumberValue) value).getDefaultValue() + "\n");
                            } else {
                                fileWriter.write("Setting:" + module.getModuleName() + ":" + value.getName() + ":" + ((NumberValue) value).getDefaultValue() + "\n");
                            }
                        }
                    }

                }
                fileWriter.close();

            } catch (Exception ignored) {

            }
        }
    }
