/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import java.util.Arrays;

public class ArrayHelper {
    public static <T> T[] popFirst(T[] Nigga) {
        T[] Nigga2 = Arrays.copyOf(Nigga, Nigga.length);
        for (int Nigga3 = 1; Nigga3 < Nigga.length; ++Nigga3) {
            Nigga2[Nigga3 - 1] = Nigga2[Nigga3];
        }
        return Nigga2;
    }

    public static <T> T[] push(T[] Nigga, T Nigga2) {
        T[] Nigga3 = Arrays.copyOf(Nigga, Nigga.length + 1);
        Nigga3[Nigga3.length - 1] = Nigga2;
        return Nigga3;
    }

    public ArrayHelper() {
        ArrayHelper Nigga;
    }

    public static {
        throw throwable;
    }

    public static <T> T[] pop(T[] Nigga) {
        T[] Nigga2 = Arrays.copyOf(Nigga, Nigga.length - 1);
        return Nigga2;
    }
}

