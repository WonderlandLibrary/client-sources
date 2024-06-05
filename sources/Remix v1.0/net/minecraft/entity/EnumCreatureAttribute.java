package net.minecraft.entity;

public enum EnumCreatureAttribute
{
    UNDEFINED("UNDEFINED", 0), 
    UNDEAD("UNDEAD", 1), 
    ARTHROPOD("ARTHROPOD", 2);
    
    private static final EnumCreatureAttribute[] $VALUES;
    
    private EnumCreatureAttribute(final String p_i1597_1_, final int p_i1597_2_) {
    }
    
    static {
        $VALUES = new EnumCreatureAttribute[] { EnumCreatureAttribute.UNDEFINED, EnumCreatureAttribute.UNDEAD, EnumCreatureAttribute.ARTHROPOD };
    }
}
