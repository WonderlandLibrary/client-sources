package HORIZON-6-0-SKIDPROTECTION;

public class LayerEndermanEyes implements LayerRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final RenderEnderman Â;
    private static final String Ý = "CL_00002418";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/enderman/enderman_eyes.png");
    }
    
    public LayerEndermanEyes(final RenderEnderman p_i46117_1_) {
        this.Â = p_i46117_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityEnderman p_177201_1_, final float p_177201_2_, final float p_177201_3_, final float p_177201_4_, final float p_177201_5_, final float p_177201_6_, final float p_177201_7_, final float p_177201_8_) {
        this.Â.HorizonCode_Horizon_È(LayerEndermanEyes.HorizonCode_Horizon_È);
        GlStateManager.á();
        GlStateManager.Ý();
        GlStateManager.Â(1, 1);
        GlStateManager.Ó();
        if (p_177201_1_.áŒŠÏ()) {
            GlStateManager.HorizonCode_Horizon_È(false);
        }
        else {
            GlStateManager.HorizonCode_Horizon_È(true);
        }
        final char var9 = '';
        final int var10 = var9 % 65536;
        final int var11 = var9 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var10 / 1.0f, var11 / 1.0f);
        GlStateManager.Âµá€();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        this.Â.Â().HorizonCode_Horizon_È(p_177201_1_, p_177201_2_, p_177201_3_, p_177201_5_, p_177201_6_, p_177201_7_, p_177201_8_);
        this.Â.HorizonCode_Horizon_È(p_177201_1_, p_177201_4_);
        GlStateManager.ÂµÈ();
        GlStateManager.Ø­áŒŠá();
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityEnderman)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
