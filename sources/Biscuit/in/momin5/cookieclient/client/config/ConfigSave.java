package in.momin5.cookieclient.client.config;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.setting.Setting;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;

public class ConfigSave {
    private File dir;
    private File dataFile;
    private Minecraft mc = Minecraft.getMinecraft();

    public ConfigSave() {
        dir = new File(mc.gameDir, CookieClient.MOD_NAME);
        if(!dir.exists()) {
            dir.mkdir();
        }
        dataFile = new File(dir, "Config.txt");
        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {e.printStackTrace();}
        }
        this.load();
    }

    public void save() {
        ArrayList<String> toSave = new ArrayList<String>();


        for(Module mod : ModuleManager.modules) {
                toSave.add("MOD:" + mod.getName() + ":" + mod.isEnabled() + ":" + mod.getKey());
        }

        // settings
        for(Module mod : ModuleManager.modules) {
            for(Setting setting : mod.settings) {

                if(setting instanceof SettingBoolean) {
                    SettingBoolean bool = (SettingBoolean) setting;
                    toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + bool.isEnabled());
                }

                if(setting instanceof SettingNumber) {
                    SettingNumber numb = (SettingNumber) setting;
                    toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + numb.getValue());
                }

                if(setting instanceof SettingMode) {
                    SettingMode mode = (SettingMode) setting;
                    toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + mode.getMode());
                }

                if(setting instanceof SettingColor) {
                    SettingColor color = (SettingColor) setting;
                    toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + color.toInteger());
                }
            }
        }

        try {
            PrintWriter pw = new PrintWriter(this.dataFile);
            for(String str : toSave) {
                pw.println(str);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        ArrayList<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
            String line = reader.readLine();
            while(line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        for(String s : lines) {
            String[] args = s.split(":");
            if(s.toLowerCase().startsWith("mod:")) {
                Module m = CookieClient.moduleManager.getModuleInnit(args[1]);
                if(m != null) {
                    if(m.getName().equals("ClickGUI") && m.getName().equals("hudEditor"))
                        m.setEnabled(!Boolean.parseBoolean(args[2]));

                    if(!m.getName().equals("ClickGUI") && !m.getName().equals("hudEditor"))
                        m.setEnabled(Boolean.parseBoolean(args[2]));
                    m.setBind(Integer.parseInt(args[3]));
                }
            }else if(s.toLowerCase().startsWith("set:")) {
                Module m = CookieClient.moduleManager.getModuleInnit(args[1]);
                if(m != null) {
                    Setting setting = CookieClient.settingManager.getSettingByName(m,args[2]);
                    if(setting != null) {
                        if(setting instanceof SettingBoolean) {
                            ((SettingBoolean)setting).setEnabled(Boolean.parseBoolean(args[3]));
                        }
                        if(setting instanceof SettingNumber) {
                            ((SettingNumber)setting).setValue(Double.parseDouble(args[3]));
                        }
                        if(setting instanceof SettingMode) {
                            ((SettingMode)setting).setMode(args[3]);
                        }
                        if(setting instanceof SettingColor) {
                            ((SettingColor)setting).fromInteger(Integer.parseInt(args[3]));
                        }
                    }
                }
            }
            }
        }
    }
