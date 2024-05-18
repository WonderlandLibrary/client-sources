package wwe;

import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.inventory.*;
import wwe.modules.exploits.*;
import net.minecraft.client.renderer.*;
import wwe.utils.*;
import org.lwjgl.opengl.*;
import net.minecraft.nbt.*;
import net.minecraft.world.storage.*;
import net.minecraft.tileentity.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import javax.imageio.*;
import javax.net.ssl.*;
import java.net.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;

public class FIwW
{
    public FIwW() {
        super();
    }
    
    public static void SitR(final ItemStack itemStack, @Nullable final World world, final int n, final int n2, final boolean b) {
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
            final NBTTagCompound compoundTag = tagCompound.getCompoundTag("BlockEntityTag");
            if (compoundTag.hasKey("Items", 9)) {
                final NonNullList withSize = NonNullList.withSize(27, (Object)ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundTag, withSize);
                final int n3 = 0;
                if (((aKVA)vlwv.hNxW.qHPF((Class<Peek>)aKVA.class)).IuiN()) {
                    if (vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).mode.OnGi().equalsIgnoreCase("Minecraft")) {
                        GlStateManager.enableBlend();
                        GlStateManager.disableRescaleNormal();
                        RenderHelper.disableStandardItemLighting();
                        GlStateManager.disableLighting();
                        GlStateManager.disableDepth();
                        Wrapper.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/shulker_box.png"));
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        Wrapper.mc.ingameGUI.drawTexturedModalRect(n - 8, n2 + 1, 0, 0, 176, 76);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                    if (vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).mode.OnGi().equalsIgnoreCase("WWE")) {
                        GlStateManager.enableBlend();
                        GlStateManager.disableRescaleNormal();
                        RenderHelper.disableStandardItemLighting();
                        GlStateManager.disableLighting();
                        GlStateManager.disableDepth();
                        gnNa.axgH(n - 2, n2 + 15, n + 162, n2 + 73, -1342177280, -4390912);
                    }
                    GL11.glPushMatrix();
                    GL11.glDepthMask(true);
                    GlStateManager.clear(256);
                    GlStateManager.disableDepth();
                    GlStateManager.enableDepth();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.scale(1.0f, 1.0f, 0.0f);
                    final int size = withSize.size();
                    int i = 0;
                    while (i < size) {
                        Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)withSize.get(i), n + i % 9 * 18, n3 * 18 + (i / 9 + 1) * 18 + n2 + 1);
                        Wrapper.mc.getRenderItem().renderItemOverlays(Wrapper.fontRenderer, (ItemStack)withSize.get(i), n + i % 9 * 18, n3 * 18 + (i / 9 + 1) * 18 + n2 + 1);
                        ++i;
                        if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                            throw null;
                        }
                    }
                    GlStateManager.scale(1.0f, 1.0f, 1.0f);
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.enableAlpha();
                    GlStateManager.disableBlend();
                    GlStateManager.disableLighting();
                    GlStateManager.scale(0.5, 0.5, 0.5);
                    GlStateManager.disableDepth();
                    GlStateManager.enableDepth();
                    GlStateManager.scale(2.0f, 2.0f, 2.0f);
                    GL11.glPopMatrix();
                }
            }
            if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                throw null;
            }
        }
        else {
            if (vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).mode.OnGi().equalsIgnoreCase("Minecraft")) {
                GlStateManager.enableBlend();
                GlStateManager.disableRescaleNormal();
                RenderHelper.disableStandardItemLighting();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                Wrapper.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/shulker_box.png"));
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                Wrapper.mc.ingameGUI.drawTexturedModalRect(n - 8, n2 + 1, 0, 0, 176, 76);
            }
            if (vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).mode.OnGi().equalsIgnoreCase("WWE")) {
                if (b) {
                    xtEK();
                }
                gnNa.axgH(n - 2, n2 + 15, n + 162, n2 + 73, -1342177280, -4390912);
                if (b) {
                    arlI();
                }
            }
        }
    }
    
    public static void rRds(final MapData mapData, final int n, final int n2, final boolean b) {
        final ResourceLocation ycXe = YcXe(mapData.mapName);
        if (ycXe == null) {
            return;
        }
        if (b) {
            xtEK();
        }
        Wrapper.mc.renderEngine.bindTexture(ycXe);
        GlStateManager.pushMatrix();
        GL11.glScalef(0.2f, 0.2f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Wrapper.mc.ingameGUI.drawTexturedModalRect(n * 5, n2 * 7, 0, 0, 256, 256);
        GlStateManager.popMatrix();
        if (b) {
            arlI();
        }
    }
    
    public static ResourceLocation YcXe(final String s) {
        try {
            if (!yYGD.PZBQ().keySet().contains(new ResourceLocation(s))) {
                Wrapper.mc.world.loadData((Class)MapData.class, s);
            }
            if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                throw null;
            }
        }
        catch (Exception ex) {}
        return yYGD.PZBQ().keySet().stream().filter(FIwW::AaBX).findFirst().orElse(null);
    }
    
    public static void xtEK() {
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableRescaleNormal();
    }
    
    public static void arlI() {
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }
    
    public static float WwmM(final Entity entity) {
        final float n = (float)(Wrapper.mc.player.posX - entity.posX);
        final float n2 = (float)(Wrapper.mc.player.posZ - entity.posZ);
        return MathHelper.sqrt(n * n + n2 * n2);
    }
    
    public static float tLFP(final TileEntity tileEntity) {
        final float n = (float)(Wrapper.mc.player.posX - tileEntity.getPos().getX());
        final float n2 = (float)(Wrapper.mc.player.posZ - tileEntity.getPos().getZ());
        return MathHelper.sqrt(n * n + n2 * n2);
    }
    
    public static ResourceLocation dkaH(final BufferedImage bufferedImage, final String s) {
        try {
            return Wrapper.mc.getRenderManager().renderEngine.getDynamicTextureLocation(s, new DynamicTexture(bufferedImage));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static ResourceLocation MPCo(final File file) {
        try {
            if (file != null && file.exists()) {
                return dkaH(ImageIO.read(file), file.getName());
            }
            if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static BufferedImage vdYN(final String s) {
        try {
            final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL(s).openConnection();
            httpsURLConnection.setRequestProperty("User-Agent", "WWE CLIENT BEST CLIENT USER-AGENT FOR CURRENT USERS");
            return ImageIO.read(httpsURLConnection.getInputStream());
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static ResourceLocation EfTQ(final String s, final String s2) {
        try {
            return dkaH(vdYN(s), s2);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static ArrayList<String> DDuY(final String s, final int n) {
        final ArrayList<String> list = new ArrayList<String>((s.length() + n - 1) / n);
        int i = 0;
        while (i < s.length()) {
            list.add(s.substring(i, Math.min(s.length(), i + n)));
            i += n;
            if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                throw null;
            }
        }
        return list;
    }
    
    public static String YCeF(final ArrayList<String> list) {
        String s = "";
        for (final String s2 : list) {
            if (s2.length() > s.length()) {
                s = s2;
            }
            if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                throw null;
            }
        }
        return s;
    }
    
    public static boolean ecXb(final BlockPos blockPos) {
        boolean b;
        if (Wrapper.mc.world.rayTraceBlocks(new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ), new Vec3d((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ())) == null) {
            b = true;
            if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public static EnumFacing FWlE(final BlockPos blockPos) {
        EnumFacing enumFacing = null;
        final EnumFacing[] array = EnumFacing.values();
        final Vec3d vec3d = new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ);
        final Vec3d center = Wrapper.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)Wrapper.mc.world, blockPos).offset(blockPos).offset((double)(-blockPos.getX()), (double)(-blockPos.getY()), (double)(-blockPos.getZ())).getCenter();
        final Vec3d add = new Vec3d((Vec3i)blockPos).add(center);
        final Vec3d[] array2 = new Vec3d[array.length];
        int i = 0;
        while (i < array.length) {
            final Vec3i directionVec = array[i].getDirectionVec();
            array2[i] = add.add(new Vec3d(center.x * directionVec.getX(), center.y * directionVec.getY(), center.z * directionVec.getZ()));
            ++i;
            if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                throw null;
            }
        }
        int j = 0;
        while (j < array.length) {
            if (Wrapper.mc.world.rayTraceBlocks(vec3d, array2[j], false, true, false) == null) {
                enumFacing = array[j];
                if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                    throw null;
                }
                break;
            }
            else {
                ++j;
                if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                    throw null;
                }
                continue;
            }
        }
        if (enumFacing == null) {
            final double squareDistanceTo = vec3d.squareDistanceTo(add);
            int k = 0;
            while (k < array.length) {
                if (vec3d.squareDistanceTo(array2[k]) < squareDistanceTo) {
                    enumFacing = array[k];
                    if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++k;
                    if (FIwW.\u2008\u200c\u2001\u2002\u200c\u2000\u2008) {
                        throw null;
                    }
                    continue;
                }
            }
        }
        return enumFacing;
    }
    
    public static boolean Bvtk(final EntityLivingBase entityLivingBase, final boolean b, final boolean b2, final boolean b3, final boolean b4, final boolean b5, final double n) {
        if (entityLivingBase instanceof EntityPlayerSP) {
            return false;
        }
        if (b && entityLivingBase instanceof EntityPlayer && !vlwv.Zqnd().UsVJ(entityLivingBase.getName()) && Wrapper.mc.player.getDistance((Entity)entityLivingBase) <= n && !entityLivingBase.isDead && entityLivingBase.getHealth() > 0.0 && !entityLivingBase.equals((Object)Wrapper.mc.player.getRidingEntity())) {
            return !entityLivingBase.isInvisible() && (b5 || Wrapper.mc.player.canEntityBeSeen((Entity)entityLivingBase));
        }
        if (b2 && entityLivingBase instanceof EntityAnimal && Wrapper.mc.player.getDistance((Entity)entityLivingBase) <= n && !entityLivingBase.isDead && entityLivingBase.getHealth() > 0.0 && !entityLivingBase.equals((Object)Wrapper.mc.player.getRidingEntity())) {
            return !entityLivingBase.isInvisible() && (b5 || Wrapper.mc.player.canEntityBeSeen((Entity)entityLivingBase));
        }
        return b3 && entityLivingBase instanceof EntityLivingBase && !(entityLivingBase instanceof EntityPlayer) && !(entityLivingBase instanceof EntityAnimal) && Wrapper.mc.player.getDistance((Entity)entityLivingBase) <= n && !entityLivingBase.isDead && entityLivingBase.getHealth() > 0.0 && !entityLivingBase.equals((Object)Wrapper.mc.player.getRidingEntity()) && !entityLivingBase.isInvisible() && (b5 || Wrapper.mc.player.canEntityBeSeen((Entity)entityLivingBase));
    }
    
    private static boolean AaBX(final String s, final ResourceLocation resourceLocation) {
        return resourceLocation.getPath().contains(s);
    }
}
