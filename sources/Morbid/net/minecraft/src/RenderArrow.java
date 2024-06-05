package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderArrow extends Render
{
    public void renderArrow(final EntityArrow par1EntityArrow, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.loadTexture("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(par1EntityArrow.prevRotationYaw + (par1EntityArrow.rotationYaw - par1EntityArrow.prevRotationYaw) * par9 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(par1EntityArrow.prevRotationPitch + (par1EntityArrow.rotationPitch - par1EntityArrow.prevRotationPitch) * par9, 0.0f, 0.0f, 1.0f);
        final Tessellator var10 = Tessellator.instance;
        final byte var11 = 0;
        final float var12 = 0.0f;
        final float var13 = 0.5f;
        final float var14 = (0 + var11 * 10) / 32.0f;
        final float var15 = (5 + var11 * 10) / 32.0f;
        final float var16 = 0.0f;
        final float var17 = 0.15625f;
        final float var18 = (5 + var11 * 10) / 32.0f;
        final float var19 = (10 + var11 * 10) / 32.0f;
        final float var20 = 0.05625f;
        GL11.glEnable(32826);
        final float var21 = par1EntityArrow.arrowShake - par9;
        if (var21 > 0.0f) {
            final float var22 = -MathHelper.sin(var21 * 3.0f) * var21;
            GL11.glRotatef(var22, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(var20, var20, var20);
        GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(var20, 0.0f, 0.0f);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-7.0, -2.0, -2.0, var16, var18);
        var10.addVertexWithUV(-7.0, -2.0, 2.0, var17, var18);
        var10.addVertexWithUV(-7.0, 2.0, 2.0, var17, var19);
        var10.addVertexWithUV(-7.0, 2.0, -2.0, var16, var19);
        var10.draw();
        GL11.glNormal3f(-var20, 0.0f, 0.0f);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-7.0, 2.0, -2.0, var16, var18);
        var10.addVertexWithUV(-7.0, 2.0, 2.0, var17, var18);
        var10.addVertexWithUV(-7.0, -2.0, 2.0, var17, var19);
        var10.addVertexWithUV(-7.0, -2.0, -2.0, var16, var19);
        var10.draw();
        for (int var23 = 0; var23 < 4; ++var23) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, var20);
            var10.startDrawingQuads();
            var10.addVertexWithUV(-8.0, -2.0, 0.0, var12, var14);
            var10.addVertexWithUV(8.0, -2.0, 0.0, var13, var14);
            var10.addVertexWithUV(8.0, 2.0, 0.0, var13, var15);
            var10.addVertexWithUV(-8.0, 2.0, 0.0, var12, var15);
            var10.draw();
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderArrow((EntityArrow)par1Entity, par2, par4, par6, par8, par9);
    }
}
