package net.minecraft.world;

public enum ValueType
{
    ANY_VALUE("ANY_VALUE", 0, "ANY_VALUE", 0), 
    BOOLEAN_VALUE("BOOLEAN_VALUE", 1, "BOOLEAN_VALUE", 1), 
    NUMERICAL_VALUE("NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2);
    
    private static final ValueType[] $VALUES;
    
    private ValueType(final String p_i46394_1_, final int p_i46394_2_, final String p_i45750_1_, final int p_i45750_2_) {
    }
    
    static {
        $VALUES = new ValueType[] { ValueType.ANY_VALUE, ValueType.BOOLEAN_VALUE, ValueType.NUMERICAL_VALUE };
    }
}
