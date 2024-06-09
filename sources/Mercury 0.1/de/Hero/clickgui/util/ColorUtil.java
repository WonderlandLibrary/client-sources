/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.clickgui.util;

import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.awt.Color;
import us.amerikan.amerikan;

public class ColorUtil {
    public static Color getClickGUIColor() {
        if (!amerikan.setmgr.getSettingByName("LSD").getValBoolean()) {
            return new Color((int)amerikan.setmgr.getSettingByName("GuiRed").getValDouble(), (int)amerikan.setmgr.getSettingByName("GuiGreen").getValDouble(), (int)amerikan.setmgr.getSettingByName("GuiBlue").getValDouble());
        }
        return ColorUtil.rainbow(100);
    }

    private static Color rainbow(int delay) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.8f, 0.7f).brighter();
    }
}

