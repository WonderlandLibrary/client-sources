package lol.point.returnclient.managers;

import lol.point.returnclient.module.Module;
import lol.point.returnclient.settings.Setting;

public class SettingsManager {
    public Setting getSettingByName(Module mod, String settingName) {
        for (Setting setting : mod.settings) {
            if (setting.name.equalsIgnoreCase(settingName)) {
                return setting;
            }
        }
        return null;
    }
}