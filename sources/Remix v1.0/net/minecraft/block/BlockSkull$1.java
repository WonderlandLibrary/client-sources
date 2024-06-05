package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;

static final class BlockSkull$1 implements Predicate {
    public boolean func_177062_a(final BlockWorldState p_177062_1_) {
        return p_177062_1_.func_177509_a().getBlock() == Blocks.skull && p_177062_1_.func_177507_b() instanceof TileEntitySkull && ((TileEntitySkull)p_177062_1_.func_177507_b()).getSkullType() == 1;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_177062_a((BlockWorldState)p_apply_1_);
    }
}