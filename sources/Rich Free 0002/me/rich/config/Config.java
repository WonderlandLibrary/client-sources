package me.rich.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import clickgui.setting.Setting;
import clickgui.setting.SettingsManager;
import me.rich.Main;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class Config {
    public File dir;
    public File configs;
    public File dataFile;

    public Config() {
        this.dir = new File(Minecraft.getMinecraft().mcDataDir, "richclient");
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        this.dataFile = new File(this.dir, "config.txt");
        if (!this.dataFile.exists()) {
            try {
                this.dataFile.createNewFile();
            }
            catch (IOException var2) {
                var2.printStackTrace();
            }
        }
        this.load();
    }

    public void save() {
        ArrayList<String> toSave = new ArrayList<String>();
        for (Feature mod : Main.moduleManager.getModules()) {
            toSave.add("Feature:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey());
        }
        SettingsManager var10000 = Main.settingsManager;
        for (Setting set : SettingsManager.getSettings()) {
            if (set.isCheck()) {
                toSave.add("Setting:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
            }
            if (set.isCombo()) {
                toSave.add("Setting:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
            }
            if (!set.isSlider()) continue;
            toSave.add("Setting:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValFloat());
        }
        try {
            PrintWriter pw = new PrintWriter(this.dataFile);
            for (String str : toSave) {
                pw.println(str);
            }
            pw.close();
        }
        catch (FileNotFoundException var5) {
            var5.printStackTrace();
        }
    }

    public void load() {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
            String s = reader.readLine();
            while (s != null) {
                lines.add(s);
                s = reader.readLine();
            }
            reader.close();
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
        for (String s : lines) {
            Setting set;
            Feature m;
            String[] args = s.split(":");
            if (s.toLowerCase().startsWith("feature:")) {
                m = Main.instance.moduleManager.getModuleByName(args[1]);
                if (m == null) continue;
                m.setEnabled(Boolean.parseBoolean(args[2]));
                m.setKey(Integer.parseInt(args[3]));
                continue;
            }
            if (!s.toLowerCase().startsWith("setting:") || (m = Main.instance.moduleManager.getModuleByName(args[2])) == null || (set = Main.settingsManager.getSettingByName(args[1])) == null) continue;
            if (set.isCheck()) {
                set.setValBoolean(Boolean.parseBoolean(args[3]));
            }
            if (set.isCombo()) {
                set.setValString(args[3]);
            }
            if (!set.isSlider()) continue;
            set.setValDouble(Double.parseDouble(args[3]));
            set.setValFloat(Float.parseFloat(args[3]));
        }
    }
}

