package net.minecraft.src;

import org.lwjgl.opengl.*;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer
{
    public void renderTileEntityBeaconAt(final TileEntityBeacon par1TileEntityBeacon, final double par2, final double par4, final double par6, final float par8) {
        final float var9 = par1TileEntityBeacon.func_82125_v_();
        if (var9 > 0.0f) {
            final Tessellator var10 = Tessellator.instance;
            this.bindTextureByName("/misc/beam.png");
            GL11.glTexParameterf(3553, 10242, 10497.0f);
            GL11.glTexParameterf(3553, 10243, 10497.0f);
            GL11.glDisable(2896);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glBlendFunc(770, 1);
            final float var11 = par1TileEntityBeacon.getWorldObj().getTotalWorldTime() + par8;
            final float var12 = -var11 * 0.2f - MathHelper.floor_float(-var11 * 0.1f);
            final byte var13 = 1;
            final double var14 = var11 * 0.025 * (1.0 - (var13 & 0x1) * 2.5);
            var10.startDrawingQuads();
            var10.setColorRGBA(255, 255, 255, 32);
            final double var15 = var13 * 0.2;
            final double var16 = 0.5 + Math.cos(var14 + 2.356194490192345) * var15;
            final double var17 = 0.5 + Math.sin(var14 + 2.356194490192345) * var15;
            final double var18 = 0.5 + Math.cos(var14 + 0.7853981633974483) * var15;
            final double var19 = 0.5 + Math.sin(var14 + 0.7853981633974483) * var15;
            final double var20 = 0.5 + Math.cos(var14 + 3.9269908169872414) * var15;
            final double var21 = 0.5 + Math.sin(var14 + 3.9269908169872414) * var15;
            final double var22 = 0.5 + Math.cos(var14 + 5.497787143782138) * var15;
            final double var23 = 0.5 + Math.sin(var14 + 5.497787143782138) * var15;
            final double var24 = 256.0f * var9;
            final double var25 = 0.0;
            final double var26 = 1.0;
            final double var27 = -1.0f + var12;
            final double var28 = 256.0f * var9 * (0.5 / var15) + var27;
            var10.addVertexWithUV(par2 + var16, par4 + var24, par6 + var17, var26, var28);
            var10.addVertexWithUV(par2 + var16, par4, par6 + var17, var26, var27);
            var10.addVertexWithUV(par2 + var18, par4, par6 + var19, var25, var27);
            var10.addVertexWithUV(par2 + var18, par4 + var24, par6 + var19, var25, var28);
            var10.addVertexWithUV(par2 + var22, par4 + var24, par6 + var23, var26, var28);
            var10.addVertexWithUV(par2 + var22, par4, par6 + var23, var26, var27);
            var10.addVertexWithUV(par2 + var20, par4, par6 + var21, var25, var27);
            var10.addVertexWithUV(par2 + var20, par4 + var24, par6 + var21, var25, var28);
            var10.addVertexWithUV(par2 + var18, par4 + var24, par6 + var19, var26, var28);
            var10.addVertexWithUV(par2 + var18, par4, par6 + var19, var26, var27);
            var10.addVertexWithUV(par2 + var22, par4, par6 + var23, var25, var27);
            var10.addVertexWithUV(par2 + var22, par4 + var24, par6 + var23, var25, var28);
            var10.addVertexWithUV(par2 + var20, par4 + var24, par6 + var21, var26, var28);
            var10.addVertexWithUV(par2 + var20, par4, par6 + var21, var26, var27);
            var10.addVertexWithUV(par2 + var16, par4, par6 + var17, var25, var27);
            var10.addVertexWithUV(par2 + var16, par4 + var24, par6 + var17, var25, var28);
            var10.draw();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthMask(false);
            var10.startDrawingQuads();
            var10.setColorRGBA(255, 255, 255, 32);
            final double var29 = 0.2;
            final double var30 = 0.2;
            final double var31 = 0.8;
            final double var32 = 0.2;
            final double var33 = 0.2;
            final double var34 = 0.8;
            final double var35 = 0.8;
            final double var36 = 0.8;
            final double var37 = 256.0f * var9;
            final double var38 = 0.0;
            final double var39 = 1.0;
            final double var40 = -1.0f + var12;
            final double var41 = 256.0f * var9 + var40;
            var10.addVertexWithUV(par2 + var29, par4 + var37, par6 + var30, var39, var41);
            var10.addVertexWithUV(par2 + var29, par4, par6 + var30, var39, var40);
            var10.addVertexWithUV(par2 + var31, par4, par6 + var32, var38, var40);
            var10.addVertexWithUV(par2 + var31, par4 + var37, par6 + var32, var38, var41);
            var10.addVertexWithUV(par2 + var35, par4 + var37, par6 + var36, var39, var41);
            var10.addVertexWithUV(par2 + var35, par4, par6 + var36, var39, var40);
            var10.addVertexWithUV(par2 + var33, par4, par6 + var34, var38, var40);
            var10.addVertexWithUV(par2 + var33, par4 + var37, par6 + var34, var38, var41);
            var10.addVertexWithUV(par2 + var31, par4 + var37, par6 + var32, var39, var41);
            var10.addVertexWithUV(par2 + var31, par4, par6 + var32, var39, var40);
            var10.addVertexWithUV(par2 + var35, par4, par6 + var36, var38, var40);
            var10.addVertexWithUV(par2 + var35, par4 + var37, par6 + var36, var38, var41);
            var10.addVertexWithUV(par2 + var33, par4 + var37, par6 + var34, var39, var41);
            var10.addVertexWithUV(par2 + var33, par4, par6 + var34, var39, var40);
            var10.addVertexWithUV(par2 + var29, par4, par6 + var30, var38, var40);
            var10.addVertexWithUV(par2 + var29, par4 + var37, par6 + var30, var38, var41);
            var10.draw();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glDepthMask(true);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderTileEntityBeaconAt((TileEntityBeacon)par1TileEntity, par2, par4, par6, par8);
    }
}
