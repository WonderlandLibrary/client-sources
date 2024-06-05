package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumDoorHalf implements IStringSerializable
{
    UPPER("UPPER", 0), 
    LOWER("LOWER", 1);
    
    private static final EnumDoorHalf[] $VALUES;
    
    private EnumDoorHalf(final String p_i45726_1_, final int p_i45726_2_) {
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    @Override
    public String getName() {
        return (this == EnumDoorHalf.UPPER) ? "upper" : "lower";
    }
    
    static {
        $VALUES = new EnumDoorHalf[] { EnumDoorHalf.UPPER, EnumDoorHalf.LOWER };
    }
}
