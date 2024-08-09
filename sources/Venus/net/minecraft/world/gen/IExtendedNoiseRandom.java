/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IPixelTransformer;

public interface IExtendedNoiseRandom<R extends IArea>
extends INoiseRandom {
    public void setPosition(long var1, long var3);

    public R makeArea(IPixelTransformer var1);

    default public R makeArea(IPixelTransformer iPixelTransformer, R r) {
        return this.makeArea(iPixelTransformer);
    }

    default public R makeArea(IPixelTransformer iPixelTransformer, R r, R r2) {
        return this.makeArea(iPixelTransformer);
    }

    default public int pickRandom(int n, int n2) {
        return this.random(2) == 0 ? n : n2;
    }

    default public int pickRandom(int n, int n2, int n3, int n4) {
        int n5 = this.random(4);
        if (n5 == 0) {
            return n;
        }
        if (n5 == 1) {
            return n2;
        }
        return n5 == 2 ? n3 : n4;
    }
}

