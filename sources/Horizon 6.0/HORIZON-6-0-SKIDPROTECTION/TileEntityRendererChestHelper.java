package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import com.mojang.authlib.GameProfile;

public class TileEntityRendererChestHelper
{
    public static TileEntityRendererChestHelper HorizonCode_Horizon_È;
    private TileEntityChest Â;
    private TileEntityChest Ý;
    private TileEntityEnderChest Ø­áŒŠá;
    private TileEntityBanner Âµá€;
    private TileEntitySkull Ó;
    private static final String à = "CL_00000946";
    
    static {
        TileEntityRendererChestHelper.HorizonCode_Horizon_È = new TileEntityRendererChestHelper();
    }
    
    public TileEntityRendererChestHelper() {
        this.Â = new TileEntityChest(0);
        this.Ý = new TileEntityChest(1);
        this.Ø­áŒŠá = new TileEntityEnderChest();
        this.Âµá€ = new TileEntityBanner();
        this.Ó = new TileEntitySkull();
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_179022_1_) {
        if (p_179022_1_.HorizonCode_Horizon_È() == Items.£Ç) {
            this.Âµá€.HorizonCode_Horizon_È(p_179022_1_);
            TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Âµá€, 0.0, 0.0, 0.0, 0.0f);
        }
        else if (p_179022_1_.HorizonCode_Horizon_È() == Items.ˆ) {
            GameProfile var2 = null;
            if (p_179022_1_.£á()) {
                final NBTTagCompound var3 = p_179022_1_.Å();
                if (var3.Â("SkullOwner", 10)) {
                    var2 = NBTUtil.HorizonCode_Horizon_È(var3.ˆÏ­("SkullOwner"));
                }
                else if (var3.Â("SkullOwner", 8) && var3.áˆºÑ¢Õ("SkullOwner").length() > 0) {
                    var2 = new GameProfile((UUID)null, var3.áˆºÑ¢Õ("SkullOwner"));
                    var2 = TileEntitySkull.Â(var2);
                    var3.Å("SkullOwner");
                    var3.HorizonCode_Horizon_È("SkullOwner", NBTUtil.HorizonCode_Horizon_È(new NBTTagCompound(), var2));
                }
            }
            if (TileEntitySkullRenderer.HorizonCode_Horizon_È != null) {
                GlStateManager.Çªà¢();
                GlStateManager.Â(-0.5f, 0.0f, -0.5f);
                GlStateManager.HorizonCode_Horizon_È(2.0f, 2.0f, 2.0f);
                GlStateManager.£à();
                TileEntitySkullRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, EnumFacing.Â, 0.0f, p_179022_1_.Ø(), var2, -1);
                GlStateManager.Å();
                GlStateManager.Ê();
            }
        }
        else {
            final Block var4 = Block.HorizonCode_Horizon_È(p_179022_1_.HorizonCode_Horizon_È());
            if (var4 == Blocks.¥áŒŠà) {
                TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ø­áŒŠá, 0.0, 0.0, 0.0, 0.0f);
            }
            else if (var4 == Blocks.ÇŽ) {
                TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý, 0.0, 0.0, 0.0, 0.0f);
            }
            else {
                TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, 0.0, 0.0, 0.0, 0.0f);
            }
        }
    }
}
