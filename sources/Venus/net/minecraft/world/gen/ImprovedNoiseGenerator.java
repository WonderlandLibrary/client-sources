/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.SimplexNoiseGenerator;

public final class ImprovedNoiseGenerator {
    private final byte[] permutations;
    public final double xCoord;
    public final double yCoord;
    public final double zCoord;

    public ImprovedNoiseGenerator(Random random2) {
        int n;
        this.xCoord = random2.nextDouble() * 256.0;
        this.yCoord = random2.nextDouble() * 256.0;
        this.zCoord = random2.nextDouble() * 256.0;
        this.permutations = new byte[256];
        for (n = 0; n < 256; ++n) {
            this.permutations[n] = (byte)n;
        }
        for (n = 0; n < 256; ++n) {
            int n2 = random2.nextInt(256 - n);
            byte by = this.permutations[n];
            this.permutations[n] = this.permutations[n + n2];
            this.permutations[n + n2] = by;
        }
    }

    public double func_215456_a(double d, double d2, double d3, double d4, double d5) {
        double d6;
        double d7 = d + this.xCoord;
        double d8 = d2 + this.yCoord;
        double d9 = d3 + this.zCoord;
        int n = MathHelper.floor(d7);
        int n2 = MathHelper.floor(d8);
        int n3 = MathHelper.floor(d9);
        double d10 = d7 - (double)n;
        double d11 = d8 - (double)n2;
        double d12 = d9 - (double)n3;
        double d13 = MathHelper.perlinFade(d10);
        double d14 = MathHelper.perlinFade(d11);
        double d15 = MathHelper.perlinFade(d12);
        if (d4 != 0.0) {
            double d16 = Math.min(d5, d11);
            d6 = (double)MathHelper.floor(d16 / d4) * d4;
        } else {
            d6 = 0.0;
        }
        return this.func_215459_a(n, n2, n3, d10, d11 - d6, d12, d13, d14, d15);
    }

    private static double dotGrad(int n, double d, double d2, double d3) {
        int n2 = n & 0xF;
        return SimplexNoiseGenerator.processGrad(SimplexNoiseGenerator.GRADS[n2], d, d2, d3);
    }

    private int getPermutValue(int n) {
        return this.permutations[n & 0xFF] & 0xFF;
    }

    public double func_215459_a(int n, int n2, int n3, double d, double d2, double d3, double d4, double d5, double d6) {
        int n4 = this.getPermutValue(n) + n2;
        int n5 = this.getPermutValue(n4) + n3;
        int n6 = this.getPermutValue(n4 + 1) + n3;
        int n7 = this.getPermutValue(n + 1) + n2;
        int n8 = this.getPermutValue(n7) + n3;
        int n9 = this.getPermutValue(n7 + 1) + n3;
        double d7 = ImprovedNoiseGenerator.dotGrad(this.getPermutValue(n5), d, d2, d3);
        double d8 = ImprovedNoiseGenerator.dotGrad(this.getPermutValue(n8), d - 1.0, d2, d3);
        double d9 = ImprovedNoiseGenerator.dotGrad(this.getPermutValue(n6), d, d2 - 1.0, d3);
        double d10 = ImprovedNoiseGenerator.dotGrad(this.getPermutValue(n9), d - 1.0, d2 - 1.0, d3);
        double d11 = ImprovedNoiseGenerator.dotGrad(this.getPermutValue(n5 + 1), d, d2, d3 - 1.0);
        double d12 = ImprovedNoiseGenerator.dotGrad(this.getPermutValue(n8 + 1), d - 1.0, d2, d3 - 1.0);
        double d13 = ImprovedNoiseGenerator.dotGrad(this.getPermutValue(n6 + 1), d, d2 - 1.0, d3 - 1.0);
        double d14 = ImprovedNoiseGenerator.dotGrad(this.getPermutValue(n9 + 1), d - 1.0, d2 - 1.0, d3 - 1.0);
        return MathHelper.lerp3(d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14);
    }
}

