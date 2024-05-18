package HORIZON-6-0-SKIDPROTECTION;

public class LayerHeldItem implements LayerRenderer
{
    private final RendererLivingEntity HorizonCode_Horizon_È;
    private static final String Â = "CL_00002416";
    
    public LayerHeldItem(final RendererLivingEntity p_i46115_1_) {
        this.HorizonCode_Horizon_È = p_i46115_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        ItemStack var9 = p_177141_1_.Çª();
        if (var9 != null) {
            GlStateManager.Çªà¢();
            if (this.HorizonCode_Horizon_È.Â().à) {
                final float var10 = 0.5f;
                GlStateManager.Â(0.0f, 0.625f, 0.0f);
                GlStateManager.Â(-20.0f, -1.0f, 0.0f, 0.0f);
                GlStateManager.HorizonCode_Horizon_È(var10, var10, var10);
            }
            ((ModelBiped)this.HorizonCode_Horizon_È.Â()).HorizonCode_Horizon_È(0.0625f);
            GlStateManager.Â(-0.0625f, 0.4375f, 0.0625f);
            if (p_177141_1_ instanceof EntityPlayer && ((EntityPlayer)p_177141_1_).µÏ != null) {
                var9 = new ItemStack(Items.ÂµÕ, 0);
            }
            final Item_1028566121 var11 = var9.HorizonCode_Horizon_È();
            final Minecraft var12 = Minecraft.áŒŠà();
            if (var11 instanceof ItemBlock && Block.HorizonCode_Horizon_È(var11).ÂµÈ() == 2) {
                GlStateManager.Â(0.0f, 0.1875f, -0.3125f);
                GlStateManager.Â(20.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.Â(45.0f, 0.0f, 1.0f, 0.0f);
                final float var13 = 0.375f;
                GlStateManager.HorizonCode_Horizon_È(-var13, -var13, var13);
            }
            var12.ˆáƒ().HorizonCode_Horizon_È(p_177141_1_, var9, ItemCameraTransforms.Â.Â);
            GlStateManager.Ê();
        }
    }
    
    @Override
    public boolean Â() {
        return false;
    }
}
