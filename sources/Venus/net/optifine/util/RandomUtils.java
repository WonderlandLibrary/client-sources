/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();

    public static Random getRandom() {
        return random;
    }

    public static int getRandomInt(int n) {
        return random.nextInt(n);
    }
}

