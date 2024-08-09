/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum MixOceansLayer implements IAreaTransformer2,
IDimOffset0Transformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, IArea iArea, IArea iArea2, int n, int n2) {
        int n3 = iArea.getValue(this.getOffsetX(n), this.getOffsetZ(n2));
        int n4 = iArea2.getValue(this.getOffsetX(n), this.getOffsetZ(n2));
        if (!LayerUtil.isOcean(n3)) {
            return n3;
        }
        int n5 = 8;
        int n6 = 4;
        for (int i = -8; i <= 8; i += 4) {
            for (int j = -8; j <= 8; j += 4) {
                int n7 = iArea.getValue(this.getOffsetX(n + i), this.getOffsetZ(n2 + j));
                if (LayerUtil.isOcean(n7)) continue;
                if (n4 == 44) {
                    return 0;
                }
                if (n4 != 10) continue;
                return 1;
            }
        }
        if (n3 == 24) {
            if (n4 == 45) {
                return 1;
            }
            if (n4 == 0) {
                return 1;
            }
            if (n4 == 46) {
                return 0;
            }
            if (n4 == 10) {
                return 1;
            }
        }
        return n4;
    }
}

