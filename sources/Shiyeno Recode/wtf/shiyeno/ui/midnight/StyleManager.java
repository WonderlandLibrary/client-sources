package wtf.shiyeno.ui.midnight;

import net.minecraft.util.math.MathHelper;
import org.apache.commons.codec.binary.Hex;
import wtf.shiyeno.util.render.ColorUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StyleManager {
    public List<Style> styles = new ArrayList<>();
    private Style currentStyle = null;

    public void init() {
        styles.addAll(Arrays.asList(
                        new Style("Радужный", HexColor.toColor("#000000"), HexColor.toColor("#FFFFFF")),
                        new Style("Цветной", HexColor.toColor("#000000"), HexColor.toColor("#FFFFFF")),
                        new Style("Жемчужный", HexColor.toColor("#EAE0C8"), HexColor.toColor("#F5F5DC")),
                        new Style("Звёздный", HexColor.toColor("#1E90FF"), HexColor.toColor("#FFD700")),
                        new Style("Лазурный", HexColor.toColor("#00BFFF"), HexColor.toColor("#E0FFFF")),
                        new Style("Лунный", HexColor.toColor("#483D8B"), HexColor.toColor("#F0E68C")),
                        new Style("Изумрудный", HexColor.toColor("#2E8B57"), HexColor.toColor("#ADFF2F")),
                        new Style("Сиреневый", HexColor.toColor("#9370DB"), HexColor.toColor("#EE82EE")),
                        new Style("Винтажный", HexColor.toColor("#D2B48C"), HexColor.toColor("#8B4513")),
                        new Style("Рубиновый", HexColor.toColor("#DC143C"), HexColor.toColor("#FFC0CB")),
                        new Style("Золотистый", HexColor.toColor("#FFD700"), HexColor.toColor("#FFF8DC")),
                        new Style("Россовый", HexColor.toColor("#4682B4"), HexColor.toColor("#B0C4DE")),
                        new Style("Свой цвет", HexColor.toColor("#765AA5"), HexColor.toColor("#F4ECFF"))
                )
        );
        currentStyle = styles.get(0);
    }

    public static Color astolfo(float yDist, float yTotal, float saturation, float speedt) {
        float speed = 1800f;
        float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * speedt;
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.getHSBColor(hue, saturation, 1F);
    }

    public void setCurrentStyle(Style style) {
        currentStyle = style;
    }

    public Style getCurrentStyle() {
        return currentStyle;
    }

    public static class HexColor {
        public static int toColor(String hexColor) {
            int argb = Integer.parseInt(hexColor.substring(1), 16);
            return reAlphaInt(argb, 255);
        }

        public static int reAlphaInt(final int color, final int alpha) {
            return (MathHelper.clamp(alpha, 0, 255) << 24) | (color & 16777215);
        }
    }
}