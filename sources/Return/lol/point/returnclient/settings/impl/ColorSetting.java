package lol.point.returnclient.settings.impl;

import lol.point.returnclient.settings.Setting;

import java.awt.*;

public class ColorSetting extends Setting {
    public boolean expanded;
    public Color color;

    public ColorSetting(String name, Color color) {
        this.name = name;
        this.expanded = false;
        this.color = color;
    }

    public void toggle() {
        expanded = !expanded;
    }
}
