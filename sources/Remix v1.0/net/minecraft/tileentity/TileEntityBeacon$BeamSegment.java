package net.minecraft.tileentity;

public static class BeamSegment
{
    private final float[] field_177266_a;
    private int field_177265_b;
    
    public BeamSegment(final float[] p_i45669_1_) {
        this.field_177266_a = p_i45669_1_;
        this.field_177265_b = 1;
    }
    
    protected void func_177262_a() {
        ++this.field_177265_b;
    }
    
    public float[] func_177263_b() {
        return this.field_177266_a;
    }
    
    public int func_177264_c() {
        return this.field_177265_b;
    }
}
