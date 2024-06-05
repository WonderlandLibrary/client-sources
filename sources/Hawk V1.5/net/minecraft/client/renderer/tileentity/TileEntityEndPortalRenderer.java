package net.minecraft.client.renderer.tileentity;

import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer {
   FloatBuffer field_147528_b = GLAllocation.createDirectFloatBuffer(16);
   private static final Random field_147527_e = new Random(31100L);
   private static final ResourceLocation field_147526_d = new ResourceLocation("textures/entity/end_portal.png");
   private static final String __OBFID = "CL_00002467";
   private static final ResourceLocation field_147529_c = new ResourceLocation("textures/environment/end_sky.png");

   public void func_180544_a(TileEntityEndPortal var1, double var2, double var4, double var6, float var8, int var9) {
      float var10 = (float)this.rendererDispatcher.field_147560_j;
      float var11 = (float)this.rendererDispatcher.field_147561_k;
      float var12 = (float)this.rendererDispatcher.field_147558_l;
      GlStateManager.disableLighting();
      field_147527_e.setSeed(31100L);
      float var13 = 0.75F;

      for(int var14 = 0; var14 < 16; ++var14) {
         GlStateManager.pushMatrix();
         float var15 = (float)(16 - var14);
         float var16 = 0.0625F;
         float var17 = 1.0F / (var15 + 1.0F);
         if (var14 == 0) {
            this.bindTexture(field_147529_c);
            var17 = 0.1F;
            var15 = 65.0F;
            var16 = 0.125F;
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
         }

         if (var14 >= 1) {
            this.bindTexture(field_147526_d);
         }

         if (var14 == 1) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(1, 1);
            var16 = 0.5F;
         }

         float var18 = (float)(-(var4 + (double)var13));
         float var19 = var18 + (float)ActiveRenderInfo.func_178804_a().yCoord;
         float var20 = var18 + var15 + (float)ActiveRenderInfo.func_178804_a().yCoord;
         float var21 = var19 / var20;
         var21 += (float)(var4 + (double)var13);
         GlStateManager.translate(var10, var21, var12);
         GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
         GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
         GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
         GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
         GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9473, this.func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
         GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9473, this.func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
         GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9473, this.func_147525_a(0.0F, 0.0F, 0.0F, 1.0F));
         GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9474, this.func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, (float)(Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
         GlStateManager.scale(var16, var16, var16);
         GlStateManager.translate(0.5F, 0.5F, 0.0F);
         GlStateManager.rotate((float)(var14 * var14 * 4321 + var14 * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.translate(-0.5F, -0.5F, 0.0F);
         GlStateManager.translate(-var10, -var12, -var11);
         var19 = var18 + (float)ActiveRenderInfo.func_178804_a().yCoord;
         GlStateManager.translate((float)ActiveRenderInfo.func_178804_a().xCoord * var15 / var19, (float)ActiveRenderInfo.func_178804_a().zCoord * var15 / var19, -var11);
         Tessellator var22 = Tessellator.getInstance();
         WorldRenderer var23 = var22.getWorldRenderer();
         var23.startDrawingQuads();
         float var24 = field_147527_e.nextFloat() * 0.5F + 0.1F;
         float var25 = field_147527_e.nextFloat() * 0.5F + 0.4F;
         float var26 = field_147527_e.nextFloat() * 0.5F + 0.5F;
         if (var14 == 0) {
            var26 = 1.0F;
            var25 = 1.0F;
            var24 = 1.0F;
         }

         var23.func_178960_a(var24 * var17, var25 * var17, var26 * var17, 1.0F);
         var23.addVertex(var2, var4 + (double)var13, var6);
         var23.addVertex(var2, var4 + (double)var13, var6 + 1.0D);
         var23.addVertex(var2 + 1.0D, var4 + (double)var13, var6 + 1.0D);
         var23.addVertex(var2 + 1.0D, var4 + (double)var13, var6);
         var22.draw();
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }

      GlStateManager.disableBlend();
      GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
      GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
      GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
      GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
      GlStateManager.enableLighting();
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8, int var9) {
      this.func_180544_a((TileEntityEndPortal)var1, var2, var4, var6, var8, var9);
   }

   private FloatBuffer func_147525_a(float var1, float var2, float var3, float var4) {
      this.field_147528_b.clear();
      this.field_147528_b.put(var1).put(var2).put(var3).put(var4);
      this.field_147528_b.flip();
      return this.field_147528_b;
   }
}
