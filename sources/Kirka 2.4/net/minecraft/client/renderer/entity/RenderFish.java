/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFish
extends Render {
    private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");
    private static final String __OBFID = "CL_00000996";

    public RenderFish(RenderManager p_i46175_1_) {
        super(p_i46175_1_);
    }

    public void func_180558_a(EntityFishHook p_180558_1_, double p_180558_2_, double p_180558_4_, double p_180558_6_, float p_180558_8_, float p_180558_9_) {
        block3 : {
            WorldRenderer var11;
            double var24;
            double var28;
            Tessellator var10;
            double var30;
            double var26;
            block5 : {
                block4 : {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float)p_180558_2_, (float)p_180558_4_, (float)p_180558_6_);
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    this.bindEntityTexture(p_180558_1_);
                    var10 = Tessellator.getInstance();
                    var11 = var10.getWorldRenderer();
                    int var12 = 1;
                    int var13 = 2;
                    float var14 = (float)(var12 * 8 + 0) / 128.0f;
                    float var15 = (float)(var12 * 8 + 8) / 128.0f;
                    float var16 = (float)(var13 * 8 + 0) / 128.0f;
                    float var17 = (float)(var13 * 8 + 8) / 128.0f;
                    float var18 = 1.0f;
                    float var19 = 0.5f;
                    float var20 = 0.5f;
                    GlStateManager.rotate(180.0f - RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    var11.startDrawingQuads();
                    var11.func_178980_d(0.0f, 1.0f, 0.0f);
                    var11.addVertexWithUV(0.0f - var19, 0.0f - var20, 0.0, var14, var17);
                    var11.addVertexWithUV(var18 - var19, 0.0f - var20, 0.0, var15, var17);
                    var11.addVertexWithUV(var18 - var19, 1.0f - var20, 0.0, var15, var16);
                    var11.addVertexWithUV(0.0f - var19, 1.0f - var20, 0.0, var14, var16);
                    var10.draw();
                    GlStateManager.disableRescaleNormal();
                    GlStateManager.popMatrix();
                    if (p_180558_1_.angler == null) break block3;
                    float var21 = p_180558_1_.angler.getSwingProgress(p_180558_9_);
                    float var22 = MathHelper.sin(MathHelper.sqrt_float(var21) * 3.1415927f);
                    Vec3 var23 = new Vec3(-0.36, 0.03, 0.35);
                    var23 = var23.rotatePitch(-(p_180558_1_.angler.prevRotationPitch + (p_180558_1_.angler.rotationPitch - p_180558_1_.angler.prevRotationPitch) * p_180558_9_) * 3.1415927f / 180.0f);
                    var23 = var23.rotateYaw(-(p_180558_1_.angler.prevRotationYaw + (p_180558_1_.angler.rotationYaw - p_180558_1_.angler.prevRotationYaw) * p_180558_9_) * 3.1415927f / 180.0f);
                    var23 = var23.rotateYaw(var22 * 0.5f);
                    var23 = var23.rotatePitch(-var22 * 0.7f);
                    var24 = p_180558_1_.angler.prevPosX + (p_180558_1_.angler.posX - p_180558_1_.angler.prevPosX) * (double)p_180558_9_ + var23.xCoord;
                    var26 = p_180558_1_.angler.prevPosY + (p_180558_1_.angler.posY - p_180558_1_.angler.prevPosY) * (double)p_180558_9_ + var23.yCoord;
                    var28 = p_180558_1_.angler.prevPosZ + (p_180558_1_.angler.posZ - p_180558_1_.angler.prevPosZ) * (double)p_180558_9_ + var23.zCoord;
                    var30 = p_180558_1_.angler.getEyeHeight();
                    if (this.renderManager.options != null && this.renderManager.options.thirdPersonView > 0) break block4;
                    Minecraft.getMinecraft();
                    if (p_180558_1_.angler == Minecraft.thePlayer) break block5;
                }
                float var32 = (p_180558_1_.angler.prevRenderYawOffset + (p_180558_1_.angler.renderYawOffset - p_180558_1_.angler.prevRenderYawOffset) * p_180558_9_) * 3.1415927f / 180.0f;
                double var33 = MathHelper.sin(var32);
                double var35 = MathHelper.cos(var32);
                double var37 = 0.35;
                double var39 = 0.8;
                var24 = p_180558_1_.angler.prevPosX + (p_180558_1_.angler.posX - p_180558_1_.angler.prevPosX) * (double)p_180558_9_ - var35 * 0.35 - var33 * 0.8;
                var26 = p_180558_1_.angler.prevPosY + var30 + (p_180558_1_.angler.posY - p_180558_1_.angler.prevPosY) * (double)p_180558_9_ - 0.45;
                var28 = p_180558_1_.angler.prevPosZ + (p_180558_1_.angler.posZ - p_180558_1_.angler.prevPosZ) * (double)p_180558_9_ - var33 * 0.35 + var35 * 0.8;
                var30 = p_180558_1_.angler.isSneaking() ? -0.1875 : 0.0;
            }
            double var47 = p_180558_1_.prevPosX + (p_180558_1_.posX - p_180558_1_.prevPosX) * (double)p_180558_9_;
            double var34 = p_180558_1_.prevPosY + (p_180558_1_.posY - p_180558_1_.prevPosY) * (double)p_180558_9_ + 0.25;
            double var36 = p_180558_1_.prevPosZ + (p_180558_1_.posZ - p_180558_1_.prevPosZ) * (double)p_180558_9_;
            double var38 = (float)(var24 - var47);
            double var40 = (double)((float)(var26 - var34)) + var30;
            double var42 = (float)(var28 - var36);
            GlStateManager.func_179090_x();
            GlStateManager.disableLighting();
            var11.startDrawing(3);
            var11.func_178991_c(0);
            int var44 = 16;
            for (int var45 = 0; var45 <= var44; ++var45) {
                float var46 = (float)var45 / (float)var44;
                var11.addVertex(p_180558_2_ + var38 * (double)var46, p_180558_4_ + var40 * (double)(var46 * var46 + var46) * 0.5 + 0.25, p_180558_6_ + var42 * (double)var46);
            }
            var10.draw();
            GlStateManager.enableLighting();
            GlStateManager.bindTexture();
            super.doRender(p_180558_1_, p_180558_2_, p_180558_4_, p_180558_6_, p_180558_8_, p_180558_9_);
        }
    }

    protected ResourceLocation getEntityTexture(EntityFishHook p_110775_1_) {
        return field_110792_a;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityFishHook)p_110775_1_);
    }

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.func_180558_a((EntityFishHook)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}

