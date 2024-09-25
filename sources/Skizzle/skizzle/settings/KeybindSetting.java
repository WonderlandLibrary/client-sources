/*
 * Decompiled with CFR 0.150.
 */
package skizzle.settings;

import skizzle.files.FileManager;
import skizzle.settings.Setting;

public class KeybindSetting
extends Setting {
    public int code;
    public boolean cgSelected;

    public KeybindSetting(String Nigga, int Nigga2) {
        KeybindSetting Nigga3;
        Nigga3.cgSelected = false;
        Nigga3.name = Nigga;
        Nigga3.code = Nigga2;
    }

    public KeybindSetting(int Nigga) {
        KeybindSetting Nigga2;
        Nigga2.cgSelected = false;
        Nigga2.name = Qprot0.0("\ub288\u71ce\u89ce\ua7e6\u9c79\u3717\u8c2b");
        Nigga2.code = Nigga;
    }

    public void setKeyCode(int Nigga) {
        Nigga.code = Nigga;
        FileManager Nigga2 = new FileManager();
        Nigga2.updateSettings();
    }

    public static {
        throw throwable;
    }

    public int getKeyCode() {
        KeybindSetting Nigga;
        return Nigga.code;
    }
}

