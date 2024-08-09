package im.expensive.functions.settings.impl;

import im.expensive.functions.settings.Setting;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.function.Supplier;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StringSetting extends Setting<String> {

    final String description;
    boolean onlyNumber;
    public StringSetting(String name, String defaultVal, String description) {
        super(name, defaultVal);
        this.description = description;
    }

    public StringSetting(String name, String defaultVal, String description, boolean onlyNumber) {
        super(name, defaultVal);
        this.description = description;
        this.onlyNumber = onlyNumber;
    }

    @Override
    public StringSetting setVisible(Supplier<Boolean> bool) {
        return (StringSetting) super.setVisible(bool);
    }
}
