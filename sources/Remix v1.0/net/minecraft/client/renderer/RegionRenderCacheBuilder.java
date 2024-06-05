package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class RegionRenderCacheBuilder
{
    private final WorldRenderer[] field_179040_a;
    
    public RegionRenderCacheBuilder() {
        (this.field_179040_a = new WorldRenderer[EnumWorldBlockLayer.values().length])[EnumWorldBlockLayer.SOLID.ordinal()] = new WorldRenderer(2097152);
        this.field_179040_a[EnumWorldBlockLayer.CUTOUT.ordinal()] = new WorldRenderer(131072);
        this.field_179040_a[EnumWorldBlockLayer.CUTOUT_MIPPED.ordinal()] = new WorldRenderer(131072);
        this.field_179040_a[EnumWorldBlockLayer.TRANSLUCENT.ordinal()] = new WorldRenderer(262144);
    }
    
    public WorldRenderer func_179038_a(final EnumWorldBlockLayer p_179038_1_) {
        return this.field_179040_a[p_179038_1_.ordinal()];
    }
    
    public WorldRenderer func_179039_a(final int p_179039_1_) {
        return this.field_179040_a[p_179039_1_];
    }
}
