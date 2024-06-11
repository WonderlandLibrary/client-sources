package Hydro.module.modules.render;

import java.awt.*;

import Hydro.module.Category;
import Hydro.module.Module;

public class CustomGlint extends Module {

    public static float armorHue = 0.8f;
    public static float itemHue = 0.8f;
    public static boolean rainbow = false;

    public CustomGlint() {
        super("CustomGlint", 0, true, Category.RENDER, "Custom enchantment glint");
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() * 2 + offset / 2) % speed * 2);
        hue /= speed;
        return Color.getHSBColor(hue, 1.0F, 1.0F).getRGB();
    }
}
