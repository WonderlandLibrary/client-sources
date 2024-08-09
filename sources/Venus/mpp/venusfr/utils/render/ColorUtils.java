/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.utils.math.MathUtil;
import net.minecraft.util.math.MathHelper;

public final class ColorUtils {
    public static final int green = new Color(64, 255, 64).getRGB();
    public static final int yellow = new Color(255, 255, 64).getRGB();
    public static final int orange = new Color(255, 128, 32).getRGB();
    public static final int red = new Color(255, 64, 64).getRGB();

    public static int rgb(int n, int n2, int n3) {
        return 0xFF000000 | n << 16 | n2 << 8 | n3;
    }

    public static int rgba(int n, int n2, int n3, int n4) {
        return n4 << 24 | n << 16 | n2 << 8 | n3;
    }

    public static void setAlphaColor(int n, float f) {
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        RenderSystem.color4f(f2, f3, f4, f);
    }

    public static int getColor(int n) {
        return HUD.getColor(n);
    }

    public static void setColor(int n) {
        ColorUtils.setAlphaColor(n, (float)(n >> 24 & 0xFF) / 255.0f);
    }

    public static int toColor(String string) {
        int n = Integer.parseInt(string.substring(1), 16);
        return ColorUtils.setAlpha(n, 255);
    }

    public static int setAlpha(int n, int n2) {
        return n & 0xFFFFFF | n2 << 24;
    }

    public static float[] rgba(int n) {
        return new float[]{(float)(n >> 16 & 0xFF) / 255.0f, (float)(n >> 8 & 0xFF) / 255.0f, (float)(n & 0xFF) / 255.0f, (float)(n >> 24 & 0xFF) / 255.0f};
    }

    public static int gradient(int n, int n2, int n3, int n4) {
        int n5 = (int)((System.currentTimeMillis() / (long)n4 + (long)n3) % 360L);
        n5 = (n5 > 180 ? 360 - n5 : n5) + 180;
        int n6 = ColorUtils.interpolate(n, n2, MathHelper.clamp((float)n5 / 180.0f - 1.0f, 0.0f, 1.0f));
        float[] fArray = ColorUtils.rgba(n6);
        float[] fArray2 = Color.RGBtoHSB((int)(fArray[0] * 255.0f), (int)(fArray[1] * 255.0f), (int)(fArray[2] * 255.0f), null);
        fArray2[1] = fArray2[1] * 1.5f;
        fArray2[1] = Math.min(fArray2[1], 1.0f);
        return Color.HSBtoRGB(fArray2[0], fArray2[1], fArray2[2]);
    }

    public static int interpolate(int n, int n2, float f) {
        float[] fArray = ColorUtils.rgba(n);
        float[] fArray2 = ColorUtils.rgba(n2);
        return ColorUtils.rgba((int)MathUtil.interpolate(fArray[0] * 255.0f, fArray2[0] * 255.0f, (double)f), (int)MathUtil.interpolate(fArray[1] * 255.0f, fArray2[1] * 255.0f, (double)f), (int)MathUtil.interpolate(fArray[2] * 255.0f, fArray2[2] * 255.0f, (double)f), (int)MathUtil.interpolate(fArray[3] * 255.0f, fArray2[3] * 255.0f, (double)f));
    }

    public static void setColor1(int n) {
        ColorUtils.setAlphaColor(n, (float)(n >> 24 & 0xFF) / 255.0f);
    }

    private ColorUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

