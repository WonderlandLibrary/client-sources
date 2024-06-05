package me.satisfactory.base.hero.settings;

import java.io.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.utils.file.*;
import java.util.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.*;

public class SettingsManager
{
    private static final File SETTING_DIR;
    public static ArrayList<Setting> settings;
    
    public SettingsManager() {
        SettingsManager.settings = new ArrayList<Setting>();
    }
    
    public static void load() {
        final List<String> fileContent = FileUtils.read(SettingsManager.SETTING_DIR);
        for (final String line : fileContent) {
            try {
                final String[] split = line.split(":");
                final String name = split[0];
                final String value = split[1];
                final String whatitis = split[2];
                for (final Setting m : SettingsManager.settings) {
                    if (!name.equalsIgnoreCase(m.getName())) {
                        continue;
                    }
                    if (whatitis.equalsIgnoreCase("Double")) {
                        m.setValDouble(Double.parseDouble(value));
                    }
                    if (whatitis.equalsIgnoreCase("String")) {
                        Mode mode = null;
                        try {
                            for (final Mode m2 : m.getParentMod().getModes()) {
                                if (!m2.getName().equalsIgnoreCase(value)) {
                                    continue;
                                }
                                mode = m2;
                            }
                            if (mode == null) {
                                continue;
                            }
                            m.getParentMod().setMode(mode);
                        }
                        catch (Exception e10) {
                            e10.printStackTrace();
                        }
                    }
                    if (!whatitis.equalsIgnoreCase("Boolean")) {
                        continue;
                    }
                    m.setValBoolean(Boolean.valueOf(value));
                }
            }
            catch (Exception split2) {
                System.err.println(split2);
            }
        }
    }
    
    public static void save() {
        final ArrayList<String> fileContent = new ArrayList<String>();
        for (final Setting setting : SettingsManager.settings) {
            if (setting != null) {
                final Double doub = setting.doubleValue();
                final Boolean bool = setting.booleanValue();
                final String stri = setting.getValStringForSaving();
                if (doub != null && setting.getMax() != 0.0) {
                    fileContent.add(setting.getName() + ":" + setting.doubleValue() + ":Double");
                }
                if (stri != null) {
                    try {
                        fileContent.add(setting.getName() + ":" + setting.getParentMod().getMode().getName() + ":String");
                    }
                    catch (Exception ex) {}
                }
                if (bool == null || setting.getMax() != 0.0 || setting.getValStringForSaving() != null) {
                    continue;
                }
                fileContent.add(setting.getName() + ":" + setting.booleanValue() + ":Boolean");
            }
        }
        FileUtils.write(SettingsManager.SETTING_DIR, fileContent, true);
    }
    
    public void rSetting(final Setting in) {
        SettingsManager.settings.add(in);
    }
    
    public ArrayList<Setting> getSettings() {
        return SettingsManager.settings;
    }
    
    public ArrayList<Setting> getSettingsByMod(final Module mod) {
        try {
            final ArrayList<Setting> out = new ArrayList<Setting>();
            for (final Setting s : this.getSettings()) {
                if (s.getParentMod().equals(mod)) {
                    out.add(s);
                }
            }
            if (out.isEmpty()) {
                return null;
            }
            return out;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public Setting getSettingByModule(final Module mod, final String setting) {
        final ArrayList<Setting> settingsList = this.getSettingsByMod(mod);
        for (final Setting settings : settingsList) {
            if (settings.getName().equalsIgnoreCase(setting)) {
                return settings;
            }
        }
        return null;
    }
    
    public Setting getSettingByName(final String name) {
        try {
            for (final Setting set : this.getSettings()) {
                if (set.getName().equalsIgnoreCase(name)) {
                    return set;
                }
            }
            System.err.println("[" + Base.INSTANCE.getCLIENT_NAME() + "] Error Setting NOT found: '" + name + "'!");
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static {
        SETTING_DIR = FileUtils.getConfigFile("Settings");
    }
}
