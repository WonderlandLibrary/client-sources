/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import net.minecraft.util.FastRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.IBiomeMagnifier;

public enum FuzzedBiomeMagnifier implements IBiomeMagnifier
{
    INSTANCE;

    ThreadLocal<double[]> DOUBLE8 = ThreadLocal.withInitial(FuzzedBiomeMagnifier::lambda$new$0);

    @Override
    public Biome getBiome(long l, int n, int n2, int n3, BiomeManager.IBiomeReader iBiomeReader) {
        int n4;
        int n5;
        int n6;
        int n7;
        int n8 = n - 2;
        int n9 = n2 - 2;
        int n10 = n3 - 2;
        int n11 = n8 >> 2;
        int n12 = n9 >> 2;
        int n13 = n10 >> 2;
        double d = (double)(n8 & 3) / 4.0;
        double d2 = (double)(n9 & 3) / 4.0;
        double d3 = (double)(n10 & 3) / 4.0;
        double[] dArray = this.DOUBLE8.get();
        for (n7 = 0; n7 < 8; ++n7) {
            boolean bl = (n7 & 4) == 0;
            boolean bl2 = (n7 & 2) == 0;
            n6 = (n7 & 1) == 0 ? 1 : 0;
            n5 = bl ? n11 : n11 + 1;
            n4 = bl2 ? n12 : n12 + 1;
            int n14 = n6 != 0 ? n13 : n13 + 1;
            double d4 = bl ? d : d - 1.0;
            double d5 = bl2 ? d2 : d2 - 1.0;
            double d6 = n6 != 0 ? d3 : d3 - 1.0;
            dArray[n7] = FuzzedBiomeMagnifier.distanceToCorner(l, n5, n4, n14, d4, d5, d6);
        }
        n7 = 0;
        double d7 = dArray[0];
        for (n6 = 1; n6 < 8; ++n6) {
            if (!(d7 > dArray[n6])) continue;
            n7 = n6;
            d7 = dArray[n6];
        }
        n6 = (n7 & 4) == 0 ? n11 : n11 + 1;
        n5 = (n7 & 2) == 0 ? n12 : n12 + 1;
        n4 = (n7 & 1) == 0 ? n13 : n13 + 1;
        return iBiomeReader.getNoiseBiome(n6, n5, n4);
    }

    private static double distanceToCorner(long l, int n, int n2, int n3, double d, double d2, double d3) {
        long l2 = FastRandom.mix(l, n);
        l2 = FastRandom.mix(l2, n2);
        l2 = FastRandom.mix(l2, n3);
        l2 = FastRandom.mix(l2, n);
        l2 = FastRandom.mix(l2, n2);
        l2 = FastRandom.mix(l2, n3);
        double d4 = FuzzedBiomeMagnifier.randomDouble(l2);
        l2 = FastRandom.mix(l2, l);
        double d5 = FuzzedBiomeMagnifier.randomDouble(l2);
        l2 = FastRandom.mix(l2, l);
        double d6 = FuzzedBiomeMagnifier.randomDouble(l2);
        return FuzzedBiomeMagnifier.square(d3 + d6) + FuzzedBiomeMagnifier.square(d2 + d5) + FuzzedBiomeMagnifier.square(d + d4);
    }

    private static double randomDouble(long l) {
        double d = (double)((int)Math.floorMod(l >> 24, 1024L)) / 1024.0;
        return (d - 0.5) * 0.9;
    }

    private static double square(double d) {
        return d * d;
    }

    private static double[] lambda$new$0() {
        return new double[8];
    }
}

