/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.settings;

import de.Hero.settings.Setting;
import java.io.PrintStream;
import java.util.ArrayList;
import us.amerikan.amerikan;
import us.amerikan.modules.Module;

public class SettingsManager {
    private ArrayList<Setting> settings = new ArrayList();

    public void rSetting(Setting in2) {
        this.settings.add(in2);
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public ArrayList<Setting> getSettingsByMod(Module mod) {
        ArrayList<Setting> out = new ArrayList<Setting>();
        for (Setting s2 : this.getSettings()) {
            if (!s2.getParentMod().equals(mod)) continue;
            out.add(s2);
        }
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }

    public Setting getSettingByName(String name) {
        for (Setting set : this.getSettings()) {
            if (!set.getName().equalsIgnoreCase(name)) continue;
            return set;
        }
        System.err.println("[" + amerikan.Client_Name + "] Error Setting NOT found: '" + name + "'!");
        return null;
    }
}

