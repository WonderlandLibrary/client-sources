package club.mega.util;

import club.mega.Mega;

import java.awt.*;

public final class ColorUtil {

    public static Color getMainColor() {
        return new Color(Mega.INSTANCE.getModuleManager().getModule(club.mega.module.impl.hud.Color.class).red.getAsInt(), Mega.INSTANCE.getModuleManager().getModule(club.mega.module.impl.hud.Color.class).green.getAsInt(), Mega.INSTANCE.getModuleManager().getModule(club.mega.module.impl.hud.Color.class).blue.getAsInt());
    }

    public static Color getMainColor(final int alpha) {
        return new Color(Mega.INSTANCE.getModuleManager().getModule(club.mega.module.impl.hud.Color.class).red.getAsInt(), Mega.INSTANCE.getModuleManager().getModule(club.mega.module.impl.hud.Color.class).green.getAsInt(), Mega.INSTANCE.getModuleManager().getModule(club.mega.module.impl.hud.Color.class).blue.getAsInt(), alpha);
    }

    public static Color getGradientOffset(final Color color1, final Color color2, final double index) {
        double offs = (Math.abs(((System.currentTimeMillis()) / 16D)) / 60D) + index;
        if(offs >1)
        {
            double left = offs % 1;
            int off = (int) offs;
            offs = off % 2 == 0 ? left : 1 - left;
        }

        final double inverse_percent = 1 - offs;
        int redPart = (int) (color1.getRed() * inverse_percent + color2.getRed() * offs);
        int greenPart = (int) (color1.getGreen() * inverse_percent + color2.getGreen() * offs);
        int bluePart = (int) (color1.getBlue() * inverse_percent + color2.getBlue() * offs);
        int alphaPart = (int) (color1.getAlpha() * inverse_percent + color2.getAlpha() * offs);
        return new Color(redPart, greenPart, bluePart, alphaPart);
    }

}
