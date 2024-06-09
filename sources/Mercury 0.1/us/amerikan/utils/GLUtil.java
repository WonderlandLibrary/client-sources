/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class GLUtil {
    public static void setColor(Color color) {
        GL11.glColor4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
    }

    public static void setColor(int rgba) {
        int r2 = rgba & 255;
        int g2 = rgba >> 8 & 255;
        int b2 = rgba >> 16 & 255;
        int a2 = rgba >> 24 & 255;
        GL11.glColor4b((byte)r2, (byte)g2, (byte)b2, (byte)a2);
    }

    public static int toRGBA(Color c2) {
        return c2.getRed() | c2.getGreen() << 8 | c2.getBlue() << 16 | c2.getAlpha() << 24;
    }
}

