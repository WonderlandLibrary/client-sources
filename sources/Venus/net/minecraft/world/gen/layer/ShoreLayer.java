/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum ShoreLayer implements ICastleTransformer
{
    INSTANCE;

    private static final IntSet field_242942_b;
    private static final IntSet field_242943_c;

    @Override
    public int apply(INoiseRandom iNoiseRandom, int n, int n2, int n3, int n4, int n5) {
        if (n5 == 14) {
            if (LayerUtil.isShallowOcean(n) || LayerUtil.isShallowOcean(n2) || LayerUtil.isShallowOcean(n3) || LayerUtil.isShallowOcean(n4)) {
                return 0;
            }
        } else if (field_242943_c.contains(n5)) {
            if (!(ShoreLayer.isJungleCompatible(n) && ShoreLayer.isJungleCompatible(n2) && ShoreLayer.isJungleCompatible(n3) && ShoreLayer.isJungleCompatible(n4))) {
                return 0;
            }
            if (LayerUtil.isOcean(n) || LayerUtil.isOcean(n2) || LayerUtil.isOcean(n3) || LayerUtil.isOcean(n4)) {
                return 1;
            }
        } else if (n5 != 3 && n5 != 34 && n5 != 20) {
            if (field_242942_b.contains(n5) ? !LayerUtil.isOcean(n5) && (LayerUtil.isOcean(n) || LayerUtil.isOcean(n2) || LayerUtil.isOcean(n3) || LayerUtil.isOcean(n4)) : (n5 != 37 && n5 != 38 ? !LayerUtil.isOcean(n5) && n5 != 7 && n5 != 6 && (LayerUtil.isOcean(n) || LayerUtil.isOcean(n2) || LayerUtil.isOcean(n3) || LayerUtil.isOcean(n4)) : !LayerUtil.isOcean(n) && !LayerUtil.isOcean(n2) && !LayerUtil.isOcean(n3) && !LayerUtil.isOcean(n4) && (!this.isMesa(n) || !this.isMesa(n2) || !this.isMesa(n3) || !this.isMesa(n4)))) {
                return 1;
            }
        } else if (!LayerUtil.isOcean(n5) && (LayerUtil.isOcean(n) || LayerUtil.isOcean(n2) || LayerUtil.isOcean(n3) || LayerUtil.isOcean(n4))) {
            return 0;
        }
        return n5;
    }

    private static boolean isJungleCompatible(int n) {
        return field_242943_c.contains(n) || n == 4 || n == 5 || LayerUtil.isOcean(n);
    }

    private boolean isMesa(int n) {
        return n == 37 || n == 38 || n == 39 || n == 165 || n == 166 || n == 167;
    }

    static {
        field_242942_b = new IntOpenHashSet(new int[]{26, 11, 12, 13, 140, 30, 31, 158, 10});
        field_242943_c = new IntOpenHashSet(new int[]{168, 169, 21, 22, 23, 149, 151});
    }
}

