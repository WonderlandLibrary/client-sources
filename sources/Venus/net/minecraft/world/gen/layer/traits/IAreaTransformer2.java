/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.traits.IDimTransformer;

public interface IAreaTransformer2
extends IDimTransformer {
    default public <R extends IArea> IAreaFactory<R> apply(IExtendedNoiseRandom<R> iExtendedNoiseRandom, IAreaFactory<R> iAreaFactory, IAreaFactory<R> iAreaFactory2) {
        return () -> this.lambda$apply$1(iAreaFactory, iAreaFactory2, iExtendedNoiseRandom);
    }

    public int apply(INoiseRandom var1, IArea var2, IArea var3, int var4, int var5);

    private IArea lambda$apply$1(IAreaFactory iAreaFactory, IAreaFactory iAreaFactory2, IExtendedNoiseRandom iExtendedNoiseRandom) {
        Object a = iAreaFactory.make();
        Object a2 = iAreaFactory2.make();
        return iExtendedNoiseRandom.makeArea((arg_0, arg_1) -> this.lambda$apply$0(iExtendedNoiseRandom, a, a2, arg_0, arg_1), a, a2);
    }

    private int lambda$apply$0(IExtendedNoiseRandom iExtendedNoiseRandom, IArea iArea, IArea iArea2, int n, int n2) {
        iExtendedNoiseRandom.setPosition(n, n2);
        return this.apply(iExtendedNoiseRandom, iArea, iArea2, n, n2);
    }
}

