package wtf.diablo.client.setting.impl;

import wtf.diablo.client.setting.api.AbstractSetting;

import java.awt.*;

public final class ColorSetting extends AbstractSetting<Color> {
    public ColorSetting(final String name, final Color value) {
        super(name, value);
    }

    @Override
    public Color parse(String value) {
        final String[] colorComponents = value.replaceAll("[^\\d,]", "").split(",");

        return new Color(Integer.parseInt(colorComponents[0]), Integer.parseInt(colorComponents[1]), Integer.parseInt(colorComponents[2]));
    }
}
