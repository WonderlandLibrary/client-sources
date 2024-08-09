package wtf.resolute.ui.styled;

import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StyleManager {
    public List<Style> styles = new ArrayList<>();
    private Style currentStyle = null;

    public void init() {
        styles.addAll(Arrays.asList(
                new Style("Ягодный рай", HexColor.toColor("#8A2BE2"), HexColor.toColor("#BE2B8A")),
                new Style("Зеленый Микс", HexColor.toColor("#FF1493"), HexColor.toColor("#7CFC00")),
                new Style("Восход солнца", HexColor.toColor("#FF6347"), HexColor.toColor("#9400D3")),
                new Style("Малиновый микс", HexColor.toColor("#4682B4"), HexColor.toColor("#FF69B4")),
                new Style("Снежный холодок", HexColor.toColor("#191970"), HexColor.toColor("#B0C4DE")),
                new Style("Песочно холодный", HexColor.toColor("#9370DB"), HexColor.toColor("#FFD700")),
                new Style("Красный", HexColor.toColor("#df0606"), HexColor.toColor("#000000")),
                new Style("Мармеладный", HexColor.toColor("#00BFFF"), HexColor.toColor("#FF1493")),
                new Style("Кровавый", HexColor.toColor("#3D3D3D"), HexColor.toColor("#9400D3")),
                new Style("Зеленый", HexColor.toColor("#27AE60"), HexColor.toColor("#2ECC71")),
                new Style("Синий", HexColor.toColor("#2C3E50"), HexColor.toColor("#191970")),
                new Style("Вечерьний", HexColor.toColor("#800080"), HexColor.toColor("#87CEEB")),
                new Style("Лиловый", HexColor.toColor("#FFD700"), HexColor.toColor("#32CD32")),
                new Style("Голубой", HexColor.toColor("#87CEEB"), HexColor.toColor("#4682B4")),
                new Style("Ананасовый", HexColor.toColor("#FFD700"), HexColor.toColor("#8B008B")),
                new Style("Бело розовый", HexColor.toColor("#E6E8FA"), HexColor.toColor("#FF69B4")),
                new Style("Солнечный", HexColor.toColor("#FF4500"), HexColor.toColor("#FFD700"))));
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
