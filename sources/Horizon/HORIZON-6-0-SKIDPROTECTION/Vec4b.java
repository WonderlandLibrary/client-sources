package HORIZON-6-0-SKIDPROTECTION;

public class Vec4b
{
    private byte HorizonCode_Horizon_È;
    private byte Â;
    private byte Ý;
    private byte Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001964";
    
    public Vec4b(final byte p_i45555_1_, final byte p_i45555_2_, final byte p_i45555_3_, final byte p_i45555_4_) {
        this.HorizonCode_Horizon_È = p_i45555_1_;
        this.Â = p_i45555_2_;
        this.Ý = p_i45555_3_;
        this.Ø­áŒŠá = p_i45555_4_;
    }
    
    public Vec4b(final Vec4b p_i45556_1_) {
        this.HorizonCode_Horizon_È = p_i45556_1_.HorizonCode_Horizon_È;
        this.Â = p_i45556_1_.Â;
        this.Ý = p_i45556_1_.Ý;
        this.Ø­áŒŠá = p_i45556_1_.Ø­áŒŠá;
    }
    
    public byte HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public byte Â() {
        return this.Â;
    }
    
    public byte Ý() {
        return this.Ý;
    }
    
    public byte Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof Vec4b)) {
            return false;
        }
        final Vec4b var2 = (Vec4b)p_equals_1_;
        return this.HorizonCode_Horizon_È == var2.HorizonCode_Horizon_È && this.Ø­áŒŠá == var2.Ø­áŒŠá && this.Â == var2.Â && this.Ý == var2.Ý;
    }
    
    @Override
    public int hashCode() {
        final byte var1 = this.HorizonCode_Horizon_È;
        int var2 = 31 * var1 + this.Â;
        var2 = 31 * var2 + this.Ý;
        var2 = 31 * var2 + this.Ø­áŒŠá;
        return var2;
    }
}
