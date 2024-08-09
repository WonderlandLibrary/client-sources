package im.expensive.functions.settings.impl;


import im.expensive.functions.settings.Setting;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModeListSetting extends Setting<List<BooleanSetting>> {
    public ModeListSetting(String name, BooleanSetting... strings) {
        super(name, Arrays.asList(strings));
    }

    public BooleanSetting getValueByName(String settingName) {
        return get().stream().filter(booleanSetting -> booleanSetting.getName().equalsIgnoreCase(settingName)).findFirst().orElse(null);
    }

    public BooleanSetting get(int index) {
        return get().get(index);
    }

    @Override
    public ModeListSetting setVisible(Supplier<Boolean> bool) {
        return (ModeListSetting) super.setVisible(bool);
    }
}