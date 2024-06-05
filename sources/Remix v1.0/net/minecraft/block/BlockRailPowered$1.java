package net.minecraft.block;

import com.google.common.base.*;

static final class BlockRailPowered$1 implements Predicate {
    public boolean func_180133_a(final EnumRailDirection p_180133_1_) {
        return p_180133_1_ != EnumRailDirection.NORTH_EAST && p_180133_1_ != EnumRailDirection.NORTH_WEST && p_180133_1_ != EnumRailDirection.SOUTH_EAST && p_180133_1_ != EnumRailDirection.SOUTH_WEST;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180133_a((EnumRailDirection)p_apply_1_);
    }
}