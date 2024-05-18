package HORIZON-6-0-SKIDPROTECTION;

public class EntityPickupFX extends EntityFX
{
    private Entity HorizonCode_Horizon_È;
    private Entity ÇŽá;
    private int Ñ¢à;
    private int ÇªØ­;
    private float £áŒŠá;
    private RenderManager áˆº;
    private static final String Šà = "CL_00000930";
    
    public EntityPickupFX(final World worldIn, final Entity p_i1233_2_, final Entity p_i1233_3_, final float p_i1233_4_) {
        super(worldIn, p_i1233_2_.ŒÏ, p_i1233_2_.Çªà¢, p_i1233_2_.Ê, p_i1233_2_.ÇŽÉ, p_i1233_2_.ˆá, p_i1233_2_.ÇŽÕ);
        this.áˆº = Minecraft.áŒŠà().ÇªÓ();
        this.HorizonCode_Horizon_È = p_i1233_2_;
        this.ÇŽá = p_i1233_3_;
        this.ÇªØ­ = 3;
        this.£áŒŠá = p_i1233_4_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        float var9 = (this.Ñ¢à + p_180434_3_) / this.ÇªØ­;
        var9 *= var9;
        final double var10 = this.HorizonCode_Horizon_È.ŒÏ;
        final double var11 = this.HorizonCode_Horizon_È.Çªà¢;
        final double var12 = this.HorizonCode_Horizon_È.Ê;
        final double var13 = this.ÇŽá.áˆºáˆºÈ + (this.ÇŽá.ŒÏ - this.ÇŽá.áˆºáˆºÈ) * p_180434_3_;
        final double var14 = this.ÇŽá.ÇŽá€ + (this.ÇŽá.Çªà¢ - this.ÇŽá.ÇŽá€) * p_180434_3_ + this.£áŒŠá;
        final double var15 = this.ÇŽá.Ï + (this.ÇŽá.Ê - this.ÇŽá.Ï) * p_180434_3_;
        double var16 = var10 + (var13 - var10) * var9;
        double var17 = var11 + (var14 - var11) * var9;
        double var18 = var12 + (var15 - var12) * var9;
        final int var19 = this.HorizonCode_Horizon_È(p_180434_3_);
        final int var20 = var19 % 65536;
        final int var21 = var19 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var20 / 1.0f, var21 / 1.0f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        var16 -= EntityPickupFX.Å;
        var17 -= EntityPickupFX.£à;
        var18 -= EntityPickupFX.µà;
        this.áˆº.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, (float)var16, (float)var17, (float)var18, this.HorizonCode_Horizon_È.É, p_180434_3_);
    }
    
    @Override
    public void á() {
        ++this.Ñ¢à;
        if (this.Ñ¢à == this.ÇªØ­) {
            this.á€();
        }
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
}
