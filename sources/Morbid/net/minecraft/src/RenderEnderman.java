package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class RenderEnderman extends RenderLiving
{
    private ModelEnderman endermanModel;
    private Random rnd;
    
    public RenderEnderman() {
        super(new ModelEnderman(), 0.5f);
        this.rnd = new Random();
        this.setRenderPassModel(this.endermanModel = (ModelEnderman)super.mainModel);
    }
    
    public void renderEnderman(final EntityEnderman par1EntityEnderman, double par2, final double par4, double par6, final float par8, final float par9) {
        this.endermanModel.isCarrying = (par1EntityEnderman.getCarried() > 0);
        this.endermanModel.isAttacking = par1EntityEnderman.isScreaming();
        if (par1EntityEnderman.isScreaming()) {
            final double var10 = 0.02;
            par2 += this.rnd.nextGaussian() * var10;
            par6 += this.rnd.nextGaussian() * var10;
        }
        super.doRenderLiving(par1EntityEnderman, par2, par4, par6, par8, par9);
    }
    
    protected void renderCarrying(final EntityEnderman par1EntityEnderman, final float par2) {
        super.renderEquippedItems(par1EntityEnderman, par2);
        if (par1EntityEnderman.getCarried() > 0) {
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            float var3 = 0.5f;
            GL11.glTranslatef(0.0f, 0.6875f, -0.75f);
            var3 *= 1.0f;
            GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(-var3, -var3, var3);
            final int var4 = par1EntityEnderman.getBrightnessForRender(par2);
            final int var5 = var4 % 65536;
            final int var6 = var4 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0f, var6 / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.loadTexture("/terrain.png");
            this.renderBlocks.renderBlockAsItem(Block.blocksList[par1EntityEnderman.getCarried()], par1EntityEnderman.getCarryingData(), 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable(32826);
        }
    }
    
    protected int renderEyes(final EntityEnderman par1EntityEnderman, final int par2, final float par3) {
        if (par2 != 0) {
            return -1;
        }
        this.loadTexture("/mob/enderman_eyes.png");
        final float var4 = 1.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(1, 1);
        GL11.glDisable(2896);
        if (par1EntityEnderman.isInvisible()) {
            GL11.glDepthMask(false);
        }
        else {
            GL11.glDepthMask(true);
        }
        final char var5 = '\uf0f0';
        final int var6 = var5 % 65536;
        final int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0f, var7 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, var4);
        return 1;
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.renderEyes((EntityEnderman)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.renderCarrying((EntityEnderman)par1EntityLiving, par2);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderEnderman((EntityEnderman)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderEnderman((EntityEnderman)par1Entity, par2, par4, par6, par8, par9);
    }
}
