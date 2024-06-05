package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer {
   private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");
   private static final String __OBFID = "CL_00000962";

   public void func_180536_a(TileEntityBeacon var1, double var2, double var4, double var6, float var8, int var9) {
      float var10 = var1.shouldBeamRender();
      GlStateManager.alphaFunc(516, 0.1F);
      if (var10 > 0.0F) {
         Tessellator var11 = Tessellator.getInstance();
         WorldRenderer var12 = var11.getWorldRenderer();
         List var13 = var1.func_174907_n();
         int var14 = 0;

         for(int var15 = 0; var15 < var13.size(); ++var15) {
            TileEntityBeacon.BeamSegment var16 = (TileEntityBeacon.BeamSegment)var13.get(var15);
            int var17 = var14 + var16.func_177264_c();
            this.bindTexture(beaconBeam);
            GL11.glTexParameterf(3553, 10242, 10497.0F);
            GL11.glTexParameterf(3553, 10243, 10497.0F);
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            float var18 = (float)var1.getWorld().getTotalWorldTime() + var8;
            float var19 = -var18 * 0.2F - (float)MathHelper.floor_float(-var18 * 0.1F);
            double var20 = (double)var18 * 0.025D * -1.5D;
            var12.startDrawingQuads();
            double var22 = 0.2D;
            double var24 = 0.5D + Math.cos(var20 + 2.356194490192345D) * var22;
            double var26 = 0.5D + Math.sin(var20 + 2.356194490192345D) * var22;
            double var28 = 0.5D + Math.cos(var20 + 0.7853981633974483D) * var22;
            double var30 = 0.5D + Math.sin(var20 + 0.7853981633974483D) * var22;
            double var32 = 0.5D + Math.cos(var20 + 3.9269908169872414D) * var22;
            double var34 = 0.5D + Math.sin(var20 + 3.9269908169872414D) * var22;
            double var36 = 0.5D + Math.cos(var20 + 5.497787143782138D) * var22;
            double var38 = 0.5D + Math.sin(var20 + 5.497787143782138D) * var22;
            double var40 = 0.0D;
            double var42 = 1.0D;
            double var44 = (double)(-1.0F + var19);
            double var46 = (double)((float)var16.func_177264_c() * var10) * (0.5D / var22) + var44;
            var12.func_178960_a(var16.func_177263_b()[0], var16.func_177263_b()[1], var16.func_177263_b()[2], 0.125F);
            var12.addVertexWithUV(var2 + var24, var4 + (double)var17, var6 + var26, var42, var46);
            var12.addVertexWithUV(var2 + var24, var4 + (double)var14, var6 + var26, var42, var44);
            var12.addVertexWithUV(var2 + var28, var4 + (double)var14, var6 + var30, var40, var44);
            var12.addVertexWithUV(var2 + var28, var4 + (double)var17, var6 + var30, var40, var46);
            var12.addVertexWithUV(var2 + var36, var4 + (double)var17, var6 + var38, var42, var46);
            var12.addVertexWithUV(var2 + var36, var4 + (double)var14, var6 + var38, var42, var44);
            var12.addVertexWithUV(var2 + var32, var4 + (double)var14, var6 + var34, var40, var44);
            var12.addVertexWithUV(var2 + var32, var4 + (double)var17, var6 + var34, var40, var46);
            var12.addVertexWithUV(var2 + var28, var4 + (double)var17, var6 + var30, var42, var46);
            var12.addVertexWithUV(var2 + var28, var4 + (double)var14, var6 + var30, var42, var44);
            var12.addVertexWithUV(var2 + var36, var4 + (double)var14, var6 + var38, var40, var44);
            var12.addVertexWithUV(var2 + var36, var4 + (double)var17, var6 + var38, var40, var46);
            var12.addVertexWithUV(var2 + var32, var4 + (double)var17, var6 + var34, var42, var46);
            var12.addVertexWithUV(var2 + var32, var4 + (double)var14, var6 + var34, var42, var44);
            var12.addVertexWithUV(var2 + var24, var4 + (double)var14, var6 + var26, var40, var44);
            var12.addVertexWithUV(var2 + var24, var4 + (double)var17, var6 + var26, var40, var46);
            var11.draw();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.depthMask(false);
            var12.startDrawingQuads();
            var12.func_178960_a(var16.func_177263_b()[0], var16.func_177263_b()[1], var16.func_177263_b()[2], 0.125F);
            var20 = 0.2D;
            var22 = 0.2D;
            var24 = 0.8D;
            var26 = 0.2D;
            var28 = 0.2D;
            var30 = 0.8D;
            var32 = 0.8D;
            var34 = 0.8D;
            var36 = 0.0D;
            var38 = 1.0D;
            var40 = (double)(-1.0F + var19);
            var42 = (double)((float)var16.func_177264_c() * var10) + var40;
            var12.addVertexWithUV(var2 + var20, var4 + (double)var17, var6 + var22, var38, var42);
            var12.addVertexWithUV(var2 + var20, var4 + (double)var14, var6 + var22, var38, var40);
            var12.addVertexWithUV(var2 + var24, var4 + (double)var14, var6 + var26, var36, var40);
            var12.addVertexWithUV(var2 + var24, var4 + (double)var17, var6 + var26, var36, var42);
            var12.addVertexWithUV(var2 + var32, var4 + (double)var17, var6 + var34, var38, var42);
            var12.addVertexWithUV(var2 + var32, var4 + (double)var14, var6 + var34, var38, var40);
            var12.addVertexWithUV(var2 + var28, var4 + (double)var14, var6 + var30, var36, var40);
            var12.addVertexWithUV(var2 + var28, var4 + (double)var17, var6 + var30, var36, var42);
            var12.addVertexWithUV(var2 + var24, var4 + (double)var17, var6 + var26, var38, var42);
            var12.addVertexWithUV(var2 + var24, var4 + (double)var14, var6 + var26, var38, var40);
            var12.addVertexWithUV(var2 + var32, var4 + (double)var14, var6 + var34, var36, var40);
            var12.addVertexWithUV(var2 + var32, var4 + (double)var17, var6 + var34, var36, var42);
            var12.addVertexWithUV(var2 + var28, var4 + (double)var17, var6 + var30, var38, var42);
            var12.addVertexWithUV(var2 + var28, var4 + (double)var14, var6 + var30, var38, var40);
            var12.addVertexWithUV(var2 + var20, var4 + (double)var14, var6 + var22, var36, var40);
            var12.addVertexWithUV(var2 + var20, var4 + (double)var17, var6 + var22, var36, var42);
            var11.draw();
            GlStateManager.enableLighting();
            GlStateManager.func_179098_w();
            GlStateManager.depthMask(true);
            var14 = var17;
         }
      }

   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8, int var9) {
      this.func_180536_a((TileEntityBeacon)var1, var2, var4, var6, var8, var9);
   }
}
