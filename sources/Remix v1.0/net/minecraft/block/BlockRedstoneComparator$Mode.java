package net.minecraft.block;

import net.minecraft.util.*;

public enum Mode implements IStringSerializable
{
    COMPARE("COMPARE", 0, "compare"), 
    SUBTRACT("SUBTRACT", 1, "subtract");
    
    private static final Mode[] $VALUES;
    private final String field_177041_c;
    
    private Mode(final String p_i45731_1_, final int p_i45731_2_, final String p_i45731_3_) {
        this.field_177041_c = p_i45731_3_;
    }
    
    @Override
    public String toString() {
        return this.field_177041_c;
    }
    
    @Override
    public String getName() {
        return this.field_177041_c;
    }
    
    static {
        $VALUES = new Mode[] { Mode.COMPARE, Mode.SUBTRACT };
    }
}
