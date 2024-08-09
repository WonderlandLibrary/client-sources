/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;

public interface IC1Transformer
extends IAreaTransformer1,
IDimOffset1Transformer {
    public int apply(INoiseRandom var1, int var2);

    @Override
    default public int apply(IExtendedNoiseRandom<?> iExtendedNoiseRandom, IArea iArea, int n, int n2) {
        int n3 = iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 1));
        return this.apply(iExtendedNoiseRandom, n3);
    }
}

