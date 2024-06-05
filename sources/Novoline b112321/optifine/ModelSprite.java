package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import optifine.MatchBlock;
import org.lwjgl.opengl.GL11;

public class ModelSprite {
   private ModelRenderer modelRenderer = null;
   private int textureOffsetX = 0;
   private int textureOffsetY = 0;
   private float posX = 0.0F;
   private float posY = 0.0F;
   private float posZ = 0.0F;
   private int sizeX = 0;
   private int sizeY = 0;
   private int sizeZ = 0;
   private float sizeAdd = 0.0F;
   private float minU = 0.0F;
   private float minV = 0.0F;
   private float maxU = 0.0F;
   private float maxV = 0.0F;

   public ModelSprite(ModelRenderer var1, int var2, int var3, float var4, float var5, float var6, int var7, int var8, int var9, float var10) {
      this.modelRenderer = var1;
      this.textureOffsetX = var2;
      this.textureOffsetY = var3;
      this.posX = var4;
      this.posY = var5;
      this.posZ = var6;
      this.sizeX = var7;
      this.sizeY = var8;
      this.sizeZ = var9;
      this.sizeAdd = var10;
      this.minU = (float)var2 / var1.textureWidth;
      this.minV = (float)var3 / var1.textureHeight;
      this.maxU = (float)(var2 + var7) / var1.textureWidth;
      this.maxV = (float)(var3 + var8) / var1.textureHeight;
   }

   public void render(Tessellator var1, float var2) {
      GlStateManager.translate(this.posX * var2, this.posY * var2, this.posZ * var2);
      MatchBlock.b();
      float var4 = this.minU;
      float var5 = this.maxU;
      float var6 = this.minV;
      float var7 = this.maxV;
      if(this.modelRenderer.mirror) {
         var4 = this.maxU;
         var5 = this.minU;
      }

      if(this.modelRenderer.mirrorV) {
         var6 = this.maxV;
         var7 = this.minV;
      }

      renderItemIn2D(var1, var4, var6, var5, var7, this.sizeX, this.sizeY, var2 * (float)this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight);
      GlStateManager.translate(-this.posX * var2, -this.posY * var2, -this.posZ * var2);
   }

   public static void renderItemIn2D(Tessellator var0, float var1, float var2, float var3, float var4, int var5, int var6, float var7, float var8, float var9) {
      PacketRemapper[] var10 = MatchBlock.b();
      if(var7 < 6.25E-4F) {
         var7 = 6.25E-4F;
      }

      float var11 = var3 - var1;
      float var12 = var4 - var2;
      double var13 = (double)(MathHelper.abs(var11) * (var8 / 16.0F));
      double var15 = (double)(MathHelper.abs(var12) * (var9 / 16.0F));
      WorldRenderer var17 = var0.getWorldRenderer();
      GL11.glNormal3f(0.0F, 0.0F, -1.0F);
      var17.begin(7, DefaultVertexFormats.POSITION_TEX);
      var17.pos(0.0D, var15, 0.0D).tex((double)var1, (double)var4).endVertex();
      var17.pos(var13, var15, 0.0D).tex((double)var3, (double)var4).endVertex();
      var17.pos(var13, 0.0D, 0.0D).tex((double)var3, (double)var2).endVertex();
      var17.pos(0.0D, 0.0D, 0.0D).tex((double)var1, (double)var2).endVertex();
      var0.draw();
      GL11.glNormal3f(0.0F, 0.0F, 1.0F);
      var17.begin(7, DefaultVertexFormats.POSITION_TEX);
      var17.pos(0.0D, 0.0D, (double)var7).tex((double)var1, (double)var2).endVertex();
      var17.pos(var13, 0.0D, (double)var7).tex((double)var3, (double)var2).endVertex();
      var17.pos(var13, var15, (double)var7).tex((double)var3, (double)var4).endVertex();
      var17.pos(0.0D, var15, (double)var7).tex((double)var1, (double)var4).endVertex();
      var0.draw();
      float var18 = 0.5F * var11 / (float)var5;
      float var19 = 0.5F * var12 / (float)var6;
      GL11.glNormal3f(-1.0F, 0.0F, 0.0F);
      var17.begin(7, DefaultVertexFormats.POSITION_TEX);
      int var20 = 0;
      if(var20 < var5) {
         float var21 = (float)var20 / (float)var5;
         float var22 = var1 + var11 * var21 + var18;
         var17.pos((double)var21 * var13, var15, (double)var7).tex((double)var22, (double)var4).endVertex();
         var17.pos((double)var21 * var13, var15, 0.0D).tex((double)var22, (double)var4).endVertex();
         var17.pos((double)var21 * var13, 0.0D, 0.0D).tex((double)var22, (double)var2).endVertex();
         var17.pos((double)var21 * var13, 0.0D, (double)var7).tex((double)var22, (double)var2).endVertex();
         ++var20;
      }

      var0.draw();
      GL11.glNormal3f(1.0F, 0.0F, 0.0F);
      var17.begin(7, DefaultVertexFormats.POSITION_TEX);
      var20 = 0;
      if(var20 < var5) {
         float var31 = (float)var20 / (float)var5;
         float var34 = var1 + var11 * var31 + var18;
         float var23 = var31 + 1.0F / (float)var5;
         var17.pos((double)var23 * var13, 0.0D, (double)var7).tex((double)var34, (double)var2).endVertex();
         var17.pos((double)var23 * var13, 0.0D, 0.0D).tex((double)var34, (double)var2).endVertex();
         var17.pos((double)var23 * var13, var15, 0.0D).tex((double)var34, (double)var4).endVertex();
         var17.pos((double)var23 * var13, var15, (double)var7).tex((double)var34, (double)var4).endVertex();
         ++var20;
      }

      var0.draw();
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      var17.begin(7, DefaultVertexFormats.POSITION_TEX);
      var20 = 0;
      if(var20 < var6) {
         float var32 = (float)var20 / (float)var6;
         float var35 = var2 + var12 * var32 + var19;
         float var37 = var32 + 1.0F / (float)var6;
         var17.pos(0.0D, (double)var37 * var15, (double)var7).tex((double)var1, (double)var35).endVertex();
         var17.pos(var13, (double)var37 * var15, (double)var7).tex((double)var3, (double)var35).endVertex();
         var17.pos(var13, (double)var37 * var15, 0.0D).tex((double)var3, (double)var35).endVertex();
         var17.pos(0.0D, (double)var37 * var15, 0.0D).tex((double)var1, (double)var35).endVertex();
         ++var20;
      }

      var0.draw();
      GL11.glNormal3f(0.0F, -1.0F, 0.0F);
      var17.begin(7, DefaultVertexFormats.POSITION_TEX);
      var20 = 0;
      if(var20 < var6) {
         float var33 = (float)var20 / (float)var6;
         float var36 = var2 + var12 * var33 + var19;
         var17.pos(var13, (double)var33 * var15, (double)var7).tex((double)var3, (double)var36).endVertex();
         var17.pos(0.0D, (double)var33 * var15, (double)var7).tex((double)var1, (double)var36).endVertex();
         var17.pos(0.0D, (double)var33 * var15, 0.0D).tex((double)var1, (double)var36).endVertex();
         var17.pos(var13, (double)var33 * var15, 0.0D).tex((double)var3, (double)var36).endVertex();
         ++var20;
      }

      var0.draw();
   }
}
