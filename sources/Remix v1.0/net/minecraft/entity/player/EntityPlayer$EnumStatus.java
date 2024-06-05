package net.minecraft.entity.player;

public enum EnumStatus
{
    OK("OK", 0), 
    NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1), 
    NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2), 
    TOO_FAR_AWAY("TOO_FAR_AWAY", 3), 
    OTHER_PROBLEM("OTHER_PROBLEM", 4), 
    NOT_SAFE("NOT_SAFE", 5);
    
    private static final EnumStatus[] $VALUES;
    
    private EnumStatus(final String p_i1751_1_, final int p_i1751_2_) {
    }
    
    static {
        $VALUES = new EnumStatus[] { EnumStatus.OK, EnumStatus.NOT_POSSIBLE_HERE, EnumStatus.NOT_POSSIBLE_NOW, EnumStatus.TOO_FAR_AWAY, EnumStatus.OTHER_PROBLEM, EnumStatus.NOT_SAFE };
    }
}
