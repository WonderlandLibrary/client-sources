package de.Hero.settings;

import zamorozka.main.Zamorozka;
import zamorozka.module.Module;

import java.util.ArrayList;


/**
 * Made by HeroCode
 * it's free to use
 * but you have to credit me
 *
 * @author HeroCode
 */
public class SettingsManager {

    public static ArrayList<Setting> settings;

    public SettingsManager() {
        settings = new ArrayList<>();
    }

    public static ArrayList<Setting> getSettings() {
        return settings;
    }

    public boolean rSetting(Setting in) {
        return settings.add(in);
    }

    public ArrayList<Setting> getSettingsByMod(Module mod) {
        ArrayList<Setting> out = new ArrayList<>();
        for (Setting s : getSettings()) {
            if (s.getParentMod().equals(mod)) {
                out.add(s);
            }
        }
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }

    public Setting getSettingByName(String name) {
        for (Setting set : getSettings()) {
            if (set.getName().equalsIgnoreCase(name)) {
                return set;
            }
        }
        System.err.println("[" + Zamorozka.ClientName + "] Error Setting NOT found: '" + name + "'!");
        return null;
    }

}