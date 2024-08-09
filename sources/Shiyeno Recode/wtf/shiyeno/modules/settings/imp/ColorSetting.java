package wtf.shiyeno.modules.settings.imp;

import wtf.shiyeno.modules.settings.Setting;

import java.awt.*;
import java.util.function.Supplier;

public class ColorSetting extends Setting {
    public int color = 0;

    public ColorSetting(String name, int color) {
        super(name);
        this.color = color;
    }

    public int get() {
        return color;
    }

    public Color getColor() {
        return new Color(color);
    }

    public ColorSetting setVisible(Supplier<Boolean> bool) {
        visible = bool;
        return this;
    }

    @Override
    public SettingType getType() {
        return SettingType.COLOR_SETTING;
    }
}
