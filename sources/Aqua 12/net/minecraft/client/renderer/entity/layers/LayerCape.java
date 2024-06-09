// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.EntityLivingBase;
import java.awt.image.BufferedImage;
import intent.AquaDev.aqua.cape.GIF;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.Aqua;
import de.liquiddev.ircclient.client.IrcPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;

public class LayerCape implements LayerRenderer<AbstractClientPlayer>
{
    private final RenderPlayer playerRenderer;
    Minecraft mc;
    
    public LayerCape(final RenderPlayer playerRendererIn) {
        this.mc = Minecraft.getMinecraft();
        this.playerRenderer = playerRendererIn;
    }
    
    @Override
    public void doRenderLayer(final AbstractClientPlayer entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        final String s = entitylivingbaseIn.getNameClear();
        if (IrcPlayer.isClientUser(s)) {
            final IrcPlayer player = IrcPlayer.getByIngameName(s);
            if (player.hasCape() && Aqua.moduleManager.getModuleByName("CustomCapes").isToggled()) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                final GIF gif = Aqua.setmgr.getSetting("CustomCapesMode").getCurrentMode().equalsIgnoreCase("Rias") ? Aqua.INSTANCE.GIFmgr.getGIFByName("rias") : (Aqua.setmgr.getSetting("CustomCapesMode").getCurrentMode().equalsIgnoreCase("Rias2") ? Aqua.INSTANCE.GIFmgr.getGIFByName("rias2") : (Aqua.setmgr.getSetting("CustomCapesMode").getCurrentMode().equalsIgnoreCase("Aqua") ? Aqua.INSTANCE.GIFmgr.getGIFByName("aqua") : Aqua.INSTANCE.GIFmgr.getGIFByName("anime")));
                this.mc.getTextureManager().bindTexture(gif.getNext().getLocation());
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0f, 0.0f, 0.125f);
                final double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks);
                final double d2 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks);
                final double d3 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks);
                final float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
                final double d4 = MathHelper.sin(f * 3.1415927f / 180.0f);
                final double d5 = -MathHelper.cos(f * 3.1415927f / 180.0f);
                float f2 = (float)d2 * 10.0f;
                f2 = MathHelper.clamp_float(f2, -6.0f, 32.0f);
                float f3 = (float)(d0 * d4 + d3 * d5) * 100.0f;
                final float f4 = (float)(d0 * d5 - d3 * d4) * 100.0f;
                if (f3 < 0.0f) {
                    f3 = 0.0f;
                }
                if (f3 > 165.0f) {
                    f3 = 165.0f;
                }
                final float f5 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
                f2 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0f) * 32.0f * f5;
                if (entitylivingbaseIn.isSneaking()) {
                    f2 += 25.0f;
                    GlStateManager.translate(0.0f, 0.142f, -0.0178f);
                }
                GlStateManager.rotate(6.0f + f3 / 2.0f + f2, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(f4 / 2.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(-f4 / 2.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                final BufferedImage image = gif.getNext().getImage();
                final float width = image.getWidth() * (12.0f / image.getWidth());
                final float height = image.getHeight() * (18.0f / image.getHeight());
                this.playerRenderer.getMainModel().renderCustomCape(0.0625f, (int)width, (int)height);
                GlStateManager.popMatrix();
            }
        }
        else if (entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.getLocationCape() != null) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationCape());
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 0.0f, 0.125f);
            final double d6 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks);
            final double d7 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks);
            final double d8 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks);
            final float f6 = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
            final double d9 = MathHelper.sin(f6 * 3.1415927f / 180.0f);
            final double d10 = -MathHelper.cos(f6 * 3.1415927f / 180.0f);
            float f7 = (float)d7 * 10.0f;
            f7 = MathHelper.clamp_float(f7, -6.0f, 32.0f);
            float f8 = (float)(d6 * d9 + d8 * d10) * 100.0f;
            final float f9 = (float)(d6 * d10 - d8 * d9) * 100.0f;
            if (f8 < 0.0f) {
                f8 = 0.0f;
            }
            final float f10 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
            f7 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0f) * 32.0f * f10;
            if (entitylivingbaseIn.isSneaking()) {
                f7 += 25.0f;
            }
            GlStateManager.rotate(6.0f + f8 / 2.0f + f7, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(f9 / 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(-f9 / 2.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            this.playerRenderer.getMainModel().renderCape(0.0625f);
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
