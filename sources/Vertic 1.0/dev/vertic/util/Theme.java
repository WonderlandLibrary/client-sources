package dev.vertic.util;

import dev.vertic.util.render.ColorUtil;

import java.awt.*;

public enum Theme {

    VERTIC(new Color(102, 255, 209), new Color(6, 149, 255)),
    FIRE(new Color(210, 40, 15), new Color(220, 150, 25)),
    SKULL(new Color(240, 240, 240), new Color(110, 110, 110)),
    SKY(new Color(125, 204, 241), new Color(30, 71, 170)),
    WAVE(new Color(160, 230, 225), new Color(15, 190, 220));

    private final Color color1;
    private final Color color2;


    Theme(final Color color1, final Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    public Color getColor(int offset) {
        return ColorUtil.getColor(color1, color2, 1000, offset);
    }
    public Color getColor(int offset, int delay) {
        return ColorUtil.getColor(color1, color2, delay, offset);
    }

    public static final String[] themes = {
            "Vertic",
            "Fire",
            "Skull",
            "Sky",
            "Wave"
    };

}
