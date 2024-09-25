/*
 * Decompiled with CFR 0.150.
 */
package skizzle.settings;

import skizzle.files.FileManager;
import skizzle.settings.Setting;

public class BooleanSetting
extends Setting {
    public boolean enabled;

    public static {
        throw throwable;
    }

    public boolean isEnabled() {
        BooleanSetting Nigga;
        return Nigga.enabled;
    }

    public void toggle() {
        BooleanSetting Nigga;
        Nigga.enabled = !Nigga.enabled;
        FileManager Nigga2 = new FileManager();
        Nigga2.updateSettings();
    }

    public BooleanSetting(String Nigga, boolean Nigga2) {
        BooleanSetting Nigga3;
        Nigga3.name = Nigga;
        Nigga3.enabled = Nigga2;
    }

    public void setEnabled(boolean Nigga) {
        Nigga.enabled = Nigga;
    }
}

