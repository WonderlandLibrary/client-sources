package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBeacon$BeamSegment;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer {
   private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");

   public void renderTileEntityAt(TileEntityBeacon var1, double var2, double var4, double var6, float var8, int var9) {
      float var10 = var1.shouldBeamRender();
      GlStateManager.alphaFunc(516, 0.1F);
      if(var10 > 0.0F) {
         Tessellator var11 = Tessellator.getInstance();
         WorldRenderer var12 = var11.getWorldRenderer();
         GlStateManager.disableFog();
         List var13 = var1.getBeamSegments();
         int var14 = 0;

         for(TileEntityBeacon$BeamSegment var16 : var13) {
            int var17 = var14 + var16.getHeight();
            this.bindTexture(beaconBeam);
            GL11.glTexParameterf(3553, 10242, 10497.0F);
            GL11.glTexParameterf(3553, 10243, 10497.0F);
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            double var18 = (double)var1.getWorld().getTotalWorldTime() + (double)var8;
            double var20 = MathHelper.func_181162_h(-var18 * 0.2D - (double)MathHelper.floor_double(-var18 * 0.1D));
            float var22 = var16.getColors()[0];
            float var23 = var16.getColors()[1];
            float var24 = var16.getColors()[2];
            double var25 = var18 * 0.025D * -1.5D;
            double var27 = 0.5D + (double)MathHelper.cos(var25 + 2.356194490192345D) * 0.2D;
            double var29 = 0.5D + (double)MathHelper.sin(var25 + 2.356194490192345D) * 0.2D;
            double var31 = 0.5D + (double)MathHelper.cos(var25 + 0.7853981633974483D) * 0.2D;
            double var33 = 0.5D + (double)MathHelper.sin(var25 + 0.7853981633974483D) * 0.2D;
            double var35 = 0.5D + (double)MathHelper.cos(var25 + 3.9269908169872414D) * 0.2D;
            double var37 = 0.5D + (double)MathHelper.sin(var25 + 3.9269908169872414D) * 0.2D;
            double var39 = 0.5D + (double)MathHelper.cos(var25 + 5.497787143782138D) * 0.2D;
            double var41 = 0.5D + (double)MathHelper.sin(var25 + 5.497787143782138D) * 0.2D;
            double var43 = -1.0D + var20;
            double var45 = (double)((float)var16.getHeight() * var10) * 2.5D + var43;
            var12.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            var12.pos(var2 + var27, var4 + (double)var17, var6 + var29).tex(1.0D, var45).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var27, var4 + (double)var14, var6 + var29).tex(1.0D, var43).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var31, var4 + (double)var14, var6 + var33).tex(0.0D, var43).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var31, var4 + (double)var17, var6 + var33).tex(0.0D, var45).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var39, var4 + (double)var17, var6 + var41).tex(1.0D, var45).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var39, var4 + (double)var14, var6 + var41).tex(1.0D, var43).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var35, var4 + (double)var14, var6 + var37).tex(0.0D, var43).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var35, var4 + (double)var17, var6 + var37).tex(0.0D, var45).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var31, var4 + (double)var17, var6 + var33).tex(1.0D, var45).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var31, var4 + (double)var14, var6 + var33).tex(1.0D, var43).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var39, var4 + (double)var14, var6 + var41).tex(0.0D, var43).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var39, var4 + (double)var17, var6 + var41).tex(0.0D, var45).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var35, var4 + (double)var17, var6 + var37).tex(1.0D, var45).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var35, var4 + (double)var14, var6 + var37).tex(1.0D, var43).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var27, var4 + (double)var14, var6 + var29).tex(0.0D, var43).color(var22, var23, var24, 1.0F).endVertex();
            var12.pos(var2 + var27, var4 + (double)var17, var6 + var29).tex(0.0D, var45).color(var22, var23, var24, 1.0F).endVertex();
            var11.draw();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.depthMask(false);
            double var47 = -1.0D + var20;
            double var49 = (double)((float)var16.getHeight() * var10) + var47;
            var12.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            var12.pos(var2 + 0.2D, var4 + (double)var17, var6 + 0.2D).tex(1.0D, var49).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.2D, var4 + (double)var14, var6 + 0.2D).tex(1.0D, var47).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.8D, var4 + (double)var14, var6 + 0.2D).tex(0.0D, var47).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.8D, var4 + (double)var17, var6 + 0.2D).tex(0.0D, var49).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.8D, var4 + (double)var17, var6 + 0.8D).tex(1.0D, var49).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.8D, var4 + (double)var14, var6 + 0.8D).tex(1.0D, var47).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.2D, var4 + (double)var14, var6 + 0.8D).tex(0.0D, var47).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.2D, var4 + (double)var17, var6 + 0.8D).tex(0.0D, var49).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.8D, var4 + (double)var17, var6 + 0.2D).tex(1.0D, var49).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.8D, var4 + (double)var14, var6 + 0.2D).tex(1.0D, var47).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.8D, var4 + (double)var14, var6 + 0.8D).tex(0.0D, var47).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.8D, var4 + (double)var17, var6 + 0.8D).tex(0.0D, var49).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.2D, var4 + (double)var17, var6 + 0.8D).tex(1.0D, var49).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.2D, var4 + (double)var14, var6 + 0.8D).tex(1.0D, var47).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.2D, var4 + (double)var14, var6 + 0.2D).tex(0.0D, var47).color(var22, var23, var24, 0.125F).endVertex();
            var12.pos(var2 + 0.2D, var4 + (double)var17, var6 + 0.2D).tex(0.0D, var49).color(var22, var23, var24, 0.125F).endVertex();
            var11.draw();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);
            var14 = var17;
         }

         GlStateManager.enableFog();
      }

   }

   public boolean func_181055_a() {
      return true;
   }
}
