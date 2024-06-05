package net.minecraft.entity.passive;

enum EnumMoveType
{
    NONE("NONE", 0, 0.0f, 0.0f, 30, 1), 
    HOP("HOP", 1, 0.8f, 0.2f, 20, 10), 
    STEP("STEP", 2, 1.0f, 0.45f, 14, 14), 
    SPRINT("SPRINT", 3, 1.75f, 0.4f, 1, 8), 
    ATTACK("ATTACK", 4, 2.0f, 0.7f, 7, 8);
    
    private static final EnumMoveType[] $VALUES;
    private final float field_180076_f;
    private final float field_180077_g;
    private final int field_180084_h;
    private final int field_180085_i;
    
    private EnumMoveType(final String p_i45866_1_, final int p_i45866_2_, final float p_i45866_3_, final float p_i45866_4_, final int p_i45866_5_, final int p_i45866_6_) {
        this.field_180076_f = p_i45866_3_;
        this.field_180077_g = p_i45866_4_;
        this.field_180084_h = p_i45866_5_;
        this.field_180085_i = p_i45866_6_;
    }
    
    public float func_180072_a() {
        return this.field_180076_f;
    }
    
    public float func_180074_b() {
        return this.field_180077_g;
    }
    
    public int func_180075_c() {
        return this.field_180084_h;
    }
    
    public int func_180073_d() {
        return this.field_180085_i;
    }
    
    static {
        $VALUES = new EnumMoveType[] { EnumMoveType.NONE, EnumMoveType.HOP, EnumMoveType.STEP, EnumMoveType.SPRINT, EnumMoveType.ATTACK };
    }
}
