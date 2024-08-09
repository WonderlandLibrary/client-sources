/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.Random;
import net.minecraft.util.FastRandom;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.traits.IPixelTransformer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LazyAreaLayerContext
implements IExtendedNoiseRandom<LazyArea> {
    private final Long2IntLinkedOpenHashMap cache;
    private final int maxCacheSize;
    private final ImprovedNoiseGenerator noise;
    private final long seed;
    private long positionSeed;

    public LazyAreaLayerContext(int n, long l, long l2) {
        this.seed = LazyAreaLayerContext.hash(l, l2);
        this.noise = new ImprovedNoiseGenerator(new Random(l));
        this.cache = new Long2IntLinkedOpenHashMap(16, 0.25f);
        this.cache.defaultReturnValue(Integer.MIN_VALUE);
        this.maxCacheSize = n;
    }

    @Override
    public LazyArea makeArea(IPixelTransformer iPixelTransformer) {
        return new LazyArea(this.cache, this.maxCacheSize, iPixelTransformer);
    }

    @Override
    public LazyArea makeArea(IPixelTransformer iPixelTransformer, LazyArea lazyArea) {
        return new LazyArea(this.cache, Math.min(1024, lazyArea.getmaxCacheSize() * 4), iPixelTransformer);
    }

    @Override
    public LazyArea makeArea(IPixelTransformer iPixelTransformer, LazyArea lazyArea, LazyArea lazyArea2) {
        return new LazyArea(this.cache, Math.min(1024, Math.max(lazyArea.getmaxCacheSize(), lazyArea2.getmaxCacheSize()) * 4), iPixelTransformer);
    }

    @Override
    public void setPosition(long l, long l2) {
        long l3 = this.seed;
        l3 = FastRandom.mix(l3, l);
        l3 = FastRandom.mix(l3, l2);
        l3 = FastRandom.mix(l3, l);
        this.positionSeed = l3 = FastRandom.mix(l3, l2);
    }

    @Override
    public int random(int n) {
        int n2 = (int)Math.floorMod(this.positionSeed >> 24, (long)n);
        this.positionSeed = FastRandom.mix(this.positionSeed, this.seed);
        return n2;
    }

    @Override
    public ImprovedNoiseGenerator getNoiseGenerator() {
        return this.noise;
    }

    private static long hash(long l, long l2) {
        long l3 = FastRandom.mix(l2, l2);
        l3 = FastRandom.mix(l3, l2);
        l3 = FastRandom.mix(l3, l2);
        long l4 = FastRandom.mix(l, l3);
        l4 = FastRandom.mix(l4, l3);
        return FastRandom.mix(l4, l3);
    }

    @Override
    public IArea makeArea(IPixelTransformer iPixelTransformer, IArea iArea, IArea iArea2) {
        return this.makeArea(iPixelTransformer, (LazyArea)iArea, (LazyArea)iArea2);
    }

    @Override
    public IArea makeArea(IPixelTransformer iPixelTransformer, IArea iArea) {
        return this.makeArea(iPixelTransformer, (LazyArea)iArea);
    }

    @Override
    public IArea makeArea(IPixelTransformer iPixelTransformer) {
        return this.makeArea(iPixelTransformer);
    }
}

