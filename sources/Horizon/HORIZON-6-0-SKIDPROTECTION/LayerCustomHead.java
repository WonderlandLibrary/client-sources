package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import com.mojang.authlib.GameProfile;

public class LayerCustomHead implements LayerRenderer
{
    private final ModelRenderer HorizonCode_Horizon_È;
    private static final String Â = "CL_00002422";
    
    public LayerCustomHead(final ModelRenderer p_i46120_1_) {
        this.HorizonCode_Horizon_È = p_i46120_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        final ItemStack var9 = p_177141_1_.ÂµÈ(3);
        if (var9 != null && var9.HorizonCode_Horizon_È() != null) {
            final Item_1028566121 var10 = var9.HorizonCode_Horizon_È();
            final Minecraft var11 = Minecraft.áŒŠà();
            GlStateManager.Çªà¢();
            if (p_177141_1_.Çªà¢()) {
                GlStateManager.Â(0.0f, 0.2f, 0.0f);
            }
            final boolean var12 = p_177141_1_ instanceof EntityVillager || (p_177141_1_ instanceof EntityZombie && ((EntityZombie)p_177141_1_).ÐƒÇŽà());
            if (!var12 && p_177141_1_.h_()) {
                final float var13 = 2.0f;
                final float var14 = 1.4f;
                GlStateManager.HorizonCode_Horizon_È(var14 / var13, var14 / var13, var14 / var13);
                GlStateManager.Â(0.0f, 16.0f * p_177141_8_, 0.0f);
            }
            this.HorizonCode_Horizon_È.Ý(0.0625f);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            if (var10 instanceof ItemBlock) {
                final float var13 = 0.625f;
                GlStateManager.Â(0.0f, -0.25f, 0.0f);
                GlStateManager.Â(180.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.HorizonCode_Horizon_È(var13, -var13, -var13);
                if (var12) {
                    GlStateManager.Â(0.0f, 0.1875f, 0.0f);
                }
                var11.ˆáƒ().HorizonCode_Horizon_È(p_177141_1_, var9, ItemCameraTransforms.Â.Ø­áŒŠá);
            }
            else if (var10 == Items.ˆ) {
                final float var13 = 1.1875f;
                GlStateManager.HorizonCode_Horizon_È(var13, -var13, -var13);
                if (var12) {
                    GlStateManager.Â(0.0f, 0.0625f, 0.0f);
                }
                GameProfile var15 = null;
                if (var9.£á()) {
                    final NBTTagCompound var16 = var9.Å();
                    if (var16.Â("SkullOwner", 10)) {
                        var15 = NBTUtil.HorizonCode_Horizon_È(var16.ˆÏ­("SkullOwner"));
                    }
                    else if (var16.Â("SkullOwner", 8)) {
                        var15 = TileEntitySkull.Â(new GameProfile((UUID)null, var16.áˆºÑ¢Õ("SkullOwner")));
                        var16.HorizonCode_Horizon_È("SkullOwner", NBTUtil.HorizonCode_Horizon_È(new NBTTagCompound(), var15));
                    }
                }
                TileEntitySkullRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-0.5f, 0.0f, -0.5f, EnumFacing.Â, 180.0f, var9.Ø(), var15, -1);
            }
            GlStateManager.Ê();
        }
    }
    
    @Override
    public boolean Â() {
        return true;
    }
}
