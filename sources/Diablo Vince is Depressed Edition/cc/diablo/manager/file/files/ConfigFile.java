/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.manager.file.files;

import cc.diablo.Main;
import cc.diablo.manager.file.FileManager;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Module;
import cc.diablo.setting.Setting;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class ConfigFile
extends FileManager.CustomFile {
    public ConfigFile(String name, boolean Module2, boolean loadOnStart) {
        super(name, Module2, loadOnStart);
    }

    @Override
    public void loadFile() throws IOException {
        BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
        String line = null;
        while ((line = variable9.readLine()) != null) {
            if (line.startsWith("#")) {
                Main.customName = line.substring(1);
            }
            if (line.startsWith("/") || line.startsWith("#")) continue;
            String[] arguments = line.split(":");
            String module = arguments[0];
            String status = arguments[1];
            try {
                Module m = ModuleManager.getModuleByName(module);
                if (m != null) {
                    if (Objects.equals(status, "true")) {
                        m.setToggled(true);
                    } else if (Objects.equals(status, "false") && m.toggled) {
                        m.setToggled(false);
                    }
                }
                int val = 1;
                assert (m != null);
                for (Setting s : m.getSettingList()) {
                    ++val;
                    if (s instanceof ModeSetting) {
                        ((ModeSetting)s).setMode(arguments[val]);
                    }
                    if (s instanceof NumberSetting) {
                        ((NumberSetting)s).setValue(Double.parseDouble(arguments[val]));
                    }
                    if (!(s instanceof BooleanSetting)) continue;
                    ((BooleanSetting)s).setChecked(Boolean.parseBoolean(arguments[val]));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        variable9.close();
        System.out.println("Loaded " + this.getName() + "!");
    }

    @Override
    public void saveFile() throws IOException {
        PrintWriter variable9 = new PrintWriter(new FileWriter(this.getFile()));
        variable9.println("// Diablo " + Main.version + " config (" + Main.buildType + ")");
        variable9.println("#" + Main.customName);
        for (Module m : ModuleManager.modules) {
            if (m.getName().contains("name")) {
                // empty if block
            }
            StringBuilder string = new StringBuilder(m.getName() + ":" + m.isToggled());
            for (Setting s : m.getSettingList()) {
                if (s instanceof ModeSetting) {
                    string.append(":").append(((ModeSetting)s).getMode());
                }
                if (s instanceof NumberSetting) {
                    string.append(":").append(((NumberSetting)s).getVal());
                }
                if (!(s instanceof BooleanSetting)) continue;
                string.append(":").append(((BooleanSetting)s).isChecked());
            }
            variable9.println(string);
        }
        variable9.close();
    }
}

