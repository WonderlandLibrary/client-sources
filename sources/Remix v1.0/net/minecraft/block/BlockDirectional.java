package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import com.google.common.base.*;

public abstract class BlockDirectional extends Block
{
    public static final PropertyDirection AGE;
    
    protected BlockDirectional(final Material p_i45401_1_) {
        super(p_i45401_1_);
    }
    
    static {
        AGE = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
}
