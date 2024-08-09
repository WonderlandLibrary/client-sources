/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via;

import net.minecraft.util.math.vector.Vector2f;

public final class MouseUtils {
    public static boolean mouseOver(double d, double d2, double d3, double d4, double d5, double d6) {
        return d5 > d && d5 < d + d3 && d6 > d2 && d6 < d2 + d4;
    }

    public static boolean mouseOver(Vector2f vector2f, Vector2f vector2f2, double d, double d2) {
        return d > (double)vector2f.x && d < (double)(vector2f.x + vector2f2.x) && d2 > (double)vector2f.y && d2 < (double)(vector2f.y + vector2f2.y);
    }
}

