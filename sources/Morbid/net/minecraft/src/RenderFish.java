package net.minecraft.src;

import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class RenderFish extends Render
{
    public void doRenderFishHook(final EntityFishHook par1EntityFishHook, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(32826);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        final byte var10 = 1;
        final byte var11 = 2;
        this.loadTexture("/particles.png");
        final Tessellator var12 = Tessellator.instance;
        final float var13 = (var10 * 8 + 0) / 128.0f;
        final float var14 = (var10 * 8 + 8) / 128.0f;
        final float var15 = (var11 * 8 + 0) / 128.0f;
        final float var16 = (var11 * 8 + 8) / 128.0f;
        final float var17 = 1.0f;
        final float var18 = 0.5f;
        final float var19 = 0.5f;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        var12.startDrawingQuads();
        var12.setNormal(0.0f, 1.0f, 0.0f);
        var12.addVertexWithUV(0.0f - var18, 0.0f - var19, 0.0, var13, var16);
        var12.addVertexWithUV(var17 - var18, 0.0f - var19, 0.0, var14, var16);
        var12.addVertexWithUV(var17 - var18, 1.0f - var19, 0.0, var14, var15);
        var12.addVertexWithUV(0.0f - var18, 1.0f - var19, 0.0, var13, var15);
        var12.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        if (par1EntityFishHook.angler != null) {
            final float var20 = par1EntityFishHook.angler.getSwingProgress(par9);
            final float var21 = MathHelper.sin(MathHelper.sqrt_float(var20) * 3.1415927f);
            final Vec3 var22 = par1EntityFishHook.worldObj.getWorldVec3Pool().getVecFromPool(-0.5, 0.03, 0.8);
            var22.rotateAroundX(-(par1EntityFishHook.angler.prevRotationPitch + (par1EntityFishHook.angler.rotationPitch - par1EntityFishHook.angler.prevRotationPitch) * par9) * 3.1415927f / 180.0f);
            var22.rotateAroundY(-(par1EntityFishHook.angler.prevRotationYaw + (par1EntityFishHook.angler.rotationYaw - par1EntityFishHook.angler.prevRotationYaw) * par9) * 3.1415927f / 180.0f);
            var22.rotateAroundY(var21 * 0.5f);
            var22.rotateAroundX(-var21 * 0.7f);
            double var23 = par1EntityFishHook.angler.prevPosX + (par1EntityFishHook.angler.posX - par1EntityFishHook.angler.prevPosX) * par9 + var22.xCoord;
            double var24 = par1EntityFishHook.angler.prevPosY + (par1EntityFishHook.angler.posY - par1EntityFishHook.angler.prevPosY) * par9 + var22.yCoord;
            double var25 = par1EntityFishHook.angler.prevPosZ + (par1EntityFishHook.angler.posZ - par1EntityFishHook.angler.prevPosZ) * par9 + var22.zCoord;
            final EntityPlayer angler = par1EntityFishHook.angler;
            Minecraft.getMinecraft();
            final double var26 = (angler != Minecraft.thePlayer) ? par1EntityFishHook.angler.getEyeHeight() : 0.0;
            Label_0738: {
                if (this.renderManager.options.thirdPersonView <= 0) {
                    final EntityPlayer angler2 = par1EntityFishHook.angler;
                    Minecraft.getMinecraft();
                    if (angler2 == Minecraft.thePlayer) {
                        break Label_0738;
                    }
                }
                final float var27 = (par1EntityFishHook.angler.prevRenderYawOffset + (par1EntityFishHook.angler.renderYawOffset - par1EntityFishHook.angler.prevRenderYawOffset) * par9) * 3.1415927f / 180.0f;
                final double var28 = MathHelper.sin(var27);
                final double var29 = MathHelper.cos(var27);
                var23 = par1EntityFishHook.angler.prevPosX + (par1EntityFishHook.angler.posX - par1EntityFishHook.angler.prevPosX) * par9 - var29 * 0.35 - var28 * 0.85;
                var24 = par1EntityFishHook.angler.prevPosY + var26 + (par1EntityFishHook.angler.posY - par1EntityFishHook.angler.prevPosY) * par9 - 0.45;
                var25 = par1EntityFishHook.angler.prevPosZ + (par1EntityFishHook.angler.posZ - par1EntityFishHook.angler.prevPosZ) * par9 - var28 * 0.35 + var29 * 0.85;
            }
            final double var30 = par1EntityFishHook.prevPosX + (par1EntityFishHook.posX - par1EntityFishHook.prevPosX) * par9;
            final double var31 = par1EntityFishHook.prevPosY + (par1EntityFishHook.posY - par1EntityFishHook.prevPosY) * par9 + 0.25;
            final double var32 = par1EntityFishHook.prevPosZ + (par1EntityFishHook.posZ - par1EntityFishHook.prevPosZ) * par9;
            final double var33 = (float)(var23 - var30);
            final double var34 = (float)(var24 - var31);
            final double var35 = (float)(var25 - var32);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            var12.startDrawing(3);
            var12.setColorOpaque_I(0);
            final byte var36 = 16;
            for (int var37 = 0; var37 <= var36; ++var37) {
                final float var38 = var37 / var36;
                var12.addVertex(par2 + var33 * var38, par4 + var34 * (var38 * var38 + var38) * 0.5 + 0.25, par6 + var35 * var38);
            }
            var12.draw();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderFishHook((EntityFishHook)par1Entity, par2, par4, par6, par8, par9);
    }
}
