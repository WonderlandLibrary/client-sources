package net.minecraft.world.chunk;

public enum EnumCreateEntityType
{
    IMMEDIATE("IMMEDIATE", 0), 
    QUEUED("QUEUED", 1), 
    CHECK("CHECK", 2);
    
    private static final EnumCreateEntityType[] $VALUES;
    
    private EnumCreateEntityType(final String p_i45642_1_, final int p_i45642_2_) {
    }
    
    static {
        $VALUES = new EnumCreateEntityType[] { EnumCreateEntityType.IMMEDIATE, EnumCreateEntityType.QUEUED, EnumCreateEntityType.CHECK };
    }
}
