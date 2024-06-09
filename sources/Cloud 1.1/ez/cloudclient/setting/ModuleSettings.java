package ez.cloudclient.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ModuleSettings {
    @Expose
    @SerializedName("Settings")
    private final HashMap<String, Setting> settings = new HashMap<>();

    public void addSetting(String settingName, Setting defaultValue) {
        settings.put(settingName, defaultValue);
    }

    // there is a check for this in the method
    @SuppressWarnings("unchecked")
    public <T extends Setting> T getSetting(String settingName, Class<T> type) {
        Setting setting = settings.get(settingName);
        if (setting.getClass() == type) {
            return (T) setting;
        }
        return null;
    }
}
