package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public abstract class BlockRotatedPillar extends Block
{
    public static final PropertyEnum field_176298_M;
    
    protected BlockRotatedPillar(final Material p_i45425_1_) {
        super(p_i45425_1_);
    }
    
    static {
        field_176298_M = PropertyEnum.create("axis", EnumFacing.Axis.class);
    }
}
