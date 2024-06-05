package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumBlockHalf implements IStringSerializable
{
    TOP("TOP", 0, "top"), 
    BOTTOM("BOTTOM", 1, "bottom");
    
    private static final EnumBlockHalf[] $VALUES;
    private final String halfName;
    
    private EnumBlockHalf(final String p_i45713_1_, final int p_i45713_2_, final String p_i45713_3_) {
        this.halfName = p_i45713_3_;
    }
    
    @Override
    public String toString() {
        return this.halfName;
    }
    
    @Override
    public String getName() {
        return this.halfName;
    }
    
    static {
        $VALUES = new EnumBlockHalf[] { EnumBlockHalf.TOP, EnumBlockHalf.BOTTOM };
    }
}
