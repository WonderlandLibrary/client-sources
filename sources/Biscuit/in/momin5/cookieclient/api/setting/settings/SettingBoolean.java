package in.momin5.cookieclient.api.setting.settings;

import com.lukflug.panelstudio.settings.Toggleable;
import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.Setting;

public class SettingBoolean extends Setting implements Toggleable {
    public boolean enabled;

    public SettingBoolean(String name, Module parent, boolean enabled) {
        super(name,parent);
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }

    public void toggle() {
        this.enabled = !this.enabled;

        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }

    @Override
    public boolean isOn() {
        return this.isEnabled();
    }

}
