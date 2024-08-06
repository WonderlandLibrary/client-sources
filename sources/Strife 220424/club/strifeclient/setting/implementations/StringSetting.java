package club.strifeclient.setting.implementations;

import club.strifeclient.setting.Setting;
import imgui.type.ImString;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class StringSetting extends Setting<String> {

    private ImString imEquivalent;

    public StringSetting(String name, String value, Supplier<Boolean> dependency) {
        super(name, value, dependency);
        imEquivalent = new ImString(value);
        super.addChangeCallback((settingOld, settingNew) -> imEquivalent.set(settingNew.getValue()));
    }
    public StringSetting(String name, String value) {
        this(name, value, () -> true);
    }

    @Override
    public String toString() {
        return value;
    }
}
