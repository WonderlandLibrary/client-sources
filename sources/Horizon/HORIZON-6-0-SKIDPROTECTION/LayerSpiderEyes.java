package HORIZON-6-0-SKIDPROTECTION;

public class LayerSpiderEyes implements LayerRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final RenderSpider Â;
    private static final String Ý = "CL_00002410";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/spider_eyes.png");
    }
    
    public LayerSpiderEyes(final RenderSpider p_i46109_1_) {
        this.Â = p_i46109_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntitySpider p_177148_1_, final float p_177148_2_, final float p_177148_3_, final float p_177148_4_, final float p_177148_5_, final float p_177148_6_, final float p_177148_7_, final float p_177148_8_) {
        this.Â.HorizonCode_Horizon_È(LayerSpiderEyes.HorizonCode_Horizon_È);
        GlStateManager.á();
        GlStateManager.Ý();
        GlStateManager.Â(1, 1);
        if (p_177148_1_.áŒŠÏ()) {
            GlStateManager.HorizonCode_Horizon_È(false);
        }
        else {
            GlStateManager.HorizonCode_Horizon_È(true);
        }
        final char var9 = '';
        int var10 = var9 % 65536;
        int var11 = var9 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var10 / 1.0f, var11 / 1.0f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        this.Â.Â().HorizonCode_Horizon_È(p_177148_1_, p_177148_2_, p_177148_3_, p_177148_5_, p_177148_6_, p_177148_7_, p_177148_8_);
        final int var12 = p_177148_1_.HorizonCode_Horizon_È(p_177148_4_);
        var10 = var12 % 65536;
        var11 = var12 / 65536;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var10 / 1.0f, var11 / 1.0f);
        this.Â.HorizonCode_Horizon_È(p_177148_1_, p_177148_4_);
        GlStateManager.ÂµÈ();
        GlStateManager.Ø­áŒŠá();
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntitySpider)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
