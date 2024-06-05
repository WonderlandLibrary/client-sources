package net.minecraft.src;

import java.nio.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.client.*;

public class RenderEndPortal extends TileEntitySpecialRenderer
{
    FloatBuffer field_76908_a;
    
    public RenderEndPortal() {
        this.field_76908_a = GLAllocation.createDirectFloatBuffer(16);
    }
    
    public void renderEndPortalTileEntity(final TileEntityEndPortal par1TileEntityEndPortal, final double par2, final double par4, final double par6, final float par8) {
        final float var9 = (float)this.tileEntityRenderer.playerX;
        final float var10 = (float)this.tileEntityRenderer.playerY;
        final float var11 = (float)this.tileEntityRenderer.playerZ;
        GL11.glDisable(2896);
        final Random var12 = new Random(31100L);
        final float var13 = 0.75f;
        for (int var14 = 0; var14 < 16; ++var14) {
            GL11.glPushMatrix();
            float var15 = 16 - var14;
            float var16 = 0.0625f;
            float var17 = 1.0f / (var15 + 1.0f);
            if (var14 == 0) {
                this.bindTextureByName("/misc/tunnel.png");
                var17 = 0.1f;
                var15 = 65.0f;
                var16 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (var14 == 1) {
                this.bindTextureByName("/misc/particlefield.png");
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                var16 = 0.5f;
            }
            final float var18 = (float)(-(par4 + var13));
            float var19 = var18 + ActiveRenderInfo.objectY;
            final float var20 = var18 + var15 + ActiveRenderInfo.objectY;
            float var21 = var19 / var20;
            var21 += (float)(par4 + var13);
            GL11.glTranslatef(var9, var21, var11);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8192, 9473, this.func_76907_a(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen(8193, 9473, this.func_76907_a(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.func_76907_a(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.func_76907_a(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, Minecraft.getSystemTime() % 700000L / 700000.0f, 0.0f);
            GL11.glScalef(var16, var16, var16);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((var14 * var14 * 4321 + var14 * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-var9, -var11, -var10);
            var19 = var18 + ActiveRenderInfo.objectY;
            GL11.glTranslatef(ActiveRenderInfo.objectX * var15 / var19, ActiveRenderInfo.objectZ * var15 / var19, -var10);
            final Tessellator var22 = Tessellator.instance;
            var22.startDrawingQuads();
            var21 = var12.nextFloat() * 0.5f + 0.1f;
            float var23 = var12.nextFloat() * 0.5f + 0.4f;
            float var24 = var12.nextFloat() * 0.5f + 0.5f;
            if (var14 == 0) {
                var24 = 1.0f;
                var23 = 1.0f;
                var21 = 1.0f;
            }
            var22.setColorRGBA_F(var21 * var17, var23 * var17, var24 * var17, 1.0f);
            var22.addVertex(par2, par4 + var13, par6);
            var22.addVertex(par2, par4 + var13, par6 + 1.0);
            var22.addVertex(par2 + 1.0, par4 + var13, par6 + 1.0);
            var22.addVertex(par2 + 1.0, par4 + var13, par6);
            var22.draw();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }
    
    private FloatBuffer func_76907_a(final float par1, final float par2, final float par3, final float par4) {
        this.field_76908_a.clear();
        this.field_76908_a.put(par1).put(par2).put(par3).put(par4);
        this.field_76908_a.flip();
        return this.field_76908_a;
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderEndPortalTileEntity((TileEntityEndPortal)par1TileEntity, par2, par4, par6, par8);
    }
}
