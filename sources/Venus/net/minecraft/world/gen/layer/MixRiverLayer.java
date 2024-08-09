/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum MixRiverLayer implements IAreaTransformer2,
IDimOffset0Transformer
{
    INSTANCE;


    @Override
    public int apply(INoiseRandom iNoiseRandom, IArea iArea, IArea iArea2, int n, int n2) {
        int n3 = iArea.getValue(this.getOffsetX(n), this.getOffsetZ(n2));
        int n4 = iArea2.getValue(this.getOffsetX(n), this.getOffsetZ(n2));
        if (LayerUtil.isOcean(n3)) {
            return n3;
        }
        if (n4 == 7) {
            if (n3 == 12) {
                return 0;
            }
            return n3 != 14 && n3 != 15 ? n4 & 0xFF : 15;
        }
        return n3;
    }
}

