package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderMooshroom extends RenderLiving
{
    public RenderMooshroom(final ModelBase par1ModelBase, final float par2) {
        super(par1ModelBase, par2);
    }
    
    public void renderLivingMooshroom(final EntityMooshroom par1EntityMooshroom, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntityMooshroom, par2, par4, par6, par8, par9);
    }
    
    protected void renderMooshroomEquippedItems(final EntityMooshroom par1EntityMooshroom, final float par2) {
        super.renderEquippedItems(par1EntityMooshroom, par2);
        if (!par1EntityMooshroom.isChild()) {
            this.loadTexture("/terrain.png");
            GL11.glEnable(2884);
            GL11.glPushMatrix();
            GL11.glScalef(1.0f, -1.0f, 1.0f);
            GL11.glTranslatef(0.2f, 0.4f, 0.5f);
            GL11.glRotatef(42.0f, 0.0f, 1.0f, 0.0f);
            this.renderBlocks.renderBlockAsItem(Block.mushroomRed, 0, 1.0f);
            GL11.glTranslatef(0.1f, 0.0f, -0.6f);
            GL11.glRotatef(42.0f, 0.0f, 1.0f, 0.0f);
            this.renderBlocks.renderBlockAsItem(Block.mushroomRed, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            ((ModelQuadruped)this.mainModel).head.postRender(0.0625f);
            GL11.glScalef(1.0f, -1.0f, 1.0f);
            GL11.glTranslatef(0.0f, 0.75f, -0.2f);
            GL11.glRotatef(12.0f, 0.0f, 1.0f, 0.0f);
            this.renderBlocks.renderBlockAsItem(Block.mushroomRed, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable(2884);
        }
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.renderMooshroomEquippedItems((EntityMooshroom)par1EntityLiving, par2);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderLivingMooshroom((EntityMooshroom)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderLivingMooshroom((EntityMooshroom)par1Entity, par2, par4, par6, par8, par9);
    }
}
