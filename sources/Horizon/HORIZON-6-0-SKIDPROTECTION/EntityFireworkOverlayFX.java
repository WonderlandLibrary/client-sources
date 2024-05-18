package HORIZON-6-0-SKIDPROTECTION;

public class EntityFireworkOverlayFX extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000904";
    
    protected EntityFireworkOverlayFX(final World worldIn, final double p_i46357_2_, final double p_i46357_4_, final double p_i46357_6_) {
        super(worldIn, p_i46357_2_, p_i46357_4_, p_i46357_6_);
        this.à = 4;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        final float var9 = 0.25f;
        final float var10 = var9 + 0.25f;
        final float var11 = 0.125f;
        final float var12 = var11 + 0.25f;
        final float var13 = 7.1f * MathHelper.HorizonCode_Horizon_È((this.Ó + p_180434_3_ - 1.0f) * 0.25f * 3.1415927f);
        this.ˆÏ­ = 0.6f - (this.Ó + p_180434_3_ - 1.0f) * 0.25f * 0.5f;
        final float var14 = (float)(this.áŒŠà + (this.ŒÏ - this.áŒŠà) * p_180434_3_ - EntityFireworkOverlayFX.Å);
        final float var15 = (float)(this.ŠÄ + (this.Çªà¢ - this.ŠÄ) * p_180434_3_ - EntityFireworkOverlayFX.£à);
        final float var16 = (float)(this.Ñ¢á + (this.Ê - this.Ñ¢á) * p_180434_3_ - EntityFireworkOverlayFX.µà);
        p_180434_1_.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, this.ÂµÈ, this.á, this.ˆÏ­);
        p_180434_1_.HorizonCode_Horizon_È(var14 - p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 - p_180434_6_ * var13 - p_180434_8_ * var13, var10, var12);
        p_180434_1_.HorizonCode_Horizon_È(var14 - p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 - p_180434_6_ * var13 + p_180434_8_ * var13, var10, var11);
        p_180434_1_.HorizonCode_Horizon_È(var14 + p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 + p_180434_6_ * var13 + p_180434_8_ * var13, var9, var11);
        p_180434_1_.HorizonCode_Horizon_È(var14 + p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 + p_180434_6_ * var13 - p_180434_8_ * var13, var9, var12);
    }
}
