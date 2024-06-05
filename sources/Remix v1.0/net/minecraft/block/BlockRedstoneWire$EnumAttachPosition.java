package net.minecraft.block;

import net.minecraft.util.*;

enum EnumAttachPosition implements IStringSerializable
{
    UP("UP", 0, "up"), 
    SIDE("SIDE", 1, "side"), 
    NONE("NONE", 2, "none");
    
    private static final EnumAttachPosition[] $VALUES;
    private final String field_176820_d;
    
    private EnumAttachPosition(final String p_i45689_1_, final int p_i45689_2_, final String p_i45689_3_) {
        this.field_176820_d = p_i45689_3_;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    @Override
    public String getName() {
        return this.field_176820_d;
    }
    
    static {
        $VALUES = new EnumAttachPosition[] { EnumAttachPosition.UP, EnumAttachPosition.SIDE, EnumAttachPosition.NONE };
    }
}
