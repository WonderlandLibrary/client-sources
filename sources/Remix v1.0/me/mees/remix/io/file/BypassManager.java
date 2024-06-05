package me.mees.remix.io.file;

import me.satisfactory.base.utils.file.*;
import java.io.*;
import me.satisfactory.base.*;
import me.satisfactory.base.hero.settings.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import java.util.*;

public class BypassManager
{
    public static void create() {
        final File CoolDir = new File(FileUtils.getConfigDir(), "Configs");
        if (!CoolDir.exists()) {
            CoolDir.mkdir();
        }
    }
    
    public static void load(final String confname) {
        final File CoolDir = new File(FileUtils.getConfigDir(), "Configs");
        if (!CoolDir.exists()) {
            CoolDir.mkdir();
        }
        final List<String> fileContent = FileUtils.read(FileUtils.getConfigFileConf(confname));
        for (final String line : fileContent) {
            try {
                final String[] split = line.split(":");
                final String name = split[0];
                final String value = split[1];
                final String whatitis = split[2];
                Base.INSTANCE.getSettingManager();
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
    
    public static File getConfigFile(final String name) {
        final File file = new File(FileUtils.getConfigDir(), String.format("", name));
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
