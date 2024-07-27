package dev.nexus.utils.client;

import dev.nexus.Nexus;
import dev.nexus.modules.impl.render.Theme;

import java.awt.*;

public class ThemeUtil {

    public static Color getMainColor(int alpha) {
        int red = Nexus.INSTANCE.getModuleManager().getModule(Theme.class).red.getValueInt();
        int green = Nexus.INSTANCE.getModuleManager().getModule(Theme.class).green.getValueInt();
        int blue = Nexus.INSTANCE.getModuleManager().getModule(Theme.class).blue.getValueInt();
        return new Color(red, green, blue, alpha);
    }

    public static Color getMainColor() {
        int red = Nexus.INSTANCE.getModuleManager().getModule(Theme.class).red.getValueInt();
        int green = Nexus.INSTANCE.getModuleManager().getModule(Theme.class).green.getValueInt();
        int blue = Nexus.INSTANCE.getModuleManager().getModule(Theme.class).blue.getValueInt();
        return new Color(red, green, blue);
    }
}
