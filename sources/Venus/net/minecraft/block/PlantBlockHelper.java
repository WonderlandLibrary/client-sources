/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.BlockState;

public class PlantBlockHelper {
    public static boolean isAir(BlockState blockState) {
        return blockState.isAir();
    }

    public static int getGrowthAmount(Random random2) {
        double d = 1.0;
        int n = 0;
        while (random2.nextDouble() < d) {
            d *= 0.826;
            ++n;
        }
        return n;
    }
}

