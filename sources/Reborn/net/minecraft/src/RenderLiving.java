package net.minecraft.src;

import org.lwjgl.opengl.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import net.minecraft.client.*;
import java.util.*;

public class RenderLiving extends Render
{
    protected ModelBase mainModel;
    protected ModelBase renderPassModel;
    
    public RenderLiving(final ModelBase par1ModelBase, final float par2) {
        this.mainModel = par1ModelBase;
        this.shadowSize = par2;
    }
    
    public void setRenderPassModel(final ModelBase par1ModelBase) {
        this.renderPassModel = par1ModelBase;
    }
    
    private float interpolateRotation(final float par1, final float par2, final float par3) {
        float var4;
        for (var4 = par2 - par1; var4 < -180.0f; var4 += 360.0f) {}
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return par1 + par3 * var4;
    }
    
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        this.mainModel.onGround = this.renderSwingProgress(par1EntityLiving, par9);
        if (this.renderPassModel != null) {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }
        this.mainModel.isRiding = par1EntityLiving.isRiding();
        if (this.renderPassModel != null) {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }
        this.mainModel.isChild = par1EntityLiving.isChild();
        if (this.renderPassModel != null) {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }
        try {
            final float var10 = this.interpolateRotation(par1EntityLiving.prevRenderYawOffset, par1EntityLiving.renderYawOffset, par9);
            final float var11 = this.interpolateRotation(par1EntityLiving.prevRotationYawHead, par1EntityLiving.rotationYawHead, par9);
            final float var12 = par1EntityLiving.prevRotationPitch + (par1EntityLiving.rotationPitch - par1EntityLiving.prevRotationPitch) * par9;
            this.renderLivingAt(par1EntityLiving, par2, par4, par6);
            final float var13 = this.handleRotationFloat(par1EntityLiving, par9);
            this.rotateCorpse(par1EntityLiving, var13, var10, par9);
            final float var14 = 0.0625f;
            GL11.glEnable(32826);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            this.preRenderCallback(par1EntityLiving, par9);
            GL11.glTranslatef(0.0f, -24.0f * var14 - 0.0078125f, 0.0f);
            float var15 = par1EntityLiving.prevLimbYaw + (par1EntityLiving.limbYaw - par1EntityLiving.prevLimbYaw) * par9;
            float var16 = par1EntityLiving.limbSwing - par1EntityLiving.limbYaw * (1.0f - par9);
            if (par1EntityLiving.isChild()) {
                var16 *= 3.0f;
            }
            if (var15 > 1.0f) {
                var15 = 1.0f;
            }
            GL11.glEnable(3008);
            this.mainModel.setLivingAnimations(par1EntityLiving, var16, var15, par9);
            this.renderModel(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
            for (int var17 = 0; var17 < 4; ++var17) {
                final int var18 = this.shouldRenderPass(par1EntityLiving, var17, par9);
                if (var18 > 0) {
                    this.renderPassModel.setLivingAnimations(par1EntityLiving, var16, var15, par9);
                    this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                    if ((var18 & 0xF0) == 0x10) {
                        this.func_82408_c(par1EntityLiving, var17, par9);
                        this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                    }
                    if ((var18 & 0xF) == 0xF) {
                        final float var19 = par1EntityLiving.ticksExisted + par9;
                        this.loadTexture("%blur%/misc/glint.png");
                        GL11.glEnable(3042);
                        final float var20 = 0.5f;
                        GL11.glColor4f(var20, var20, var20, 1.0f);
                        GL11.glDepthFunc(514);
                        GL11.glDepthMask(false);
                        for (int var21 = 0; var21 < 2; ++var21) {
                            GL11.glDisable(2896);
                            final float var22 = 0.76f;
                            GL11.glColor4f(0.5f * var22, 0.25f * var22, 0.8f * var22, 1.0f);
                            GL11.glBlendFunc(768, 1);
                            GL11.glMatrixMode(5890);
                            GL11.glLoadIdentity();
                            final float var23 = var19 * (0.001f + var21 * 0.003f) * 20.0f;
                            final float var24 = 0.33333334f;
                            GL11.glScalef(var24, var24, var24);
                            GL11.glRotatef(30.0f - var21 * 60.0f, 0.0f, 0.0f, 1.0f);
                            GL11.glTranslatef(0.0f, var23, 0.0f);
                            GL11.glMatrixMode(5888);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glMatrixMode(5890);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(5888);
                        GL11.glEnable(2896);
                        GL11.glDisable(3042);
                        GL11.glDepthFunc(515);
                    }
                    GL11.glDisable(3042);
                    GL11.glEnable(3008);
                }
            }
            GL11.glDepthMask(true);
            this.renderEquippedItems(par1EntityLiving, par9);
            final float var25 = par1EntityLiving.getBrightness(par9);
            final int var18 = this.getColorMultiplier(par1EntityLiving, var25, par9);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(3553);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            if ((var18 >> 24 & 0xFF) > 0 || par1EntityLiving.hurtTime > 0 || par1EntityLiving.deathTime > 0) {
                GL11.glDisable(3553);
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if (par1EntityLiving.hurtTime > 0 || par1EntityLiving.deathTime > 0) {
                    GL11.glColor4f(var25, 0.0f, 0.0f, 0.4f);
                    this.mainModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                    for (int var21 = 0; var21 < 4; ++var21) {
                        if (this.inheritRenderPass(par1EntityLiving, var21, par9) >= 0) {
                            GL11.glColor4f(var25, 0.0f, 0.0f, 0.4f);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }
                    }
                }
                if ((var18 >> 24 & 0xFF) > 0) {
                    final float var19 = (var18 >> 16 & 0xFF) / 255.0f;
                    final float var20 = (var18 >> 8 & 0xFF) / 255.0f;
                    final float var26 = (var18 & 0xFF) / 255.0f;
                    final float var22 = (var18 >> 24 & 0xFF) / 255.0f;
                    GL11.glColor4f(var19, var20, var26, var22);
                    this.mainModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                    for (int var27 = 0; var27 < 4; ++var27) {
                        if (this.inheritRenderPass(par1EntityLiving, var27, par9) >= 0) {
                            GL11.glColor4f(var19, var20, var26, var22);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }
                    }
                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glEnable(3553);
            }
            GL11.glDisable(32826);
        }
        catch (Exception var28) {
            var28.printStackTrace();
        }
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        Morbid.getManager();
        if (ModManager.getMod("vanilla").isEnabled()) {
            this.passSpecialRender(par1EntityLiving, par2, par4, par6);
        }
    }
    
    protected void renderModel(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.func_98190_a(par1EntityLiving);
        if (!par1EntityLiving.isInvisible()) {
            this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
        }
        else {
            Minecraft.getMinecraft();
            if (!par1EntityLiving.func_98034_c(Minecraft.thePlayer)) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
                GL11.glDepthMask(false);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glAlphaFunc(516, 0.003921569f);
                this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
                GL11.glDisable(3042);
                GL11.glAlphaFunc(516, 0.1f);
                GL11.glPopMatrix();
                GL11.glDepthMask(true);
            }
            else {
                this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityLiving);
            }
        }
    }
    
    protected void func_98190_a(final EntityLiving par1EntityLiving) {
        this.loadTexture(par1EntityLiving.getTexture());
    }
    
    protected void renderLivingAt(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6) {
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
    }
    
    protected void rotateCorpse(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        GL11.glRotatef(180.0f - par3, 0.0f, 1.0f, 0.0f);
        if (par1EntityLiving.deathTime > 0) {
            float var5 = (par1EntityLiving.deathTime + par4 - 1.0f) / 20.0f * 1.6f;
            var5 = MathHelper.sqrt_float(var5);
            if (var5 > 1.0f) {
                var5 = 1.0f;
            }
            GL11.glRotatef(var5 * this.getDeathMaxRotation(par1EntityLiving), 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected float renderSwingProgress(final EntityLiving par1EntityLiving, final float par2) {
        return par1EntityLiving.getSwingProgress(par2);
    }
    
    protected float handleRotationFloat(final EntityLiving par1EntityLiving, final float par2) {
        return par1EntityLiving.ticksExisted + par2;
    }
    
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
    }
    
    protected void renderArrowsStuckInEntity(final EntityLiving par1EntityLiving, final float par2) {
        final int var3 = par1EntityLiving.getArrowCountInEntity();
        if (var3 > 0) {
            final EntityArrow var4 = new EntityArrow(par1EntityLiving.worldObj, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
            final Random var5 = new Random(par1EntityLiving.entityId);
            RenderHelper.disableStandardItemLighting();
            for (int var6 = 0; var6 < var3; ++var6) {
                GL11.glPushMatrix();
                final ModelRenderer var7 = this.mainModel.getRandomModelBox(var5);
                final ModelBox var8 = var7.cubeList.get(var5.nextInt(var7.cubeList.size()));
                var7.postRender(0.0625f);
                float var9 = var5.nextFloat();
                float var10 = var5.nextFloat();
                float var11 = var5.nextFloat();
                final float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0f;
                final float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0f;
                final float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0f;
                GL11.glTranslatef(var12, var13, var14);
                var9 = var9 * 2.0f - 1.0f;
                var10 = var10 * 2.0f - 1.0f;
                var11 = var11 * 2.0f - 1.0f;
                var9 *= -1.0f;
                var10 *= -1.0f;
                var11 *= -1.0f;
                final float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
                final EntityArrow entityArrow = var4;
                final EntityArrow entityArrow2 = var4;
                final float n = (float)(Math.atan2(var9, var11) * 180.0 / 3.141592653589793);
                entityArrow2.rotationYaw = n;
                entityArrow.prevRotationYaw = n;
                final EntityArrow entityArrow3 = var4;
                final EntityArrow entityArrow4 = var4;
                final float n2 = (float)(Math.atan2(var10, var15) * 180.0 / 3.141592653589793);
                entityArrow4.rotationPitch = n2;
                entityArrow3.prevRotationPitch = n2;
                final double var16 = 0.0;
                final double var17 = 0.0;
                final double var18 = 0.0;
                final float var19 = 0.0f;
                this.renderManager.renderEntityWithPosYaw(var4, var16, var17, var18, var19, par2);
                GL11.glPopMatrix();
            }
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    protected int inheritRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.shouldRenderPass(par1EntityLiving, par2, par3);
    }
    
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return -1;
    }
    
    protected void func_82408_c(final EntityLiving par1EntityLiving, final int par2, final float par3) {
    }
    
    protected float getDeathMaxRotation(final EntityLiving par1EntityLiving) {
        return 90.0f;
    }
    
    protected int getColorMultiplier(final EntityLiving par1EntityLiving, final float par2, final float par3) {
        return 0;
    }
    
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
    }
    
    protected void passSpecialRender(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6) {
        if (Minecraft.isGuiEnabled() && par1EntityLiving != this.renderManager.livingPlayer) {
            Minecraft.getMinecraft();
            if (!par1EntityLiving.func_98034_c(Minecraft.thePlayer) && (par1EntityLiving.func_94059_bO() || (par1EntityLiving.func_94056_bM() && par1EntityLiving == this.renderManager.field_96451_i))) {
                final float var8 = 1.6f;
                final float var9 = 0.016666668f * var8;
                final double var10 = par1EntityLiving.getDistanceSqToEntity(this.renderManager.livingPlayer);
                final float var11 = par1EntityLiving.isSneaking() ? 32.0f : 64.0f;
                if (var10 < var11 * var11) {
                    final String var12 = par1EntityLiving.getTranslatedEntityName();
                    if (par1EntityLiving.isSneaking()) {
                        final FontRenderer var13 = this.getFontRendererFromRenderManager();
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float)par2 + 0.0f, (float)par4 + par1EntityLiving.height + 0.5f, (float)par6);
                        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                        GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                        GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                        GL11.glScalef(-var9, -var9, var9);
                        GL11.glDisable(2896);
                        GL11.glTranslatef(0.0f, 0.25f / var9, 0.0f);
                        GL11.glDepthMask(false);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        final Tessellator var14 = Tessellator.instance;
                        GL11.glDisable(3553);
                        var14.startDrawingQuads();
                        final int var15 = var13.getStringWidth(var12) / 2;
                        var14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                        var14.addVertex(-var15 - 1, -1.0, 0.0);
                        var14.addVertex(-var15 - 1, 8.0, 0.0);
                        var14.addVertex(var15 + 1, 8.0, 0.0);
                        var14.addVertex(var15 + 1, -1.0, 0.0);
                        var14.draw();
                        GL11.glEnable(3553);
                        GL11.glDepthMask(true);
                        var13.drawString(var12, -var13.getStringWidth(var12) / 2, 0, 553648127);
                        GL11.glEnable(2896);
                        GL11.glDisable(3042);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glPopMatrix();
                    }
                    else {
                        this.func_96449_a(par1EntityLiving, par2, par4, par6, var12, var9, var10);
                    }
                }
            }
        }
    }
    
    protected void func_96449_a(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final String par8Str, final float par9, final double par10) {
        if (par1EntityLiving.isPlayerSleeping()) {
            this.renderLivingLabel(par1EntityLiving, par8Str, par2, par4 - 1.5, par6, 64);
        }
        else {
            this.renderLivingLabel(par1EntityLiving, par8Str, par2, par4, par6, 64);
        }
    }
    
    protected void renderLivingLabel(final EntityLiving par1EntityLiving, final String par2Str, final double par3, final double par5, final double par7, final int par9) {
        final double var10 = par1EntityLiving.getDistanceSqToEntity(this.renderManager.livingPlayer);
        if (var10 <= par9 * par9) {
            final FontRenderer var11 = this.getFontRendererFromRenderManager();
            final float var12 = 1.6f;
            final float var13 = 0.016666668f * var12;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par3 + 0.0f, (float)par5 + par1EntityLiving.height + 0.5f, (float)par7);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-var13, -var13, var13);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            final Tessellator var14 = Tessellator.instance;
            byte var15 = 0;
            if (par2Str.equals("deadmau5")) {
                var15 = -10;
            }
            GL11.glDisable(3553);
            var14.startDrawingQuads();
            final int var16 = var11.getStringWidth(par2Str) / 2;
            var14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
            var14.addVertex(-var16 - 1, -1 + var15, 0.0);
            var14.addVertex(-var16 - 1, 8 + var15, 0.0);
            var14.addVertex(var16 + 1, 8 + var15, 0.0);
            var14.addVertex(var16 + 1, -1 + var15, 0.0);
            var14.draw();
            GL11.glEnable(3553);
            var11.drawString(par2Str, -var11.getStringWidth(par2Str) / 2, var15, 553648127);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            var11.drawString(par2Str, -var11.getStringWidth(par2Str) / 2, var15, -1);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderLiving((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
    }
}
