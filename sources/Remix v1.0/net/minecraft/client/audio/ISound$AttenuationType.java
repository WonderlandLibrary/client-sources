package net.minecraft.client.audio;

public enum AttenuationType
{
    NONE("NONE", 0, 0), 
    LINEAR("LINEAR", 1, 2);
    
    private static final AttenuationType[] $VALUES;
    private final int type;
    
    private AttenuationType(final String p_i45110_1_, final int p_i45110_2_, final int p_i45110_3_) {
        this.type = p_i45110_3_;
    }
    
    public int getTypeInt() {
        return this.type;
    }
    
    static {
        $VALUES = new AttenuationType[] { AttenuationType.NONE, AttenuationType.LINEAR };
    }
}
