package in.momin5.cookieclient.api.setting;

import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingManager {
    public static ArrayList<Setting> settings;

    public SettingManager() {
        this.settings = new ArrayList<Setting>();
    }

    public void rSetting(Setting in) {
        this.settings.add(in);
    }

    public static ArrayList<Setting> getSettings() {
        return SettingManager.settings;
    }

    public static ArrayList<Setting> getSettingsByMod(Module mod) {
        ArrayList<Setting> out = new ArrayList<Setting>();
        for (Setting s : getSettings()) {
            if (s.parent.equals(mod)) {
                out.add(s);
            }
        }
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }

    public Setting getSettingByName(Module mod, String name) {
        for (Module m : ModuleManager.modules) {
            for (Setting set : m.settings) {
                if (set.name.equalsIgnoreCase(name) && set.parent == mod) {
                    return set;
                }
            }
        }
        System.err.println("Couldnt find ur setting lol");
        return null;
    }

    public static List<Setting> getSettingsForModule(Module module) {
        for (Module m : ModuleManager.modules) {
            for (Setting set : m.settings)
             return settings.stream().filter(setting -> set.parent.equals(module)).collect(Collectors.toList());

        }return null;
    }
}
