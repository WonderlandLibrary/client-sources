package dev.stephen.nexus.utils.render;

import dev.stephen.nexus.module.modules.client.Theme;

import java.awt.*;

public class ThemeUtils {

    public static Color getThemeColor(int i) {
        return ColorUtils.interpolateBetween(getMainColor(), getSecondColor(), 4, i * 20L);
    }

    public static Color getMainColor() {
        switch (Theme.themeMode.getMode()) {
            case "Bubblegum":
                return new Color(33, 212, 253);
            case "Shade":
                return new Color(142, 197, 252);
            case "Moon":
                return new Color(157, 53, 254);
            case "Lime":
                return new Color(133, 255, 189);
            case "Rise":
                return new Color(8, 174, 234);
            case "Fire":
                return new Color(240, 36, 61);
            case "Mango":
                return new Color(247, 107, 28);
            case "XMAX":
                return new Color(179, 0, 0);
            case "Amethyst":
                return new Color(72, 34, 108);
            case "Vibes":
                return new Color(255, 209, 42);
            case "Tenacity":
                return new Color(28, 167, 222);
            case "Custom":
                return new Color(Theme.redMain.getValueInt(), Theme.greenMain.getValueInt(), Theme.blueMain.getValueInt());
        }
        return null;
    }

    public static Color getSecondColor() {
        switch (Theme.themeMode.getMode()) {
            case "Bubblegum":
                return new Color(183, 33, 255);
            case "Shade":
                return new Color(224, 195, 252);
            case "Moon":
                return new Color(50, 136, 235);
            case "Lime":
                return new Color(255, 251, 125);
            case "Rise":
                return new Color(42, 245, 152);
            case "Fire":
                return new Color(245, 143, 42);
            case "Mango":
                return new Color(250, 217, 97);
            case "XMAX":
                return new Color(0, 133, 53);
            case "Amethyst":
                return new Color(191, 62, 255);
            case "Vibes":
                return new Color(255, 94, 147);
            case "Tenacity":
                return new Color(236, 133, 209);
            case "Custom":
                return new Color(Theme.redSecond.getValueInt(), Theme.greenSecond.getValueInt(), Theme.blueSecond.getValueInt());
        }
        return null;
    }
}
