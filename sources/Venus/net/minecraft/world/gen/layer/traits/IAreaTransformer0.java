/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;

public interface IAreaTransformer0 {
    default public <R extends IArea> IAreaFactory<R> apply(IExtendedNoiseRandom<R> iExtendedNoiseRandom) {
        return () -> this.lambda$apply$1(iExtendedNoiseRandom);
    }

    public int apply(INoiseRandom var1, int var2, int var3);

    private IArea lambda$apply$1(IExtendedNoiseRandom iExtendedNoiseRandom) {
        return iExtendedNoiseRandom.makeArea((arg_0, arg_1) -> this.lambda$apply$0(iExtendedNoiseRandom, arg_0, arg_1));
    }

    private int lambda$apply$0(IExtendedNoiseRandom iExtendedNoiseRandom, int n, int n2) {
        iExtendedNoiseRandom.setPosition(n, n2);
        return this.apply(iExtendedNoiseRandom, n, n2);
    }
}

