package in.momin5.cookieclient.api.setting.settings;

import com.lukflug.panelstudio.settings.EnumSetting;
import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.Setting;

import java.util.Arrays;
import java.util.List;

public class SettingMode extends Setting implements EnumSetting {
    public int index;

    public List<String> modes;

    public SettingMode(String name, Module parent, String defaultMode, String... modes) {
        super(name,parent);
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getMode() {
        return this.modes.get(this.index);
    }

    public void setMode(String mode) {
        this.index = this.modes.indexOf(mode);

        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }

    public boolean is(String mode) {
        return (this.index == this.modes.indexOf(mode));
    }

    public void cycle() {
        if (this.index < this.modes.size() - 1) {
            this.index++;
        } else {
            this.index = 0;
        }
    }

    @Override
    public String getValueName() {
        return this.modes.get(this.index);
    }

    @Override
    public void increment() {
        if (this.index < this.modes.size() - 1) {
            this.index++;
        } else {
            this.index = 0;
        }
    }
}
