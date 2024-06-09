package HORIZON-6-0-SKIDPROTECTION;

public class LayerSaddle implements LayerRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final RenderPig Â;
    private final ModelPig Ý;
    private static final String Ø­áŒŠá = "CL_00002414";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/pig/pig_saddle.png");
    }
    
    public LayerSaddle(final RenderPig p_i46113_1_) {
        this.Ý = new ModelPig(0.5f);
        this.Â = p_i46113_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityPig p_177155_1_, final float p_177155_2_, final float p_177155_3_, final float p_177155_4_, final float p_177155_5_, final float p_177155_6_, final float p_177155_7_, final float p_177155_8_) {
        if (p_177155_1_.ÐƒÇŽà()) {
            this.Â.HorizonCode_Horizon_È(LayerSaddle.HorizonCode_Horizon_È);
            this.Ý.HorizonCode_Horizon_È(this.Â.Â());
            this.Ý.HorizonCode_Horizon_È(p_177155_1_, p_177155_2_, p_177155_3_, p_177155_5_, p_177155_6_, p_177155_7_, p_177155_8_);
        }
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityPig)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
