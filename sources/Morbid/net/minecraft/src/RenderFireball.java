package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderFireball extends Render
{
    private float field_77002_a;
    
    public RenderFireball(final float par1) {
        this.field_77002_a = par1;
    }
    
    public void doRenderFireball(final EntityFireball par1EntityFireball, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(32826);
        final float var10 = this.field_77002_a;
        GL11.glScalef(var10 / 1.0f, var10 / 1.0f, var10 / 1.0f);
        final Icon var11 = Item.fireballCharge.getIconFromDamage(0);
        this.loadTexture("/gui/items.png");
        final Tessellator var12 = Tessellator.instance;
        final float var13 = var11.getMinU();
        final float var14 = var11.getMaxU();
        final float var15 = var11.getMinV();
        final float var16 = var11.getMaxV();
        final float var17 = 1.0f;
        final float var18 = 0.5f;
        final float var19 = 0.25f;
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
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderFireball((EntityFireball)par1Entity, par2, par4, par6, par8, par9);
    }
}
