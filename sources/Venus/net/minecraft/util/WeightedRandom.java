/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.List;
import java.util.Random;
import net.minecraft.util.Util;

public class WeightedRandom {
    public static int getTotalWeight(List<? extends Item> list) {
        int n = 0;
        int n2 = list.size();
        for (int i = 0; i < n2; ++i) {
            Item item = list.get(i);
            n += item.itemWeight;
        }
        return n;
    }

    public static <T extends Item> T getRandomItem(Random random2, List<T> list, int n) {
        if (n <= 0) {
            throw Util.pauseDevMode(new IllegalArgumentException());
        }
        int n2 = random2.nextInt(n);
        return WeightedRandom.getRandomItem(list, n2);
    }

    public static <T extends Item> T getRandomItem(List<T> list, int n) {
        int n2 = list.size();
        for (int i = 0; i < n2; ++i) {
            Item item = (Item)list.get(i);
            if ((n -= item.itemWeight) >= 0) continue;
            return (T)item;
        }
        return (T)((Item)null);
    }

    public static <T extends Item> T getRandomItem(Random random2, List<T> list) {
        return WeightedRandom.getRandomItem(random2, list, WeightedRandom.getTotalWeight(list));
    }

    public static class Item {
        protected final int itemWeight;

        public Item(int n) {
            this.itemWeight = n;
        }
    }
}

