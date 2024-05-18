package HORIZON-6-0-SKIDPROTECTION;

public class ModelGuardian extends ModelBase
{
    private ModelRenderer HorizonCode_Horizon_È;
    private ModelRenderer Â;
    private ModelRenderer[] Ý;
    private ModelRenderer[] Ø­áŒŠá;
    private static final String ÂµÈ = "CL_00002628";
    
    public ModelGuardian() {
        this.áŒŠÆ = 64;
        this.áˆºÑ¢Õ = 64;
        this.Ý = new ModelRenderer[12];
        this.HorizonCode_Horizon_È = new ModelRenderer(this);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0, 0).HorizonCode_Horizon_È(-6.0f, 10.0f, -8.0f, 12, 12, 16);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0, 28).HorizonCode_Horizon_È(-8.0f, 10.0f, -6.0f, 2, 12, 12);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0, 28).HorizonCode_Horizon_È(6.0f, 10.0f, -6.0f, 2, 12, 12, true);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(16, 40).HorizonCode_Horizon_È(-6.0f, 8.0f, -6.0f, 12, 2, 12);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(16, 40).HorizonCode_Horizon_È(-6.0f, 22.0f, -6.0f, 12, 2, 12);
        for (int var1 = 0; var1 < this.Ý.length; ++var1) {
            (this.Ý[var1] = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-1.0f, -4.5f, -1.0f, 2, 9, 2);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý[var1]);
        }
        (this.Â = new ModelRenderer(this, 8, 0)).HorizonCode_Horizon_È(-1.0f, 15.0f, 0.0f, 2, 2, 1);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â);
        this.Ø­áŒŠá = new ModelRenderer[3];
        (this.Ø­áŒŠá[0] = new ModelRenderer(this, 40, 0)).HorizonCode_Horizon_È(-2.0f, 14.0f, 7.0f, 4, 4, 8);
        (this.Ø­áŒŠá[1] = new ModelRenderer(this, 0, 54)).HorizonCode_Horizon_È(0.0f, 14.0f, 0.0f, 3, 3, 7);
        this.Ø­áŒŠá[2] = new ModelRenderer(this);
        this.Ø­áŒŠá[2].HorizonCode_Horizon_È(41, 32).HorizonCode_Horizon_È(0.0f, 14.0f, 0.0f, 2, 2, 6);
        this.Ø­áŒŠá[2].HorizonCode_Horizon_È(25, 19).HorizonCode_Horizon_È(1.0f, 10.5f, 3.0f, 1, 9, 9);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ø­áŒŠá[0]);
        this.Ø­áŒŠá[0].HorizonCode_Horizon_È(this.Ø­áŒŠá[1]);
        this.Ø­áŒŠá[1].HorizonCode_Horizon_È(this.Ø­áŒŠá[2]);
    }
    
    public int HorizonCode_Horizon_È() {
        return 54;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.HorizonCode_Horizon_È(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        final EntityGuardian var8 = (EntityGuardian)p_78087_7_;
        final float var9 = p_78087_3_ - var8.Œ;
        this.HorizonCode_Horizon_È.à = p_78087_4_ / 57.295776f;
        this.HorizonCode_Horizon_È.Ó = p_78087_5_ / 57.295776f;
        final float[] var10 = { 1.75f, 0.25f, 0.0f, 0.0f, 0.5f, 0.5f, 0.5f, 0.5f, 1.25f, 0.75f, 0.0f, 0.0f };
        final float[] var11 = { 0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 1.75f, 1.25f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f };
        final float[] var12 = { 0.0f, 0.0f, 0.25f, 1.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.75f, 1.25f };
        final float[] var13 = { 0.0f, 0.0f, 8.0f, -8.0f, -8.0f, 8.0f, 8.0f, -8.0f, 0.0f, 0.0f, 8.0f, -8.0f };
        final float[] var14 = { -8.0f, -8.0f, -8.0f, -8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 8.0f, 8.0f, 8.0f, 8.0f };
        final float[] var15 = { 8.0f, -8.0f, 0.0f, 0.0f, -8.0f, -8.0f, 8.0f, 8.0f, 8.0f, -8.0f, 0.0f, 0.0f };
        final float var16 = (1.0f - var8.£á(var9)) * 0.55f;
        for (int var17 = 0; var17 < 12; ++var17) {
            this.Ý[var17].Ó = 3.1415927f * var10[var17];
            this.Ý[var17].à = 3.1415927f * var11[var17];
            this.Ý[var17].Ø = 3.1415927f * var12[var17];
            this.Ý[var17].Ý = var13[var17] * (1.0f + MathHelper.Â(p_78087_3_ * 1.5f + var17) * 0.01f - var16);
            this.Ý[var17].Ø­áŒŠá = 16.0f + var14[var17] * (1.0f + MathHelper.Â(p_78087_3_ * 1.5f + var17) * 0.01f - var16);
            this.Ý[var17].Âµá€ = var15[var17] * (1.0f + MathHelper.Â(p_78087_3_ * 1.5f + var17) * 0.01f - var16);
        }
        this.Â.Âµá€ = -8.25f;
        Object var18 = Minecraft.áŒŠà().ÇŽá€();
        if (var8.¥Ê()) {
            var18 = var8.ÐƒÓ();
        }
        if (var18 != null) {
            final Vec3 var19 = ((Entity)var18).à(0.0f);
            final Vec3 var20 = p_78087_7_.à(0.0f);
            final double var21 = var19.Â - var20.Â;
            if (var21 > 0.0) {
                this.Â.Ø­áŒŠá = 0.0f;
            }
            else {
                this.Â.Ø­áŒŠá = 1.0f;
            }
            Vec3 var22 = p_78087_7_.Ó(0.0f);
            var22 = new Vec3(var22.HorizonCode_Horizon_È, 0.0, var22.Ý);
            final Vec3 var23 = new Vec3(var20.HorizonCode_Horizon_È - var19.HorizonCode_Horizon_È, 0.0, var20.Ý - var19.Ý).HorizonCode_Horizon_È().Â(1.5707964f);
            final double var24 = var22.Â(var23);
            this.Â.Ý = MathHelper.Ý((float)Math.abs(var24)) * 2.0f * (float)Math.signum(var24);
        }
        this.Â.áˆºÑ¢Õ = true;
        final float var25 = var8.Ý(var9);
        this.Ø­áŒŠá[0].à = MathHelper.HorizonCode_Horizon_È(var25) * 3.1415927f * 0.05f;
        this.Ø­áŒŠá[1].à = MathHelper.HorizonCode_Horizon_È(var25) * 3.1415927f * 0.1f;
        this.Ø­áŒŠá[1].Ý = -1.5f;
        this.Ø­áŒŠá[1].Ø­áŒŠá = 0.5f;
        this.Ø­áŒŠá[1].Âµá€ = 14.0f;
        this.Ø­áŒŠá[2].à = MathHelper.HorizonCode_Horizon_È(var25) * 3.1415927f * 0.15f;
        this.Ø­áŒŠá[2].Ý = 0.5f;
        this.Ø­áŒŠá[2].Ø­áŒŠá = 0.5f;
        this.Ø­áŒŠá[2].Âµá€ = 6.0f;
    }
}
