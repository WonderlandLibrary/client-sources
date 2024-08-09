package fun.ellant.ui.midnight.component;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import fun.ellant.utils.font.Fonts;


import java.awt.*;

public class ColorThemeWindow {

    private Color component;

    public Vector4f pos = new Vector4f();
    private float[] hsb = new float[2];
    private float alpha;
    private boolean dragging;
    private boolean draggingHue;
    private boolean draggingAlpha;

    public ColorThemeWindow(Color component, Vector4f pos) {
        this.pos = pos;
        this.component = component;
        hsb = Color.RGBtoHSB(component.getRed(), component.getGreen(), component.getBlue(), null);
        alpha = component.getAlpha() / 160;
    }

    public static float[] copied = new float[2];

    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        component = new Color(DisplayUtils.reAlphaInt(Color.getHSBColor(hsb[0], hsb[1], hsb[2]).getRGB(), (int) (alpha * 255)));

        float width = 228 / 2f;
        float x = pos.x + pos.z - 10 + 8 / 2f;
        float y = pos.y + pos.w / 2f + 8 / 2f;

        DisplayUtils.drawShadow(pos.x + pos.z - 10 + 8 / 2f, pos.y + pos.w / 2f + 8 / 2f, 228 / 2f, 321 / 2f, 9, new Color(40, 40, 40).getRGB());

        DisplayUtils.drawRoundedRect(pos.x + pos.z - 10 + 8 / 2f, pos.y + pos.w / 2f + 8 / 2f, 228 / 2f, 321 / 2f, 3, new Color(17, 18, 21).getRGB());
        DisplayUtils.drawGradientRound(x + 4, y + 4, width - 8, width - 8, 2, Color.WHITE.getRGB(), Color.BLACK.getRGB(), Color.getHSBColor(hsb[0], 1, 1).getRGB(), Color.BLACK.getRGB());


        if (dragging) {
            float saturation = MathHelper.clamp((mouseX - x - 4), 0, width - 8) / (width - 8);
            float brightness = MathHelper.clamp((mouseY - y - 4), 0, width - 8) / (width - 8);
            hsb[1] = saturation;
            hsb[2] = 1 - brightness;
        }

        float circleX = x + 4 + hsb[1] * (width - 8);
        float circleY = y + 4 + (1 - hsb[2]) * (width - 8);

        DisplayUtils.drawCircle(circleX + 1, circleY + 1, 8, Color.BLACK.getRGB());
        DisplayUtils.drawCircle(circleX + 1, circleY + 1, 6, DisplayUtils.reAlphaInt(component.getRGB(), 255));

        for (int i = 0; i < width - 12; i++) {
            float hue = i / (width - 12);
            DisplayUtils.drawCircle(x + 6 + i, y + width + 6, 6, DisplayUtils.reAlphaInt(Color.HSBtoRGB(hue, 1, 1), 255));
        }
        for (int i = 0; i < width - 12; i++) {
            float hue = i / (width - 12);
            DisplayUtils.drawCircle(x + 6 + i, y + width + 18, 6, ColorUtils.interpolateColor(DisplayUtils.reAlphaInt(component.getRGB(), 255), new Color(17, 18, 21).getRGB(), 1 - hue));
        }
        DisplayUtils.drawCircle(x + 6 + alpha * (width - 12), y + width + 18, 8, Color.BLACK.getRGB());
        DisplayUtils.drawCircle(x + 6 + alpha * (width - 12), y + width + 18, 6, ColorUtils.interpolateColor(new Color(17, 18, 21).getRGB(), DisplayUtils.reAlphaInt(component.getRGB(), 255), alpha));
        if (draggingHue) {
            float hue = MathHelper.clamp((mouseX - x - 6), 0, width - 12) / (width - 12);
            hsb[0] = hue;
        }
        if (draggingAlpha) {
            float hue = MathHelper.clamp((mouseX - x - 6), 0, width - 12) / (width - 12);
            alpha = hue;
        }

        DisplayUtils.drawRoundedRect(x + 4, y + 321 / 2f - 36 / 2f - 4, 102 / 2f, 36 / 2f, 3, new Color(40, 45, 51).getRGB());
        DisplayUtils.drawRoundedRect(x + 4 + 51 + 4, y + 321 / 2f - 36 / 2f - 4, 102 / 2f, 36 / 2f, 3, new Color(40, 45, 51).getRGB());
        Fonts.gilroy[14].drawCenteredString(stack, "Копировать", x + 4 + 51 / 2f, y + 321 / 2f - 14.5f, -1);
        Fonts.gilroy[14].drawCenteredString(stack, "Вставить", x + 4 + 51 + 4 + 51 / 2f, y + 321 / 2f - 14.5f, -1);
        DisplayUtils.drawCircle(x + 6 + hsb[0] * (width - 12), y + width + 6, 8, Color.BLACK.getRGB());
        DisplayUtils.drawCircle(x + 6 + hsb[0] * (width - 12), y + width + 6, 6, Color.HSBtoRGB(hsb[0], 1, 1));
    }

    public void onConfigUpdate() {
        hsb = Color.RGBtoHSB(component.getRed(), component.getGreen(), component.getBlue(), null);
        alpha = component.getAlpha() / 255f;
    }

    public int getColor() {
        return component.getRGB();
    }

    public void click(int mouseX, int mouseY) {

        float width = 228 / 2f;
        float x = pos.x + pos.z - 10 + 8 / 2f;
        float y = pos.y + pos.w / 2f + 8 / 2f;

        if (MathUtil.isInRegion(mouseX, mouseY, x + 4, y + width + 1, width - 8, 6)) {
            draggingHue = true;
        }
        if (MathUtil.isInRegion(mouseX, mouseY, x + 4, y + 321 / 2f - 36 / 2f - 4, 102 / 2f, 36 / 2f)) {
            ColorThemeWindow.copied = Color.RGBtoHSB(component.getRed(), component.getGreen(), component.getBlue(), null);
            ;
        }
        if (MathUtil.isInRegion(mouseX, mouseY, x + 4 + 51 + 4, y + 321 / 2f - 36 / 2f - 4, 102 / 2f, 36 / 2f)) {
            if (ColorThemeWindow.copied.length >= 3) {
                component = new Color(Color.HSBtoRGB(ColorThemeWindow.copied[0], ColorThemeWindow.copied[1], ColorThemeWindow.copied[2]));
            } else {
                System.out.println("index < 3");
            }
            hsb = Color.RGBtoHSB(component.getRed(), component.getGreen(), component.getBlue(), null);
        }
        if (MathUtil.isInRegion(mouseX, mouseY, x + 4, y + width + 13, width - 8, 6)) {
            draggingAlpha = true;
        }
        if (MathUtil.isInRegion(mouseX, mouseY, x + 4, y + 4, width - 8, width - 8)) {
            dragging = true;
        }

    }

    public void unclick(int mouseX, int mouseY) {
        dragging = false;
        draggingHue = false;
        draggingAlpha = false;
    }

}
