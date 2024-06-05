package net.minecraft.client.renderer.tileentity;

import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class TileEntityRendererChestHelper
{
    public static TileEntityRendererChestHelper instance;
    private TileEntityChest field_147717_b;
    private TileEntityChest field_147718_c;
    private TileEntityEnderChest field_147716_d;
    private TileEntityBanner banner;
    private TileEntitySkull field_179023_f;
    
    public TileEntityRendererChestHelper() {
        this.field_147717_b = new TileEntityChest(0);
        this.field_147718_c = new TileEntityChest(1);
        this.field_147716_d = new TileEntityEnderChest();
        this.banner = new TileEntityBanner();
        this.field_179023_f = new TileEntitySkull();
    }
    
    public void renderByItem(final ItemStack p_179022_1_) {
        if (p_179022_1_.getItem() == Items.banner) {
            this.banner.setItemValues(p_179022_1_);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.banner, 0.0, 0.0, 0.0, 0.0f);
        }
        else if (p_179022_1_.getItem() == Items.skull) {
            GameProfile var2 = null;
            if (p_179022_1_.hasTagCompound()) {
                final NBTTagCompound var3 = p_179022_1_.getTagCompound();
                if (var3.hasKey("SkullOwner", 10)) {
                    var2 = NBTUtil.readGameProfileFromNBT(var3.getCompoundTag("SkullOwner"));
                }
                else if (var3.hasKey("SkullOwner", 8) && var3.getString("SkullOwner").length() > 0) {
                    var2 = new GameProfile((UUID)null, var3.getString("SkullOwner"));
                    var2 = TileEntitySkull.updateGameprofile(var2);
                    var3.removeTag("SkullOwner");
                    var3.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), var2));
                }
            }
            if (TileEntitySkullRenderer.instance != null) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(-0.5f, 0.0f, -0.5f);
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                GlStateManager.disableCull();
                TileEntitySkullRenderer.instance.renderSkull(0.0f, 0.0f, 0.0f, EnumFacing.UP, 0.0f, p_179022_1_.getMetadata(), var2, -1);
                GlStateManager.enableCull();
                GlStateManager.popMatrix();
            }
        }
        else {
            final Block var4 = Block.getBlockFromItem(p_179022_1_.getItem());
            if (var4 == Blocks.ender_chest) {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147716_d, 0.0, 0.0, 0.0, 0.0f);
            }
            else if (var4 == Blocks.trapped_chest) {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147718_c, 0.0, 0.0, 0.0, 0.0f);
            }
            else {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147717_b, 0.0, 0.0, 0.0, 0.0f);
            }
        }
    }
    
    static {
        TileEntityRendererChestHelper.instance = new TileEntityRendererChestHelper();
    }
}
