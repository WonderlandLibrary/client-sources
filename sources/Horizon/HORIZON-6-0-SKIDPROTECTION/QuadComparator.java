package HORIZON-6-0-SKIDPROTECTION;

import java.nio.FloatBuffer;
import java.util.Comparator;

public class QuadComparator implements Comparator
{
    private float HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    private FloatBuffer Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00000958";
    
    public QuadComparator(final FloatBuffer p_i46247_1_, final float p_i46247_2_, final float p_i46247_3_, final float p_i46247_4_, final int p_i46247_5_) {
        this.Ø­áŒŠá = p_i46247_1_;
        this.HorizonCode_Horizon_È = p_i46247_2_;
        this.Â = p_i46247_3_;
        this.Ý = p_i46247_4_;
        this.Âµá€ = p_i46247_5_;
    }
    
    public int HorizonCode_Horizon_È(final Integer p_compare_1_, final Integer p_compare_2_) {
        final float var3 = this.Ø­áŒŠá.get(p_compare_1_) - this.HorizonCode_Horizon_È;
        final float var4 = this.Ø­áŒŠá.get(p_compare_1_ + 1) - this.Â;
        final float var5 = this.Ø­áŒŠá.get(p_compare_1_ + 2) - this.Ý;
        final float var6 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ + 0) - this.HorizonCode_Horizon_È;
        final float var7 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ + 1) - this.Â;
        final float var8 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ + 2) - this.Ý;
        final float var9 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ * 2 + 0) - this.HorizonCode_Horizon_È;
        final float var10 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ * 2 + 1) - this.Â;
        final float var11 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ * 2 + 2) - this.Ý;
        final float var12 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ * 3 + 0) - this.HorizonCode_Horizon_È;
        final float var13 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ * 3 + 1) - this.Â;
        final float var14 = this.Ø­áŒŠá.get(p_compare_1_ + this.Âµá€ * 3 + 2) - this.Ý;
        final float var15 = this.Ø­áŒŠá.get(p_compare_2_) - this.HorizonCode_Horizon_È;
        final float var16 = this.Ø­áŒŠá.get(p_compare_2_ + 1) - this.Â;
        final float var17 = this.Ø­áŒŠá.get(p_compare_2_ + 2) - this.Ý;
        final float var18 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ + 0) - this.HorizonCode_Horizon_È;
        final float var19 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ + 1) - this.Â;
        final float var20 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ + 2) - this.Ý;
        final float var21 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ * 2 + 0) - this.HorizonCode_Horizon_È;
        final float var22 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ * 2 + 1) - this.Â;
        final float var23 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ * 2 + 2) - this.Ý;
        final float var24 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ * 3 + 0) - this.HorizonCode_Horizon_È;
        final float var25 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ * 3 + 1) - this.Â;
        final float var26 = this.Ø­áŒŠá.get(p_compare_2_ + this.Âµá€ * 3 + 2) - this.Ý;
        final float var27 = (var3 + var6 + var9 + var12) * 0.25f;
        final float var28 = (var4 + var7 + var10 + var13) * 0.25f;
        final float var29 = (var5 + var8 + var11 + var14) * 0.25f;
        final float var30 = (var15 + var18 + var21 + var24) * 0.25f;
        final float var31 = (var16 + var19 + var22 + var25) * 0.25f;
        final float var32 = (var17 + var20 + var23 + var26) * 0.25f;
        final float var33 = var27 * var27 + var28 * var28 + var29 * var29;
        final float var34 = var30 * var30 + var31 * var31 + var32 * var32;
        return Float.compare(var34, var33);
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.HorizonCode_Horizon_È((Integer)p_compare_1_, (Integer)p_compare_2_);
    }
}
