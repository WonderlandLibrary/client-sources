package club.strifeclient.setting.implementations;

import club.strifeclient.setting.Setting;
import imgui.type.ImBoolean;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class BooleanSetting extends Setting<Boolean> {

    private ImBoolean imEquivalent;

    public BooleanSetting(String name, Boolean value, Supplier<Boolean> dependency) {
        super(name, value, dependency);
        imEquivalent = new ImBoolean(value);
        super.addChangeCallback((settingOld, settingNew) -> imEquivalent.set(settingNew.getValue()));
    }

    public BooleanSetting(String name, Boolean value) {
        this(name, value, () -> true);
    }

    @Override
    public void parse(Object original) {
        setValue(Boolean.parseBoolean(original.toString()));
    }
}
