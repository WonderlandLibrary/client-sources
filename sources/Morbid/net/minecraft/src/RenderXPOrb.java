package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderXPOrb extends Render
{
    public RenderXPOrb() {
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    public void renderTheXPOrb(final EntityXPOrb par1EntityXPOrb, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        final int var10 = par1EntityXPOrb.getTextureByXP();
        this.loadTexture("/item/xporb.png");
        final Tessellator var11 = Tessellator.instance;
        final float var12 = (var10 % 4 * 16 + 0) / 64.0f;
        final float var13 = (var10 % 4 * 16 + 16) / 64.0f;
        final float var14 = (var10 / 4 * 16 + 0) / 64.0f;
        final float var15 = (var10 / 4 * 16 + 16) / 64.0f;
        final float var16 = 1.0f;
        final float var17 = 0.5f;
        final float var18 = 0.25f;
        final int var19 = par1EntityXPOrb.getBrightnessForRender(par9);
        final int var20 = var19 % 65536;
        int var21 = var19 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var20 / 1.0f, var21 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var22 = 255.0f;
        final float var23 = (par1EntityXPOrb.xpColor + par9) / 2.0f;
        var21 = (int)((MathHelper.sin(var23 + 0.0f) + 1.0f) * 0.5f * var22);
        final int var24 = (int)var22;
        final int var25 = (int)((MathHelper.sin(var23 + 4.1887903f) + 1.0f) * 0.1f * var22);
        final int var26 = var21 << 16 | var24 << 8 | var25;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        final float var27 = 0.3f;
        GL11.glScalef(var27, var27, var27);
        var11.startDrawingQuads();
        var11.setColorRGBA_I(var26, 128);
        var11.setNormal(0.0f, 1.0f, 0.0f);
        var11.addVertexWithUV(0.0f - var17, 0.0f - var18, 0.0, var12, var15);
        var11.addVertexWithUV(var16 - var17, 0.0f - var18, 0.0, var13, var15);
        var11.addVertexWithUV(var16 - var17, 1.0f - var18, 0.0, var13, var14);
        var11.addVertexWithUV(0.0f - var17, 1.0f - var18, 0.0, var12, var14);
        var11.draw();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderTheXPOrb((EntityXPOrb)par1Entity, par2, par4, par6, par8, par9);
    }
}
