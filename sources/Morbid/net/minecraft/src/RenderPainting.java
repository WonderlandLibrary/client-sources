package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderPainting extends Render
{
    public void renderThePainting(final EntityPainting par1EntityPainting, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(par8, 0.0f, 1.0f, 0.0f);
        GL11.glEnable(32826);
        this.loadTexture("/art/kz.png");
        final EnumArt var10 = par1EntityPainting.art;
        final float var11 = 0.0625f;
        GL11.glScalef(var11, var11, var11);
        this.func_77010_a(par1EntityPainting, var10.sizeX, var10.sizeY, var10.offsetX, var10.offsetY);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    private void func_77010_a(final EntityPainting par1EntityPainting, final int par2, final int par3, final int par4, final int par5) {
        final float var6 = -par2 / 2.0f;
        final float var7 = -par3 / 2.0f;
        final float var8 = 0.5f;
        final float var9 = 0.75f;
        final float var10 = 0.8125f;
        final float var11 = 0.0f;
        final float var12 = 0.0625f;
        final float var13 = 0.75f;
        final float var14 = 0.8125f;
        final float var15 = 0.001953125f;
        final float var16 = 0.001953125f;
        final float var17 = 0.7519531f;
        final float var18 = 0.7519531f;
        final float var19 = 0.0f;
        final float var20 = 0.0625f;
        for (int var21 = 0; var21 < par2 / 16; ++var21) {
            for (int var22 = 0; var22 < par3 / 16; ++var22) {
                final float var23 = var6 + (var21 + 1) * 16;
                final float var24 = var6 + var21 * 16;
                final float var25 = var7 + (var22 + 1) * 16;
                final float var26 = var7 + var22 * 16;
                this.func_77008_a(par1EntityPainting, (var23 + var24) / 2.0f, (var25 + var26) / 2.0f);
                final float var27 = (par4 + par2 - var21 * 16) / 256.0f;
                final float var28 = (par4 + par2 - (var21 + 1) * 16) / 256.0f;
                final float var29 = (par5 + par3 - var22 * 16) / 256.0f;
                final float var30 = (par5 + par3 - (var22 + 1) * 16) / 256.0f;
                final Tessellator var31 = Tessellator.instance;
                var31.startDrawingQuads();
                var31.setNormal(0.0f, 0.0f, -1.0f);
                var31.addVertexWithUV(var23, var26, -var8, var28, var29);
                var31.addVertexWithUV(var24, var26, -var8, var27, var29);
                var31.addVertexWithUV(var24, var25, -var8, var27, var30);
                var31.addVertexWithUV(var23, var25, -var8, var28, var30);
                var31.setNormal(0.0f, 0.0f, 1.0f);
                var31.addVertexWithUV(var23, var25, var8, var9, var11);
                var31.addVertexWithUV(var24, var25, var8, var10, var11);
                var31.addVertexWithUV(var24, var26, var8, var10, var12);
                var31.addVertexWithUV(var23, var26, var8, var9, var12);
                var31.setNormal(0.0f, 1.0f, 0.0f);
                var31.addVertexWithUV(var23, var25, -var8, var13, var15);
                var31.addVertexWithUV(var24, var25, -var8, var14, var15);
                var31.addVertexWithUV(var24, var25, var8, var14, var16);
                var31.addVertexWithUV(var23, var25, var8, var13, var16);
                var31.setNormal(0.0f, -1.0f, 0.0f);
                var31.addVertexWithUV(var23, var26, var8, var13, var15);
                var31.addVertexWithUV(var24, var26, var8, var14, var15);
                var31.addVertexWithUV(var24, var26, -var8, var14, var16);
                var31.addVertexWithUV(var23, var26, -var8, var13, var16);
                var31.setNormal(-1.0f, 0.0f, 0.0f);
                var31.addVertexWithUV(var23, var25, var8, var18, var19);
                var31.addVertexWithUV(var23, var26, var8, var18, var20);
                var31.addVertexWithUV(var23, var26, -var8, var17, var20);
                var31.addVertexWithUV(var23, var25, -var8, var17, var19);
                var31.setNormal(1.0f, 0.0f, 0.0f);
                var31.addVertexWithUV(var24, var25, -var8, var18, var19);
                var31.addVertexWithUV(var24, var26, -var8, var18, var20);
                var31.addVertexWithUV(var24, var26, var8, var17, var20);
                var31.addVertexWithUV(var24, var25, var8, var17, var19);
                var31.draw();
            }
        }
    }
    
    private void func_77008_a(final EntityPainting par1EntityPainting, final float par2, final float par3) {
        int var4 = MathHelper.floor_double(par1EntityPainting.posX);
        final int var5 = MathHelper.floor_double(par1EntityPainting.posY + par3 / 16.0f);
        int var6 = MathHelper.floor_double(par1EntityPainting.posZ);
        if (par1EntityPainting.hangingDirection == 2) {
            var4 = MathHelper.floor_double(par1EntityPainting.posX + par2 / 16.0f);
        }
        if (par1EntityPainting.hangingDirection == 1) {
            var6 = MathHelper.floor_double(par1EntityPainting.posZ - par2 / 16.0f);
        }
        if (par1EntityPainting.hangingDirection == 0) {
            var4 = MathHelper.floor_double(par1EntityPainting.posX - par2 / 16.0f);
        }
        if (par1EntityPainting.hangingDirection == 3) {
            var6 = MathHelper.floor_double(par1EntityPainting.posZ + par2 / 16.0f);
        }
        final int var7 = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(var4, var5, var6, 0);
        final int var8 = var7 % 65536;
        final int var9 = var7 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8, var9);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderThePainting((EntityPainting)par1Entity, par2, par4, par6, par8, par9);
    }
}
