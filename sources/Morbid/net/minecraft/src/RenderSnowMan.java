package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderSnowMan extends RenderLiving
{
    private ModelSnowMan snowmanModel;
    
    public RenderSnowMan() {
        super(new ModelSnowMan(), 0.5f);
        this.setRenderPassModel(this.snowmanModel = (ModelSnowMan)super.mainModel);
    }
    
    protected void renderSnowmanPumpkin(final EntitySnowman par1EntitySnowman, final float par2) {
        super.renderEquippedItems(par1EntitySnowman, par2);
        final ItemStack var3 = new ItemStack(Block.pumpkin, 1);
        if (var3 != null && var3.getItem().itemID < 256) {
            GL11.glPushMatrix();
            this.snowmanModel.head.postRender(0.0625f);
            if (RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType())) {
                final float var4 = 0.625f;
                GL11.glTranslatef(0.0f, -0.34375f, 0.0f);
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var4, -var4, var4);
            }
            this.renderManager.itemRenderer.renderItem(par1EntitySnowman, var3, 0);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.renderSnowmanPumpkin((EntitySnowman)par1EntityLiving, par2);
    }
}
