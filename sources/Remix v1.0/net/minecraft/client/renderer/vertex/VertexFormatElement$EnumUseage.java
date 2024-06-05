package net.minecraft.client.renderer.vertex;

public enum EnumUseage
{
    POSITION("POSITION", 0, "Position"), 
    NORMAL("NORMAL", 1, "Normal"), 
    COLOR("COLOR", 2, "Vertex Color"), 
    UV("UV", 3, "UV"), 
    MATRIX("MATRIX", 4, "Bone Matrix"), 
    BLEND_WEIGHT("BLEND_WEIGHT", 5, "Blend Weight"), 
    PADDING("PADDING", 6, "Padding");
    
    private static final EnumUseage[] $VALUES;
    private final String field_177392_h;
    
    private EnumUseage(final String p_i46094_1_, final int p_i46094_2_, final String p_i46094_3_) {
        this.field_177392_h = p_i46094_3_;
    }
    
    public String func_177384_a() {
        return this.field_177392_h;
    }
    
    static {
        $VALUES = new EnumUseage[] { EnumUseage.POSITION, EnumUseage.NORMAL, EnumUseage.COLOR, EnumUseage.UV, EnumUseage.MATRIX, EnumUseage.BLEND_WEIGHT, EnumUseage.PADDING };
    }
}
