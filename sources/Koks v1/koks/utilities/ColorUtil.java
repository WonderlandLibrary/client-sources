package koks.utilities;

import net.minecraft.client.Minecraft;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 19:47
 */
public class ColorUtil {

    public int rainbow(int speed, float offset, float saturation) {
        float rainbow = (float) ((System.currentTimeMillis() - (offset % speed) / 0.25) % speed);
        rainbow /= speed;
        return Color.getHSBColor(rainbow, saturation, 1F).getRGB();
    }

}
