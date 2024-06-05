package net.minecraft.util;

public enum MovingObjectType
{
    MISS("MISS", 0), 
    BLOCK("BLOCK", 1), 
    ENTITY("ENTITY", 2);
    
    private static final MovingObjectType[] $VALUES;
    
    private MovingObjectType(final String p_i2302_1_, final int p_i2302_2_) {
    }
    
    static {
        $VALUES = new MovingObjectType[] { MovingObjectType.MISS, MovingObjectType.BLOCK, MovingObjectType.ENTITY };
    }
}
