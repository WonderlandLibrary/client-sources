/*
 * Decompiled with CFR 0.145.
 */
package superblaubeere27.util;

public class Color {
    public static java.awt.Color rainbow(float speed, float off) {
        double time = (double)System.currentTimeMillis() / (double)speed;
        time += (double)off;
        return java.awt.Color.getHSBColor((float)((time %= 255.0) / 255.0), 1.0f, 1.0f);
    }
}

