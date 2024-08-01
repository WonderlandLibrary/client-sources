package wtf.diablo.client.util.render.color;

import wtf.diablo.client.util.render.ColorUtil;

import java.awt.*;

public enum EnumPallete {
    DIABLO_COLORS(new Color(ColorUtil.PRIMARY_MAIN_COLOR.getRGB()), ColorUtil.SECONDARY_MAIN_COLOR),
    ;

    private final Color[] colors;

    EnumPallete(final Color... color) {
        this.colors = color;
    }

    public Color[] getColors() {
        return this.colors;
    }
}
