package alphentus.gui.customhud;

import java.awt.*;
import java.util.function.Supplier;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class Palette {

    /*
     * DIES IST AUS AUTUMN
     */

    public static Color fade(Color color) {
        return fade(color, 2, 100);
    }

    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index / (float) count * 2.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

}

