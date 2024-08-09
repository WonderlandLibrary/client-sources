/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;

public interface ICastleTransformer
extends IAreaTransformer1,
IDimOffset1Transformer {
    public int apply(INoiseRandom var1, int var2, int var3, int var4, int var5, int var6);

    @Override
    default public int apply(IExtendedNoiseRandom<?> iExtendedNoiseRandom, IArea iArea, int n, int n2) {
        return this.apply(iExtendedNoiseRandom, iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 0)), iArea.getValue(this.getOffsetX(n + 2), this.getOffsetZ(n2 + 1)), iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 2)), iArea.getValue(this.getOffsetX(n + 0), this.getOffsetZ(n2 + 1)), iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 1)));
    }
}

