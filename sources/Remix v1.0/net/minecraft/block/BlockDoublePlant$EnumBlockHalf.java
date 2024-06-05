package net.minecraft.block;

import net.minecraft.util.*;

enum EnumBlockHalf implements IStringSerializable
{
    UPPER("UPPER", 0), 
    LOWER("LOWER", 1);
    
    private static final EnumBlockHalf[] $VALUES;
    
    private EnumBlockHalf(final String p_i45724_1_, final int p_i45724_2_) {
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    @Override
    public String getName() {
        return (this == EnumBlockHalf.UPPER) ? "upper" : "lower";
    }
    
    static {
        $VALUES = new EnumBlockHalf[] { EnumBlockHalf.UPPER, EnumBlockHalf.LOWER };
    }
}
