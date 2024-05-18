package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderIronGolem extends RenderLiving
{
    private ModelIronGolem ironGolemModel;
    
    public RenderIronGolem() {
        super(new ModelIronGolem(), 0.5f);
        this.ironGolemModel = (ModelIronGolem)this.mainModel;
    }
    
    public void doRenderIronGolem(final EntityIronGolem par1EntityIronGolem, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntityIronGolem, par2, par4, par6, par8, par9);
    }
    
    protected void rotateIronGolemCorpse(final EntityIronGolem par1EntityIronGolem, final float par2, final float par3, final float par4) {
        super.rotateCorpse(par1EntityIronGolem, par2, par3, par4);
        if (par1EntityIronGolem.limbYaw >= 0.01) {
            final float var5 = 13.0f;
            final float var6 = par1EntityIronGolem.limbSwing - par1EntityIronGolem.limbYaw * (1.0f - par4) + 6.0f;
            final float var7 = (Math.abs(var6 % var5 - var5 * 0.5f) - var5 * 0.25f) / (var5 * 0.25f);
            GL11.glRotatef(6.5f * var7, 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected void renderIronGolemEquippedItems(final EntityIronGolem par1EntityIronGolem, final float par2) {
        super.renderEquippedItems(par1EntityIronGolem, par2);
        if (par1EntityIronGolem.getHoldRoseTick() != 0) {
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            GL11.glRotatef(5.0f + 180.0f * this.ironGolemModel.ironGolemRightArm.rotateAngleX / 3.1415927f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(-0.6875f, 1.25f, -0.9375f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            final float var3 = 0.8f;
            GL11.glScalef(var3, -var3, var3);
            final int var4 = par1EntityIronGolem.getBrightnessForRender(par2);
            final int var5 = var4 % 65536;
            final int var6 = var4 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0f, var6 / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.loadTexture("/terrain.png");
            this.renderBlocks.renderBlockAsItem(Block.plantRed, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable(32826);
        }
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.renderIronGolemEquippedItems((EntityIronGolem)par1EntityLiving, par2);
    }
    
    @Override
    protected void rotateCorpse(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        this.rotateIronGolemCorpse((EntityIronGolem)par1EntityLiving, par2, par3, par4);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderIronGolem((EntityIronGolem)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderIronGolem((EntityIronGolem)par1Entity, par2, par4, par6, par8, par9);
    }
}
