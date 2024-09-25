/*
 * Decompiled with CFR 0.150.
 */
package skizzle.settings;

import java.util.Arrays;
import java.util.List;
import skizzle.files.FileManager;
import skizzle.settings.Setting;

public class ModeSetting
extends Setting {
    public int index;
    public List<String> modes;
    public boolean expanded;

    public void setMode(Integer Nigga) {
        Nigga.index = Nigga;
    }

    public boolean is(String Nigga) {
        ModeSetting Nigga2;
        return Nigga2.index == Nigga2.modes.indexOf(Nigga);
    }

    public Integer getIndex() {
        ModeSetting Nigga;
        return Nigga.index;
    }

    public ModeSetting(String Nigga, String Nigga2, String ... Nigga3) {
        ModeSetting Nigga4;
        Nigga4.name = Nigga;
        Nigga4.modes = Arrays.asList(Nigga3);
        Nigga4.index = Nigga4.modes.indexOf(Nigga2);
    }

    public void cycle(boolean Nigga) {
        ModeSetting Nigga2;
        Nigga2.index = Nigga ? (Nigga2.index < Nigga2.modes.size() - 1 ? ++Nigga2.index : 0) : (Nigga2.index > 0 ? --Nigga2.index : Nigga2.modes.size() - 1);
        FileManager Nigga3 = new FileManager();
        Nigga3.updateSettings();
    }

    public String getMode() {
        ModeSetting Nigga;
        if (Nigga.index < 0) {
            Nigga.index = 0;
        }
        return Nigga.modes.get(Nigga.index);
    }

    public static {
        throw throwable;
    }
}

