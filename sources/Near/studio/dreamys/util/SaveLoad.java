package studio.dreamys.util;

import net.minecraft.client.Minecraft;
import studio.dreamys.module.Module;
import studio.dreamys.near;
import studio.dreamys.setting.Setting;

import java.io.*;
import java.util.ArrayList;

public class SaveLoad {
    final File dir = new File(Minecraft.getMinecraft().mcDataDir, "near");
    final File file = new File(dir, "config.txt");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public SaveLoad() {
        try {
            if (!dir.exists()) dir.mkdir();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        load();
    }

    public void save() {
        try {
            ArrayList<String> toSave = new ArrayList<>();
            for (Module mod : near.moduleManager.modules) {
                toSave.add("MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey());
            }
            for (Setting set : near.settingsManager.getSettings()) {
                if (set.isSlider()) {
                    toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
                }
                if (set.isCheck()) {
                    toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
                }
                if (set.isCombo()) {
                    toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
                }
            }
            PrintWriter pw = new PrintWriter(file);
            for (String str : toSave) {
                pw.println(str);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
            for (String s : lines) {
                String[] args = s.split(":");
                if (s.toLowerCase().startsWith("mod:")) {
                    Module m = near.moduleManager.getModule(args[1]);
                    if (m != null) {
                        m.setToggled(Boolean.parseBoolean(args[2]));
                        m.key(Integer.parseInt(args[3]));
                    }
                }
                if (s.toLowerCase().startsWith("set:")) {
                    Module m = near.moduleManager.getModule(args[2]);
                    if (m != null) {
                        Setting set = near.settingsManager.getSettingByName(m, args[1]);
                        if (set != null) {
                            if (set.isSlider()) {
                                set.setValDouble(Double.parseDouble(args[3]));
                            }
                            if (set.isCheck()) {
                                set.setValBoolean(Boolean.parseBoolean(args[3]));
                            }
                            if (set.isCombo()) {
                                set.setValString(args[3]);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
