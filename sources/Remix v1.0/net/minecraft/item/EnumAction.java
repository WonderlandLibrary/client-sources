package net.minecraft.item;

public enum EnumAction
{
    NONE("NONE", 0), 
    EAT("EAT", 1), 
    DRINK("DRINK", 2), 
    BLOCK("BLOCK", 3), 
    BOW("BOW", 4);
    
    private static final EnumAction[] $VALUES;
    
    private EnumAction(final String p_i1910_1_, final int p_i1910_2_) {
    }
    
    static {
        $VALUES = new EnumAction[] { EnumAction.NONE, EnumAction.EAT, EnumAction.DRINK, EnumAction.BLOCK, EnumAction.BOW };
    }
}
