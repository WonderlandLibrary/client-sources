/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.ImprovedNoiseGenerator;

public class OctavesNoiseGenerator
implements INoiseGenerator {
    private final ImprovedNoiseGenerator[] octaves;
    private final DoubleList field_242931_b;
    private final double field_227460_b_;
    private final double field_227461_c_;

    public OctavesNoiseGenerator(SharedSeedRandom sharedSeedRandom, IntStream intStream) {
        this(sharedSeedRandom, intStream.boxed().collect(ImmutableList.toImmutableList()));
    }

    public OctavesNoiseGenerator(SharedSeedRandom sharedSeedRandom, List<Integer> list) {
        this(sharedSeedRandom, new IntRBTreeSet(list));
    }

    public static OctavesNoiseGenerator func_242932_a(SharedSeedRandom sharedSeedRandom, int n, DoubleList doubleList) {
        return new OctavesNoiseGenerator(sharedSeedRandom, Pair.of(n, doubleList));
    }

    private static Pair<Integer, DoubleList> func_242933_a(IntSortedSet intSortedSet) {
        int n;
        if (intSortedSet.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        }
        int n2 = -intSortedSet.firstInt();
        int n3 = n2 + (n = intSortedSet.lastInt()) + 1;
        if (n3 < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
        }
        DoubleArrayList doubleArrayList = new DoubleArrayList(new double[n3]);
        IntBidirectionalIterator intBidirectionalIterator = intSortedSet.iterator();
        while (intBidirectionalIterator.hasNext()) {
            int n4 = intBidirectionalIterator.nextInt();
            doubleArrayList.set(n4 + n2, 1.0);
        }
        return Pair.of(-n2, doubleArrayList);
    }

    private OctavesNoiseGenerator(SharedSeedRandom sharedSeedRandom, IntSortedSet intSortedSet) {
        this(sharedSeedRandom, OctavesNoiseGenerator.func_242933_a(intSortedSet));
    }

    private OctavesNoiseGenerator(SharedSeedRandom sharedSeedRandom, Pair<Integer, DoubleList> pair) {
        double d;
        int n = pair.getFirst();
        this.field_242931_b = pair.getSecond();
        ImprovedNoiseGenerator improvedNoiseGenerator = new ImprovedNoiseGenerator(sharedSeedRandom);
        int n2 = this.field_242931_b.size();
        int n3 = -n;
        this.octaves = new ImprovedNoiseGenerator[n2];
        if (n3 >= 0 && n3 < n2 && (d = this.field_242931_b.getDouble(n3)) != 0.0) {
            this.octaves[n3] = improvedNoiseGenerator;
        }
        for (int i = n3 - 1; i >= 0; --i) {
            if (i < n2) {
                double d2 = this.field_242931_b.getDouble(i);
                if (d2 != 0.0) {
                    this.octaves[i] = new ImprovedNoiseGenerator(sharedSeedRandom);
                    continue;
                }
                sharedSeedRandom.skip(262);
                continue;
            }
            sharedSeedRandom.skip(262);
        }
        if (n3 < n2 - 1) {
            long l = (long)(improvedNoiseGenerator.func_215456_a(0.0, 0.0, 0.0, 0.0, 0.0) * 9.223372036854776E18);
            SharedSeedRandom sharedSeedRandom2 = new SharedSeedRandom(l);
            for (int i = n3 + 1; i < n2; ++i) {
                if (i >= 0) {
                    double d3 = this.field_242931_b.getDouble(i);
                    if (d3 != 0.0) {
                        this.octaves[i] = new ImprovedNoiseGenerator(sharedSeedRandom2);
                        continue;
                    }
                    sharedSeedRandom2.skip(262);
                    continue;
                }
                sharedSeedRandom2.skip(262);
            }
        }
        this.field_227461_c_ = Math.pow(2.0, -n3);
        this.field_227460_b_ = Math.pow(2.0, n2 - 1) / (Math.pow(2.0, n2) - 1.0);
    }

    public double func_205563_a(double d, double d2, double d3) {
        return this.getValue(d, d2, d3, 0.0, 0.0, true);
    }

    public double getValue(double d, double d2, double d3, double d4, double d5, boolean bl) {
        double d6 = 0.0;
        double d7 = this.field_227461_c_;
        double d8 = this.field_227460_b_;
        for (int i = 0; i < this.octaves.length; ++i) {
            ImprovedNoiseGenerator improvedNoiseGenerator = this.octaves[i];
            if (improvedNoiseGenerator != null) {
                d6 += this.field_242931_b.getDouble(i) * improvedNoiseGenerator.func_215456_a(OctavesNoiseGenerator.maintainPrecision(d * d7), bl ? -improvedNoiseGenerator.yCoord : OctavesNoiseGenerator.maintainPrecision(d2 * d7), OctavesNoiseGenerator.maintainPrecision(d3 * d7), d4 * d7, d5 * d7) * d8;
            }
            d7 *= 2.0;
            d8 /= 2.0;
        }
        return d6;
    }

    @Nullable
    public ImprovedNoiseGenerator getOctave(int n) {
        return this.octaves[this.octaves.length - 1 - n];
    }

    public static double maintainPrecision(double d) {
        return d - (double)MathHelper.lfloor(d / 3.3554432E7 + 0.5) * 3.3554432E7;
    }

    @Override
    public double noiseAt(double d, double d2, double d3, double d4) {
        return this.getValue(d, d2, 0.0, d3, d4, true);
    }
}

