package HORIZON-6-0-SKIDPROTECTION;

public class ModelDragon extends ModelBase
{
    private ModelRenderer HorizonCode_Horizon_È;
    private ModelRenderer Â;
    private ModelRenderer Ý;
    private ModelRenderer Ø­áŒŠá;
    private ModelRenderer ÂµÈ;
    private ModelRenderer á;
    private ModelRenderer ˆÏ­;
    private ModelRenderer £á;
    private ModelRenderer Å;
    private ModelRenderer £à;
    private ModelRenderer µà;
    private ModelRenderer ˆà;
    private float ¥Æ;
    private static final String Ø­à = "CL_00000870";
    
    public ModelDragon(final float p_i46360_1_) {
        this.áŒŠÆ = 256;
        this.áˆºÑ¢Õ = 256;
        this.HorizonCode_Horizon_È("body.body", 0, 0);
        this.HorizonCode_Horizon_È("wing.skin", -56, 88);
        this.HorizonCode_Horizon_È("wingtip.skin", -56, 144);
        this.HorizonCode_Horizon_È("rearleg.main", 0, 0);
        this.HorizonCode_Horizon_È("rearfoot.main", 112, 0);
        this.HorizonCode_Horizon_È("rearlegtip.main", 196, 0);
        this.HorizonCode_Horizon_È("head.upperhead", 112, 30);
        this.HorizonCode_Horizon_È("wing.bone", 112, 88);
        this.HorizonCode_Horizon_È("head.upperlip", 176, 44);
        this.HorizonCode_Horizon_È("jaw.jaw", 176, 65);
        this.HorizonCode_Horizon_È("frontleg.main", 112, 104);
        this.HorizonCode_Horizon_È("wingtip.bone", 112, 136);
        this.HorizonCode_Horizon_È("frontfoot.main", 144, 104);
        this.HorizonCode_Horizon_È("neck.box", 192, 104);
        this.HorizonCode_Horizon_È("frontlegtip.main", 226, 138);
        this.HorizonCode_Horizon_È("body.scale", 220, 53);
        this.HorizonCode_Horizon_È("head.scale", 0, 0);
        this.HorizonCode_Horizon_È("neck.scale", 48, 0);
        this.HorizonCode_Horizon_È("head.nostril", 112, 0);
        final float var2 = -16.0f;
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, "head")).HorizonCode_Horizon_È("upperlip", -6.0f, -1.0f, -8.0f + var2, 12, 5, 16);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È("upperhead", -8.0f, -8.0f, 6.0f + var2, 16, 16, 16);
        this.HorizonCode_Horizon_È.áŒŠÆ = true;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È("scale", -5.0f, -12.0f, 12.0f + var2, 2, 4, 6);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È("nostril", -5.0f, -3.0f, -6.0f + var2, 2, 2, 4);
        this.HorizonCode_Horizon_È.áŒŠÆ = false;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È("scale", 3.0f, -12.0f, 12.0f + var2, 2, 4, 6);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È("nostril", 3.0f, -3.0f, -6.0f + var2, 2, 2, 4);
        (this.Ý = new ModelRenderer(this, "jaw")).HorizonCode_Horizon_È(0.0f, 4.0f, 8.0f + var2);
        this.Ý.HorizonCode_Horizon_È("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý);
        (this.Â = new ModelRenderer(this, "neck")).HorizonCode_Horizon_È("box", -5.0f, -5.0f, -5.0f, 10, 10, 10);
        this.Â.HorizonCode_Horizon_È("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6);
        (this.Ø­áŒŠá = new ModelRenderer(this, "body")).HorizonCode_Horizon_È(0.0f, 4.0f, 8.0f);
        this.Ø­áŒŠá.HorizonCode_Horizon_È("body", -12.0f, 0.0f, -16.0f, 24, 24, 64);
        this.Ø­áŒŠá.HorizonCode_Horizon_È("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12);
        this.Ø­áŒŠá.HorizonCode_Horizon_È("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12);
        this.Ø­áŒŠá.HorizonCode_Horizon_È("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12);
        (this.µà = new ModelRenderer(this, "wing")).HorizonCode_Horizon_È(-12.0f, 5.0f, 2.0f);
        this.µà.HorizonCode_Horizon_È("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        this.µà.HorizonCode_Horizon_È("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        (this.ˆà = new ModelRenderer(this, "wingtip")).HorizonCode_Horizon_È(-56.0f, 0.0f, 0.0f);
        this.ˆà.HorizonCode_Horizon_È("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        this.ˆà.HorizonCode_Horizon_È("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        this.µà.HorizonCode_Horizon_È(this.ˆà);
        (this.á = new ModelRenderer(this, "frontleg")).HorizonCode_Horizon_È(-12.0f, 20.0f, 2.0f);
        this.á.HorizonCode_Horizon_È("main", -4.0f, -4.0f, -4.0f, 8, 24, 8);
        (this.£á = new ModelRenderer(this, "frontlegtip")).HorizonCode_Horizon_È(0.0f, 20.0f, -1.0f);
        this.£á.HorizonCode_Horizon_È("main", -3.0f, -1.0f, -3.0f, 6, 24, 6);
        this.á.HorizonCode_Horizon_È(this.£á);
        (this.£à = new ModelRenderer(this, "frontfoot")).HorizonCode_Horizon_È(0.0f, 23.0f, 0.0f);
        this.£à.HorizonCode_Horizon_È("main", -4.0f, 0.0f, -12.0f, 8, 4, 16);
        this.£á.HorizonCode_Horizon_È(this.£à);
        (this.ÂµÈ = new ModelRenderer(this, "rearleg")).HorizonCode_Horizon_È(-16.0f, 16.0f, 42.0f);
        this.ÂµÈ.HorizonCode_Horizon_È("main", -8.0f, -4.0f, -8.0f, 16, 32, 16);
        (this.ˆÏ­ = new ModelRenderer(this, "rearlegtip")).HorizonCode_Horizon_È(0.0f, 32.0f, -4.0f);
        this.ˆÏ­.HorizonCode_Horizon_È("main", -6.0f, -2.0f, 0.0f, 12, 32, 12);
        this.ÂµÈ.HorizonCode_Horizon_È(this.ˆÏ­);
        (this.Å = new ModelRenderer(this, "rearfoot")).HorizonCode_Horizon_È(0.0f, 31.0f, 4.0f);
        this.Å.HorizonCode_Horizon_È("main", -9.0f, 0.0f, -20.0f, 18, 6, 24);
        this.ˆÏ­.HorizonCode_Horizon_È(this.Å);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        this.¥Æ = p_78086_4_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        GlStateManager.Çªà¢();
        final EntityDragon var8 = (EntityDragon)p_78088_1_;
        final float var9 = var8.Œá + (var8.µÂ - var8.Œá) * this.¥Æ;
        this.Ý.Ó = (float)(Math.sin(var9 * 3.1415927f * 2.0f) + 1.0) * 0.2f;
        float var10 = (float)(Math.sin(var9 * 3.1415927f * 2.0f - 1.0f) + 1.0);
        var10 = (var10 * var10 * 1.0f + var10 * 2.0f) * 0.05f;
        GlStateManager.Â(0.0f, var10 - 2.0f, -3.0f);
        GlStateManager.Â(var10 * 2.0f, 1.0f, 0.0f, 0.0f);
        float var11 = -30.0f;
        float var12 = 0.0f;
        final float var13 = 1.5f;
        double[] var14 = var8.Â(6, this.¥Æ);
        final float var15 = this.HorizonCode_Horizon_È(var8.Â(5, this.¥Æ)[0] - var8.Â(10, this.¥Æ)[0]);
        final float var16 = this.HorizonCode_Horizon_È(var8.Â(5, this.¥Æ)[0] + var15 / 2.0f);
        var11 += 2.0f;
        float var17 = var9 * 3.1415927f * 2.0f;
        var11 = 20.0f;
        float var18 = -12.0f;
        for (int var19 = 0; var19 < 5; ++var19) {
            final double[] var20 = var8.Â(5 - var19, this.¥Æ);
            final float var21 = (float)Math.cos(var19 * 0.45f + var17) * 0.15f;
            this.Â.à = this.HorizonCode_Horizon_È(var20[0] - var14[0]) * 3.1415927f / 180.0f * var13;
            this.Â.Ó = var21 + (float)(var20[1] - var14[1]) * 3.1415927f / 180.0f * var13 * 5.0f;
            this.Â.Ø = -this.HorizonCode_Horizon_È(var20[0] - var16) * 3.1415927f / 180.0f * var13;
            this.Â.Ø­áŒŠá = var11;
            this.Â.Âµá€ = var18;
            this.Â.Ý = var12;
            var11 += (float)(Math.sin(this.Â.Ó) * 10.0);
            var18 -= (float)(Math.cos(this.Â.à) * Math.cos(this.Â.Ó) * 10.0);
            var12 -= (float)(Math.sin(this.Â.à) * Math.cos(this.Â.Ó) * 10.0);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
        }
        this.HorizonCode_Horizon_È.Ø­áŒŠá = var11;
        this.HorizonCode_Horizon_È.Âµá€ = var18;
        this.HorizonCode_Horizon_È.Ý = var12;
        double[] var22 = var8.Â(0, this.¥Æ);
        this.HorizonCode_Horizon_È.à = this.HorizonCode_Horizon_È(var22[0] - var14[0]) * 3.1415927f / 180.0f * 1.0f;
        this.HorizonCode_Horizon_È.Ø = -this.HorizonCode_Horizon_È(var22[0] - var16) * 3.1415927f / 180.0f * 1.0f;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_78088_7_);
        GlStateManager.Çªà¢();
        GlStateManager.Â(0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-var15 * var13 * 1.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(0.0f, -1.0f, 0.0f);
        this.Ø­áŒŠá.Ø = 0.0f;
        this.Ø­áŒŠá.HorizonCode_Horizon_È(p_78088_7_);
        for (int var23 = 0; var23 < 2; ++var23) {
            GlStateManager.Å();
            final float var21 = var9 * 3.1415927f * 2.0f;
            this.µà.Ó = 0.125f - (float)Math.cos(var21) * 0.2f;
            this.µà.à = 0.25f;
            this.µà.Ø = (float)(Math.sin(var21) + 0.125) * 0.8f;
            this.ˆà.Ø = -(float)(Math.sin(var21 + 2.0f) + 0.5) * 0.75f;
            this.ÂµÈ.Ó = 1.0f + var10 * 0.1f;
            this.ˆÏ­.Ó = 0.5f + var10 * 0.1f;
            this.Å.Ó = 0.75f + var10 * 0.1f;
            this.á.Ó = 1.3f + var10 * 0.1f;
            this.£á.Ó = -0.5f - var10 * 0.1f;
            this.£à.Ó = 0.75f + var10 * 0.1f;
            this.µà.HorizonCode_Horizon_È(p_78088_7_);
            this.á.HorizonCode_Horizon_È(p_78088_7_);
            this.ÂµÈ.HorizonCode_Horizon_È(p_78088_7_);
            GlStateManager.HorizonCode_Horizon_È(-1.0f, 1.0f, 1.0f);
            if (var23 == 0) {
                GlStateManager.Âµá€(1028);
            }
        }
        GlStateManager.Ê();
        GlStateManager.Âµá€(1029);
        GlStateManager.£à();
        float var24 = -(float)Math.sin(var9 * 3.1415927f * 2.0f) * 0.0f;
        var17 = var9 * 3.1415927f * 2.0f;
        var11 = 10.0f;
        var18 = 60.0f;
        var12 = 0.0f;
        var14 = var8.Â(11, this.¥Æ);
        for (int var25 = 0; var25 < 12; ++var25) {
            var22 = var8.Â(12 + var25, this.¥Æ);
            var24 += (float)(Math.sin(var25 * 0.45f + var17) * 0.05000000074505806);
            this.Â.à = (this.HorizonCode_Horizon_È(var22[0] - var14[0]) * var13 + 180.0f) * 3.1415927f / 180.0f;
            this.Â.Ó = var24 + (float)(var22[1] - var14[1]) * 3.1415927f / 180.0f * var13 * 5.0f;
            this.Â.Ø = this.HorizonCode_Horizon_È(var22[0] - var16) * 3.1415927f / 180.0f * var13;
            this.Â.Ø­áŒŠá = var11;
            this.Â.Âµá€ = var18;
            this.Â.Ý = var12;
            var11 += (float)(Math.sin(this.Â.Ó) * 10.0);
            var18 -= (float)(Math.cos(this.Â.à) * Math.cos(this.Â.Ó) * 10.0);
            var12 -= (float)(Math.sin(this.Â.à) * Math.cos(this.Â.Ó) * 10.0);
            this.Â.HorizonCode_Horizon_È(p_78088_7_);
        }
        GlStateManager.Ê();
    }
    
    private float HorizonCode_Horizon_È(double p_78214_1_) {
        while (p_78214_1_ >= 180.0) {
            p_78214_1_ -= 360.0;
        }
        while (p_78214_1_ < -180.0) {
            p_78214_1_ += 360.0;
        }
        return (float)p_78214_1_;
    }
}
