package net.minecraft.block.state.pattern;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

static class CacheLoader extends com.google.common.cache.CacheLoader
{
    private final World field_177680_a;
    
    public CacheLoader(final World worldIn) {
        this.field_177680_a = worldIn;
    }
    
    public BlockWorldState func_177679_a(final BlockPos p_177679_1_) {
        return new BlockWorldState(this.field_177680_a, p_177679_1_);
    }
    
    public Object load(final Object p_load_1_) {
        return this.func_177679_a((BlockPos)p_load_1_);
    }
}
