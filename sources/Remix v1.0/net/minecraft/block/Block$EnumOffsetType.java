package net.minecraft.block;

public enum EnumOffsetType
{
    NONE("NONE", 0), 
    XZ("XZ", 1), 
    XYZ("XYZ", 2);
    
    private static final EnumOffsetType[] $VALUES;
    
    private EnumOffsetType(final String p_i45733_1_, final int p_i45733_2_) {
    }
    
    static {
        $VALUES = new EnumOffsetType[] { EnumOffsetType.NONE, EnumOffsetType.XZ, EnumOffsetType.XYZ };
    }
}
