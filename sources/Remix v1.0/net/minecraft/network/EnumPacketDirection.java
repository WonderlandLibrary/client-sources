package net.minecraft.network;

public enum EnumPacketDirection
{
    SERVERBOUND("SERVERBOUND", 0), 
    CLIENTBOUND("CLIENTBOUND", 1);
    
    private static final EnumPacketDirection[] $VALUES;
    
    private EnumPacketDirection(final String p_i45995_1_, final int p_i45995_2_) {
    }
    
    static {
        $VALUES = new EnumPacketDirection[] { EnumPacketDirection.SERVERBOUND, EnumPacketDirection.CLIENTBOUND };
    }
}
