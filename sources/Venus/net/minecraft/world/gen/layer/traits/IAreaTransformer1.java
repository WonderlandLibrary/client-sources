/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.traits.IDimTransformer;

public interface IAreaTransformer1
extends IDimTransformer {
    default public <R extends IArea> IAreaFactory<R> apply(IExtendedNoiseRandom<R> iExtendedNoiseRandom, IAreaFactory<R> iAreaFactory) {
        return () -> this.lambda$apply$1(iAreaFactory, iExtendedNoiseRandom);
    }

    public int apply(IExtendedNoiseRandom<?> var1, IArea var2, int var3, int var4);

    private IArea lambda$apply$1(IAreaFactory iAreaFactory, IExtendedNoiseRandom iExtendedNoiseRandom) {
        Object a = iAreaFactory.make();
        return iExtendedNoiseRandom.makeArea((arg_0, arg_1) -> this.lambda$apply$0(iExtendedNoiseRandom, a, arg_0, arg_1), a);
    }

    private int lambda$apply$0(IExtendedNoiseRandom iExtendedNoiseRandom, IArea iArea, int n, int n2) {
        iExtendedNoiseRandom.setPosition(n, n2);
        return this.apply(iExtendedNoiseRandom, iArea, n, n2);
    }
}

