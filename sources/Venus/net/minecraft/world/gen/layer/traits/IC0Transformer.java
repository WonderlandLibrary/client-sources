/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public interface IC0Transformer
extends IAreaTransformer1,
IDimOffset0Transformer {
    public int apply(INoiseRandom var1, int var2);

    @Override
    default public int apply(IExtendedNoiseRandom<?> iExtendedNoiseRandom, IArea iArea, int n, int n2) {
        return this.apply(iExtendedNoiseRandom, iArea.getValue(this.getOffsetX(n), this.getOffsetZ(n2)));
    }
}

