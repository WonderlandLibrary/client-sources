package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumPistonType implements IStringSerializable
{
    DEFAULT("DEFAULT", 0, "normal"), 
    STICKY("STICKY", 1, "sticky");
    
    private static final EnumPistonType[] $VALUES;
    private final String field_176714_c;
    
    private EnumPistonType(final String p_i45666_1_, final int p_i45666_2_, final String p_i45666_3_) {
        this.field_176714_c = p_i45666_3_;
    }
    
    @Override
    public String toString() {
        return this.field_176714_c;
    }
    
    @Override
    public String getName() {
        return this.field_176714_c;
    }
    
    static {
        $VALUES = new EnumPistonType[] { EnumPistonType.DEFAULT, EnumPistonType.STICKY };
    }
}
