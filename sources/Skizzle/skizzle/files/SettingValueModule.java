/*
 * Decompiled with CFR 0.150.
 */
package skizzle.files;

import java.util.ArrayList;
import java.util.List;
import skizzle.files.settingvalues.SettingValue;
import skizzle.files.settingvalues.SettingValueBool;
import skizzle.files.settingvalues.SettingValueNum;
import skizzle.settings.BooleanSetting;
import skizzle.settings.KeybindSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.settings.Setting;

public class SettingValueModule {
    public String name;
    public List<SettingValue> settings = new ArrayList<SettingValue>();

    public void addSetting(Setting Nigga) {
        SettingValueModule Nigga2;
        if (Nigga instanceof BooleanSetting) {
            Nigga2.settings.add(new SettingValueBool(Nigga.name, ((BooleanSetting)Nigga).isEnabled()));
        }
        if (Nigga instanceof NumberSetting) {
            Nigga2.settings.add(new SettingValueNum(Nigga.name, ((NumberSetting)Nigga).getValue()));
        }
        if (Nigga instanceof ModeSetting) {
            Nigga2.settings.add(new SettingValueNum(Nigga.name, ((ModeSetting)Nigga).getIndex().intValue()));
        }
        if (Nigga instanceof KeybindSetting) {
            Nigga2.settings.add(new SettingValueNum(Nigga.name, ((KeybindSetting)Nigga).getKeyCode()));
        }
    }

    public static {
        throw throwable;
    }

    public SettingValueModule(String Nigga) {
        SettingValueModule Nigga2;
        Nigga2.name = Nigga;
    }
}

