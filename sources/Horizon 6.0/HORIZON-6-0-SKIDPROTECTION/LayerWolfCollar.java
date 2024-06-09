package HORIZON-6-0-SKIDPROTECTION;

public class LayerWolfCollar implements LayerRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final RenderWolf Â;
    private static final String Ý = "CL_00002405";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/wolf/wolf_collar.png");
    }
    
    public LayerWolfCollar(final RenderWolf p_i46104_1_) {
        this.Â = p_i46104_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityWolf p_177145_1_, final float p_177145_2_, final float p_177145_3_, final float p_177145_4_, final float p_177145_5_, final float p_177145_6_, final float p_177145_7_, final float p_177145_8_) {
        if (p_177145_1_.ÐƒÓ() && !p_177145_1_.áŒŠÏ()) {
            this.Â.HorizonCode_Horizon_È(LayerWolfCollar.HorizonCode_Horizon_È);
            final EnumDyeColor var9 = EnumDyeColor.Â(p_177145_1_.Ñ¢Õ().Â());
            final float[] var10 = EntitySheep.HorizonCode_Horizon_È(var9);
            GlStateManager.Ý(var10[0], var10[1], var10[2]);
            this.Â.Â().HorizonCode_Horizon_È(p_177145_1_, p_177145_2_, p_177145_3_, p_177145_5_, p_177145_6_, p_177145_7_, p_177145_8_);
        }
    }
    
    @Override
    public boolean Â() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityWolf)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
