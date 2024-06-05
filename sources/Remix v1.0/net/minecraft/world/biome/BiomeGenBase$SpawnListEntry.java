package net.minecraft.world.biome;

import net.minecraft.util.*;

public static class SpawnListEntry extends WeightedRandom.Item
{
    public Class entityClass;
    public int minGroupCount;
    public int maxGroupCount;
    
    public SpawnListEntry(final Class p_i1970_1_, final int p_i1970_2_, final int p_i1970_3_, final int p_i1970_4_) {
        super(p_i1970_2_);
        this.entityClass = p_i1970_1_;
        this.minGroupCount = p_i1970_3_;
        this.maxGroupCount = p_i1970_4_;
    }
    
    @Override
    public String toString() {
        return this.entityClass.getSimpleName() + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
    }
}
