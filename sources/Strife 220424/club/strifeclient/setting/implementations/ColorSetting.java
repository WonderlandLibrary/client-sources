package club.strifeclient.setting.implementations;

import club.strifeclient.setting.Setting;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.function.Supplier;

public class ColorSetting extends Setting<Color> {
    @Getter @Setter
    private int opacity;

    public ColorSetting(String name, Color value, Supplier<Boolean> dependency) {
        super(name, value, dependency);
        opacity = value.getAlpha();
    }
    public ColorSetting(String name, Color value) {
        this(name, value, () -> true);
    }

    @Override
    public void parse(Object original) {
        if (original instanceof String[]) {
            String[] rgb = (String[]) original;
            setValue(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
            opacity = Integer.parseInt(rgb[3]);
        }
    }
}
