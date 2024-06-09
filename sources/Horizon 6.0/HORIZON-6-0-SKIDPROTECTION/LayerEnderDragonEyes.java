package HORIZON-6-0-SKIDPROTECTION;

public class LayerEnderDragonEyes implements LayerRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final RenderDragon Â;
    private static final String Ý = "CL_00002419";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/enderdragon/dragon_eyes.png");
    }
    
    public LayerEnderDragonEyes(final RenderDragon p_i46118_1_) {
        this.Â = p_i46118_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityDragon p_177210_1_, final float p_177210_2_, final float p_177210_3_, final float p_177210_4_, final float p_177210_5_, final float p_177210_6_, final float p_177210_7_, final float p_177210_8_) {
        this.Â.HorizonCode_Horizon_È(LayerEnderDragonEyes.HorizonCode_Horizon_È);
        GlStateManager.á();
        GlStateManager.Ý();
        GlStateManager.Â(1, 1);
        GlStateManager.Ó();
        GlStateManager.Ý(514);
        final char var9 = '';
        final int var10 = var9 % 65536;
        final int var11 = var9 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var10 / 1.0f, var11 / 1.0f);
        GlStateManager.Âµá€();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        this.Â.Â().HorizonCode_Horizon_È(p_177210_1_, p_177210_2_, p_177210_3_, p_177210_5_, p_177210_6_, p_177210_7_, p_177210_8_);
        this.Â.HorizonCode_Horizon_È(p_177210_1_, p_177210_4_);
        GlStateManager.ÂµÈ();
        GlStateManager.Ø­áŒŠá();
        GlStateManager.Ý(515);
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityDragon)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
