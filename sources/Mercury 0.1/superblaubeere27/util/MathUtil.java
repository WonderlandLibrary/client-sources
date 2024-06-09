/*
 * Decompiled with CFR 0.145.
 */
package superblaubeere27.util;

public class MathUtil {
    public static double distance(float x2, float y2, float x1, float y1) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}

