package net.minecraft.client.renderer.vertex;

public enum EnumType
{
    FLOAT("FLOAT", 0, 4, "Float", 5126), 
    UBYTE("UBYTE", 1, 1, "Unsigned Byte", 5121), 
    BYTE("BYTE", 2, 1, "Byte", 5120), 
    USHORT("USHORT", 3, 2, "Unsigned Short", 5123), 
    SHORT("SHORT", 4, 2, "Short", 5122), 
    UINT("UINT", 5, 4, "Unsigned Int", 5125), 
    INT("INT", 6, 4, "Int", 5124);
    
    private static final EnumType[] $VALUES;
    private final int field_177407_h;
    private final String field_177408_i;
    private final int field_177405_j;
    
    private EnumType(final String p_i46095_1_, final int p_i46095_2_, final int p_i46095_3_, final String p_i46095_4_, final int p_i46095_5_) {
        this.field_177407_h = p_i46095_3_;
        this.field_177408_i = p_i46095_4_;
        this.field_177405_j = p_i46095_5_;
    }
    
    public int func_177395_a() {
        return this.field_177407_h;
    }
    
    public String func_177396_b() {
        return this.field_177408_i;
    }
    
    public int func_177397_c() {
        return this.field_177405_j;
    }
    
    static {
        $VALUES = new EnumType[] { EnumType.FLOAT, EnumType.UBYTE, EnumType.BYTE, EnumType.USHORT, EnumType.SHORT, EnumType.UINT, EnumType.INT };
    }
}
