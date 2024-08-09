/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.SimplexNoiseGenerator;

public class PerlinNoiseGenerator
implements INoiseGenerator {
    private final SimplexNoiseGenerator[] noiseLevels;
    private final double field_227462_b_;
    private final double field_227463_c_;

    public PerlinNoiseGenerator(SharedSeedRandom sharedSeedRandom, IntStream intStream) {
        this(sharedSeedRandom, intStream.boxed().collect(ImmutableList.toImmutableList()));
    }

    public PerlinNoiseGenerator(SharedSeedRandom sharedSeedRandom, List<Integer> list) {
        this(sharedSeedRandom, new IntRBTreeSet(list));
    }

    private PerlinNoiseGenerator(SharedSeedRandom sharedSeedRandom, IntSortedSet intSortedSet) {
        int n;
        if (intSortedSet.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }
        int n2 = -intSortedSet.firstInt();
        int n3 = n2 + (n = intSortedSet.lastInt()) + 1;
        if (n3 < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
        }
        SimplexNoiseGenerator simplexNoiseGenerator = new SimplexNoiseGenerator(sharedSeedRandom);
        int n4 = n;
        this.noiseLevels = new SimplexNoiseGenerator[n3];
        if (n >= 0 && n < n3 && intSortedSet.contains(0)) {
            this.noiseLevels[n] = simplexNoiseGenerator;
        }
        for (int i = n + 1; i < n3; ++i) {
            if (i >= 0 && intSortedSet.contains(n4 - i)) {
                this.noiseLevels[i] = new SimplexNoiseGenerator(sharedSeedRandom);
                continue;
            }
            sharedSeedRandom.skip(262);
        }
        if (n > 0) {
            long l = (long)(simplexNoiseGenerator.func_227464_a_(simplexNoiseGenerator.xo, simplexNoiseGenerator.yo, simplexNoiseGenerator.zo) * 9.223372036854776E18);
            SharedSeedRandom sharedSeedRandom2 = new SharedSeedRandom(l);
            for (int i = n4 - 1; i >= 0; --i) {
                if (i < n3 && intSortedSet.contains(n4 - i)) {
                    this.noiseLevels[i] = new SimplexNoiseGenerator(sharedSeedRandom2);
                    continue;
                }
                sharedSeedRandom2.skip(262);
            }
        }
        this.field_227463_c_ = Math.pow(2.0, n);
        this.field_227462_b_ = 1.0 / (Math.pow(2.0, n3) - 1.0);
    }

    public double noiseAt(double d, double d2, boolean bl) {
        double d3 = 0.0;
        double d4 = this.field_227463_c_;
        double d5 = this.field_227462_b_;
        for (SimplexNoiseGenerator simplexNoiseGenerator : this.noiseLevels) {
            if (simplexNoiseGenerator != null) {
                d3 += simplexNoiseGenerator.getValue(d * d4 + (bl ? simplexNoiseGenerator.xo : 0.0), d2 * d4 + (bl ? simplexNoiseGenerator.yo : 0.0)) * d5;
            }
            d4 /= 2.0;
            d5 *= 2.0;
        }
        return d3;
    }

    @Override
    public double noiseAt(double d, double d2, double d3, double d4) {
        return this.noiseAt(d, d2, false) * 0.55;
    }
}

