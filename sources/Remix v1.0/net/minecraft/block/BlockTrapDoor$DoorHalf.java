package net.minecraft.block;

import net.minecraft.util.*;

public enum DoorHalf implements IStringSerializable
{
    TOP("TOP", 0, "top"), 
    BOTTOM("BOTTOM", 1, "bottom");
    
    private static final DoorHalf[] $VALUES;
    private final String field_176671_c;
    
    private DoorHalf(final String p_i45674_1_, final int p_i45674_2_, final String p_i45674_3_) {
        this.field_176671_c = p_i45674_3_;
    }
    
    @Override
    public String toString() {
        return this.field_176671_c;
    }
    
    @Override
    public String getName() {
        return this.field_176671_c;
    }
    
    static {
        $VALUES = new DoorHalf[] { DoorHalf.TOP, DoorHalf.BOTTOM };
    }
}
