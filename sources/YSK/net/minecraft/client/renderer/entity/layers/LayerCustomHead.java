package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.init.*;
import com.mojang.authlib.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class LayerCustomHead implements LayerRenderer<EntityLivingBase>
{
    private static final String[] I;
    private final ModelRenderer field_177209_a;
    
    @Override
    public boolean shouldCombineTextures() {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x10 ^ 0x15])["".length()] = I("\u0005(\u001b\u001e!\u00194\u0000\u0017?", "VCnrM");
        LayerCustomHead.I[" ".length()] = I("\u0002\u0000\u001e.\u000b\u001e\u001c\u0005'\u0015", "QkkBg");
        LayerCustomHead.I["  ".length()] = I("!>\f)\u001d=\"\u0017 \u0003", "rUyEq");
        LayerCustomHead.I["   ".length()] = I("?'!9<#;:0\"", "lLTUP");
        LayerCustomHead.I[0x39 ^ 0x3D] = I("?\u000f\u001c9\u001a#\u0013\u00070\u0004", "ldiUv");
    }
    
    public LayerCustomHead(final ModelRenderer field_177209_a) {
        this.field_177209_a = field_177209_a;
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final ItemStack currentArmor = entityLivingBase.getCurrentArmor("   ".length());
        if (currentArmor != null && currentArmor.getItem() != null) {
            final Item item = currentArmor.getItem();
            final Minecraft minecraft = Minecraft.getMinecraft();
            GlStateManager.pushMatrix();
            if (entityLivingBase.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            int n8;
            if (!(entityLivingBase instanceof EntityVillager) && (!(entityLivingBase instanceof EntityZombie) || !((EntityZombie)entityLivingBase).isVillager())) {
                n8 = "".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            else {
                n8 = " ".length();
            }
            final int n9 = n8;
            if (n9 == 0 && entityLivingBase.isChild()) {
                final float n10 = 2.0f;
                final float n11 = 1.4f;
                GlStateManager.scale(n11 / n10, n11 / n10, n11 / n10);
                GlStateManager.translate(0.0f, 16.0f * n7, 0.0f);
            }
            this.field_177209_a.postRender(0.0625f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (item instanceof ItemBlock) {
                final float n12 = 0.625f;
                GlStateManager.translate(0.0f, -0.25f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.scale(n12, -n12, -n12);
                if (n9 != 0) {
                    GlStateManager.translate(0.0f, 0.1875f, 0.0f);
                }
                minecraft.getItemRenderer().renderItem(entityLivingBase, currentArmor, ItemCameraTransforms.TransformType.HEAD);
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else if (item == Items.skull) {
                final float n13 = 1.1875f;
                GlStateManager.scale(n13, -n13, -n13);
                if (n9 != 0) {
                    GlStateManager.translate(0.0f, 0.0625f, 0.0f);
                }
                GameProfile gameProfile = null;
                if (currentArmor.hasTagCompound()) {
                    final NBTTagCompound tagCompound = currentArmor.getTagCompound();
                    if (tagCompound.hasKey(LayerCustomHead.I["".length()], 0x3F ^ 0x35)) {
                        gameProfile = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag(LayerCustomHead.I[" ".length()]));
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else if (tagCompound.hasKey(LayerCustomHead.I["  ".length()], 0x90 ^ 0x98)) {
                        final String string = tagCompound.getString(LayerCustomHead.I["   ".length()]);
                        if (!StringUtils.isNullOrEmpty(string)) {
                            gameProfile = TileEntitySkull.updateGameprofile(new GameProfile((UUID)null, string));
                            tagCompound.setTag(LayerCustomHead.I[0x10 ^ 0x14], NBTUtil.writeGameProfile(new NBTTagCompound(), gameProfile));
                        }
                    }
                }
                TileEntitySkullRenderer.instance.renderSkull(-0.5f, 0.0f, -0.5f, EnumFacing.UP, 180.0f, currentArmor.getMetadata(), gameProfile, -" ".length());
            }
            GlStateManager.popMatrix();
        }
    }
    
    static {
        I();
    }
}
