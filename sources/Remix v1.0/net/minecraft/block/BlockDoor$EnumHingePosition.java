package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumHingePosition implements IStringSerializable
{
    LEFT("LEFT", 0), 
    RIGHT("RIGHT", 1);
    
    private static final EnumHingePosition[] $VALUES;
    
    private EnumHingePosition(final String p_i45725_1_, final int p_i45725_2_) {
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    @Override
    public String getName() {
        return (this == EnumHingePosition.LEFT) ? "left" : "right";
    }
    
    static {
        $VALUES = new EnumHingePosition[] { EnumHingePosition.LEFT, EnumHingePosition.RIGHT };
    }
}
