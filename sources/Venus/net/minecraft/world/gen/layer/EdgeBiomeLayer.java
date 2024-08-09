/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum EdgeBiomeLayer implements ICastleTransformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
        int[] nArray = new int[1];
        if (!(this.func_242935_a(nArray, n5) || this.replaceBiomeEdge(nArray, n, n2, n3, n4, n5, 38, 0) || this.replaceBiomeEdge(nArray, n, n2, n3, n4, n5, 39, 0) || this.replaceBiomeEdge(nArray, n, n2, n3, n4, n5, 32, 0))) {
            if (n5 != 2 || n != 12 && n2 != 12 && n4 != 12 && n3 != 12) {
                if (n5 == 6) {
                    if (n == 2 || n2 == 2 || n4 == 2 || n3 == 2 || n == 30 || n2 == 30 || n4 == 30 || n3 == 30 || n == 12 || n2 == 12 || n4 == 12 || n3 == 12) {
                        return 0;
                    }
                    if (n == 21 || n3 == 21 || n2 == 21 || n4 == 21 || n == 168 || n3 == 168 || n2 == 168 || n4 == 168) {
                        return 0;
                    }
                }
                return n5;
            }
            return 1;
        }
        return nArray[0];
    }

    private boolean func_242935_a(int[] nArray, int n) {
        if (!LayerUtil.areBiomesSimilar(n, 3)) {
            return true;
        }
        nArray[0] = n;
        return false;
    }

    private boolean replaceBiomeEdge(int[] nArray, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n5 != n6) {
            return true;
        }
        nArray[0] = LayerUtil.areBiomesSimilar(n, n6) && LayerUtil.areBiomesSimilar(n2, n6) && LayerUtil.areBiomesSimilar(n4, n6) && LayerUtil.areBiomesSimilar(n3, n6) ? n5 : n7;
        return false;
    }
}

