package im.expensive.functions.settings;

import java.util.function.Supplier;

public class Setting<T> implements ISetting {

    T defaultVal;

    String settingName;
    public Supplier<Boolean> visible = () -> true;

    public Setting(String name, T defaultVal) {
        this.settingName = name;
        this.defaultVal = defaultVal;
    }


    public String getName() {
        return settingName;
    }

    public void set(T value) {
        defaultVal = value;
    }

    @Override
    public Setting<?> setVisible(Supplier<Boolean> bool) {
        visible = bool;
        return this;
    }



    public T get() {
        return defaultVal;
    }

}