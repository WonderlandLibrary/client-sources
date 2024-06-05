package net.minecraft.world.gen.structure;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import java.util.*;

public abstract static class BlockSelector
{
    protected IBlockState field_151562_a;
    
    protected BlockSelector() {
        this.field_151562_a = Blocks.air.getDefaultState();
    }
    
    public abstract void selectBlocks(final Random p0, final int p1, final int p2, final int p3, final boolean p4);
    
    public IBlockState func_180780_a() {
        return this.field_151562_a;
    }
}
