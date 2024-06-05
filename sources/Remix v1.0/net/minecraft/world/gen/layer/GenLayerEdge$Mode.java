package net.minecraft.world.gen.layer;

public enum Mode
{
    COOL_WARM("COOL_WARM", 0), 
    HEAT_ICE("HEAT_ICE", 1), 
    SPECIAL("SPECIAL", 2);
    
    private static final Mode[] $VALUES;
    
    private Mode(final String p_i45473_1_, final int p_i45473_2_) {
    }
    
    static {
        $VALUES = new Mode[] { Mode.COOL_WARM, Mode.HEAT_ICE, Mode.SPECIAL };
    }
}
