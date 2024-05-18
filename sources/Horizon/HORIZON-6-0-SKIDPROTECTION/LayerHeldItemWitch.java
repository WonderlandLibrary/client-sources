package HORIZON-6-0-SKIDPROTECTION;

public class LayerHeldItemWitch implements LayerRenderer
{
    private final RenderWitch HorizonCode_Horizon_È;
    private static final String Â = "CL_00002407";
    
    public LayerHeldItemWitch(final RenderWitch p_i46106_1_) {
        this.HorizonCode_Horizon_È = p_i46106_1_;
    }
    
    public void HorizonCode_Horizon_È(final EntityWitch p_177143_1_, final float p_177143_2_, final float p_177143_3_, final float p_177143_4_, final float p_177143_5_, final float p_177143_6_, final float p_177143_7_, final float p_177143_8_) {
        final ItemStack var9 = p_177143_1_.Çª();
        if (var9 != null) {
            GlStateManager.Ý(1.0f, 1.0f, 1.0f);
            GlStateManager.Çªà¢();
            if (this.HorizonCode_Horizon_È.Â().à) {
                GlStateManager.Â(0.0f, 0.625f, 0.0f);
                GlStateManager.Â(-20.0f, -1.0f, 0.0f, 0.0f);
                final float var10 = 0.5f;
                GlStateManager.HorizonCode_Horizon_È(var10, var10, var10);
            }
            ((ModelWitch)this.HorizonCode_Horizon_È.Â()).á.Ý(0.0625f);
            GlStateManager.Â(-0.0625f, 0.53125f, 0.21875f);
            final Item_1028566121 var11 = var9.HorizonCode_Horizon_È();
            final Minecraft var12 = Minecraft.áŒŠà();
            if (var11 instanceof ItemBlock && var12.Ô().HorizonCode_Horizon_È(Block.HorizonCode_Horizon_È(var11), var9.Ø())) {
                GlStateManager.Â(0.0f, 0.1875f, -0.3125f);
                GlStateManager.Â(20.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(45.0f, 0.0f, 1.0f, 0.0f);
                final float var13 = 0.375f;
                GlStateManager.HorizonCode_Horizon_È(var13, -var13, var13);
            }
            else if (var11 == Items.Ó) {
                GlStateManager.Â(0.0f, 0.125f, 0.3125f);
                GlStateManager.Â(-20.0f, 0.0f, 1.0f, 0.0f);
                final float var13 = 0.625f;
                GlStateManager.HorizonCode_Horizon_È(var13, -var13, var13);
                GlStateManager.Â(-100.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (var11.Ó()) {
                if (var11.à()) {
                    GlStateManager.Â(180.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.Â(0.0f, -0.125f, 0.0f);
                }
                this.HorizonCode_Horizon_È.u_();
                final float var13 = 0.625f;
                GlStateManager.HorizonCode_Horizon_È(var13, -var13, var13);
                GlStateManager.Â(-100.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GlStateManager.Â(0.25f, 0.1875f, -0.1875f);
                final float var13 = 0.375f;
                GlStateManager.HorizonCode_Horizon_È(var13, var13, var13);
                GlStateManager.Â(60.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.Â(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(20.0f, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.Â(-15.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(40.0f, 0.0f, 0.0f, 1.0f);
            var12.ˆáƒ().HorizonCode_Horizon_È(p_177143_1_, var9, ItemCameraTransforms.Â.Â);
            GlStateManager.Ê();
        }
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.HorizonCode_Horizon_È((EntityWitch)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
