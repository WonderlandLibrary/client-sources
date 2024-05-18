package net.minecraft.client.renderer.tileentity;

import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import com.mojang.authlib.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class TileEntityItemStackRenderer
{
    private TileEntityChest field_147717_b;
    private TileEntitySkull skull;
    private TileEntityChest field_147718_c;
    private static final String[] I;
    private TileEntityBanner banner;
    private TileEntityEnderChest enderChest;
    public static TileEntityItemStackRenderer instance;
    
    public TileEntityItemStackRenderer() {
        this.field_147717_b = new TileEntityChest("".length());
        this.field_147718_c = new TileEntityChest(" ".length());
        this.enderChest = new TileEntityEnderChest();
        this.banner = new TileEntityBanner();
        this.skull = new TileEntitySkull();
    }
    
    public void renderByItem(final ItemStack itemValues) {
        if (itemValues.getItem() == Items.banner) {
            this.banner.setItemValues(itemValues);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.banner, 0.0, 0.0, 0.0, 0.0f);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else if (itemValues.getItem() == Items.skull) {
            GameProfile gameProfile = null;
            if (itemValues.hasTagCompound()) {
                final NBTTagCompound tagCompound = itemValues.getTagCompound();
                if (tagCompound.hasKey(TileEntityItemStackRenderer.I["".length()], 0x71 ^ 0x7B)) {
                    gameProfile = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag(TileEntityItemStackRenderer.I[" ".length()]));
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else if (tagCompound.hasKey(TileEntityItemStackRenderer.I["  ".length()], 0x1 ^ 0x9) && tagCompound.getString(TileEntityItemStackRenderer.I["   ".length()]).length() > 0) {
                    gameProfile = TileEntitySkull.updateGameprofile(new GameProfile((UUID)null, tagCompound.getString(TileEntityItemStackRenderer.I[0x23 ^ 0x27])));
                    tagCompound.removeTag(TileEntityItemStackRenderer.I[0x0 ^ 0x5]);
                    tagCompound.setTag(TileEntityItemStackRenderer.I[0x74 ^ 0x72], NBTUtil.writeGameProfile(new NBTTagCompound(), gameProfile));
                }
            }
            if (TileEntitySkullRenderer.instance != null) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(-0.5f, 0.0f, -0.5f);
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                GlStateManager.disableCull();
                TileEntitySkullRenderer.instance.renderSkull(0.0f, 0.0f, 0.0f, EnumFacing.UP, 0.0f, itemValues.getMetadata(), gameProfile, -" ".length());
                GlStateManager.enableCull();
                GlStateManager.popMatrix();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
        }
        else {
            final Block blockFromItem = Block.getBlockFromItem(itemValues.getItem());
            if (blockFromItem == Blocks.ender_chest) {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.enderChest, 0.0, 0.0, 0.0, 0.0f);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (blockFromItem == Blocks.trapped_chest) {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147718_c, 0.0, 0.0, 0.0, 0.0f);
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147717_b, 0.0, 0.0, 0.0, 0.0f);
            }
        }
    }
    
    static {
        I();
        TileEntityItemStackRenderer.instance = new TileEntityItemStackRenderer();
    }
    
    private static void I() {
        (I = new String[0x1 ^ 0x6])["".length()] = I("\u0007>\u001a'\u0005\u001b\"\u0001.\u001b", "TUoKi");
        TileEntityItemStackRenderer.I[" ".length()] = I("'1\u0019\r';-\u0002\u00049", "tZlaK");
        TileEntityItemStackRenderer.I["  ".length()] = I("\u0019*\f=\u001a\u00056\u00174\u0004", "JAyQv");
        TileEntityItemStackRenderer.I["   ".length()] = I("&.\f\r\u001e:2\u0017\u0004\u0000", "uEyar");
        TileEntityItemStackRenderer.I[0x78 ^ 0x7C] = I("\u0000\u001e\f :\u001c\u0002\u0017)$", "SuyLV");
        TileEntityItemStackRenderer.I[0xB7 ^ 0xB2] = I("\n21\u0000$\u0016.*\t:", "YYDlH");
        TileEntityItemStackRenderer.I[0x8 ^ 0xE] = I("1\u0006\u001b\u0001\r-\u001a\u0000\b\u0013", "bmnma");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
