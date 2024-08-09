package wtf.resolute.moduled.settings;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Setting<Value> implements ISetting {

    Value defaultVal;

    String settingName;
    public Supplier<Boolean> visible = () -> true;

    public Setting(String name, Value defaultVal) {
        this.settingName = name;
        this.defaultVal = defaultVal;
    }


    public String getName() {
        return settingName;
    }

    public void set(Value value) {
        defaultVal = value;
    }

    @Override
    public Setting<?> setVisible(Supplier<Boolean> bool) {
        visible = bool;
        return this;
    }

    public ArrayList<Setting> settingList = new ArrayList<>();

    public Value get() {
        return defaultVal;
    }
    public enum SettingType {
        BOOLEAN_OPTION,
        SLIDER_SETTING,
        MODE_SETTING,
        COLOR_SETTING,
        MULTI_BOX_SETTING,
        BIND_SETTING,
        BUTTON_SETTING,
        TEXT_SETTING
    }
}