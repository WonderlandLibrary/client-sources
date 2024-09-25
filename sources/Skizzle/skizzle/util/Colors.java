/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import java.awt.Color;

public class Colors {
    public static int getColor(int Nigga) {
        return Colors.getColor(Nigga, Nigga, Nigga, 255);
    }

    public static {
        throw throwable;
    }

    public static int getColor(int Nigga, int Nigga2, int Nigga3, int Nigga4) {
        int Nigga5 = 0;
        Nigga5 |= Nigga4 << 24;
        Nigga5 |= Nigga << 16;
        Nigga5 |= Nigga2 << 8;
        return Nigga5 |= Nigga3;
    }

    public static int getColor(int Nigga, int Nigga2, int Nigga3) {
        return Colors.getColor(Nigga, Nigga2, Nigga3, 255);
    }

    public Colors() {
        Colors Nigga;
    }

    public static int getColor(Color Nigga) {
        return Colors.getColor(Nigga.getRed(), Nigga.getGreen(), Nigga.getBlue(), Nigga.getAlpha());
    }

    public static int getColor(int Nigga, int Nigga2) {
        return Colors.getColor(Nigga, Nigga, Nigga, Nigga2);
    }
}

