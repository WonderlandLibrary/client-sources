package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;

static class StaticCloneData
{
    public final BlockPos field_179537_a;
    public final IBlockState field_179535_b;
    public final NBTTagCompound field_179536_c;
    
    public StaticCloneData(final BlockPos p_i46037_1_, final IBlockState p_i46037_2_, final NBTTagCompound p_i46037_3_) {
        this.field_179537_a = p_i46037_1_;
        this.field_179535_b = p_i46037_2_;
        this.field_179536_c = p_i46037_3_;
    }
}
