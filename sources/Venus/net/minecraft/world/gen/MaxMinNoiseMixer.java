/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.gen.OctavesNoiseGenerator;

public class MaxMinNoiseMixer {
    private final double field_237208_a_;
    private final OctavesNoiseGenerator field_237209_b_;
    private final OctavesNoiseGenerator field_237210_c_;

    public static MaxMinNoiseMixer func_242930_a(SharedSeedRandom sharedSeedRandom, int n, DoubleList doubleList) {
        return new MaxMinNoiseMixer(sharedSeedRandom, n, doubleList);
    }

    private MaxMinNoiseMixer(SharedSeedRandom sharedSeedRandom, int n, DoubleList doubleList) {
        this.field_237209_b_ = OctavesNoiseGenerator.func_242932_a(sharedSeedRandom, n, doubleList);
        this.field_237210_c_ = OctavesNoiseGenerator.func_242932_a(sharedSeedRandom, n, doubleList);
        int n2 = Integer.MAX_VALUE;
        int n3 = Integer.MIN_VALUE;
        DoubleListIterator doubleListIterator = doubleList.iterator();
        while (doubleListIterator.hasNext()) {
            int n4 = doubleListIterator.nextIndex();
            double d = doubleListIterator.nextDouble();
            if (d == 0.0) continue;
            n2 = Math.min(n2, n4);
            n3 = Math.max(n3, n4);
        }
        this.field_237208_a_ = 0.16666666666666666 / MaxMinNoiseMixer.func_237212_a_(n3 - n2);
    }

    private static double func_237212_a_(int n) {
        return 0.1 * (1.0 + 1.0 / (double)(n + 1));
    }

    public double func_237211_a_(double d, double d2, double d3) {
        double d4 = d * 1.0181268882175227;
        double d5 = d2 * 1.0181268882175227;
        double d6 = d3 * 1.0181268882175227;
        return (this.field_237209_b_.func_205563_a(d, d2, d3) + this.field_237210_c_.func_205563_a(d4, d5, d6)) * this.field_237208_a_;
    }
}

