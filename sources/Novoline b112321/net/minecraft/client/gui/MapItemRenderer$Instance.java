package net.minecraft.client.gui;

import java.util.Arrays;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.MapItemRenderer$1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

class MapItemRenderer$Instance {
   private final MapData mapData;
   private final DynamicTexture mapTexture;
   private final ResourceLocation location;
   private final int[] mapTextureData;
   final MapItemRenderer this$0;

   private MapItemRenderer$Instance(MapItemRenderer var1, MapData var2) {
      this.this$0 = var1;
      this.mapData = var2;
      this.mapTexture = new DynamicTexture(128, 128);
      this.mapTextureData = this.mapTexture.getTextureData();
      this.location = MapItemRenderer.access$400(var1).getDynamicTextureLocation("map/" + var2.mapName, this.mapTexture);
      Arrays.fill(this.mapTextureData, 0);
   }

   private void updateMapTexture() {
      for(int var1 = 0; var1 < 16384; ++var1) {
         int var2 = this.mapData.colors[var1] & 255;
         if(var2 / 4 == 0) {
            this.mapTextureData[var1] = (var1 + var1 / 128 & 1) * 8 + 16 << 24;
         } else {
            this.mapTextureData[var1] = MapColor.mapColorArray[var2 / 4].func_151643_b(var2 & 3);
         }
      }

      this.mapTexture.updateDynamicTexture();
   }

   private void render(boolean var1) {
      byte var2 = 0;
      byte var3 = 0;
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      float var6 = 0.0F;
      MapItemRenderer.access$400(this.this$0).bindTexture(this.location);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
      GlStateManager.disableAlpha();
      var5.begin(7, DefaultVertexFormats.POSITION_TEX);
      var5.pos((double)((float)var2 + var6), (double)((float)(var3 + 128) - var6), -0.009999999776482582D).tex(0.0D, 1.0D).endVertex();
      var5.pos((double)((float)(var2 + 128) - var6), (double)((float)(var3 + 128) - var6), -0.009999999776482582D).tex(1.0D, 1.0D).endVertex();
      var5.pos((double)((float)(var2 + 128) - var6), (double)((float)var3 + var6), -0.009999999776482582D).tex(1.0D, 0.0D).endVertex();
      var5.pos((double)((float)var2 + var6), (double)((float)var3 + var6), -0.009999999776482582D).tex(0.0D, 0.0D).endVertex();
      var4.draw();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      MapItemRenderer.access$400(this.this$0).bindTexture(MapItemRenderer.access$500());
      int var7 = 0;

      for(Vec4b var9 : this.mapData.mapDecorations.values()) {
         if(var9.func_176110_a() == 1) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)var2 + (float)var9.func_176112_b() / 2.0F + 64.0F, (float)var3 + (float)var9.func_176113_c() / 2.0F + 64.0F, -0.02F);
            GlStateManager.rotate((float)(var9.func_176111_d() * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(4.0F, 4.0F, 3.0F);
            GlStateManager.translate(-0.125F, 0.125F, 0.0F);
            byte var10 = var9.func_176110_a();
            float var11 = (float)(var10 % 4) / 4.0F;
            float var12 = (float)(var10 / 4) / 4.0F;
            float var13 = (float)(var10 % 4 + 1) / 4.0F;
            float var14 = (float)(var10 / 4 + 1) / 4.0F;
            var5.begin(7, DefaultVertexFormats.POSITION_TEX);
            float var15 = -0.001F;
            var5.pos(-1.0D, 1.0D, (double)((float)var7 * -0.001F)).tex((double)var11, (double)var12).endVertex();
            var5.pos(1.0D, 1.0D, (double)((float)var7 * -0.001F)).tex((double)var13, (double)var12).endVertex();
            var5.pos(1.0D, -1.0D, (double)((float)var7 * -0.001F)).tex((double)var13, (double)var14).endVertex();
            var5.pos(-1.0D, -1.0D, (double)((float)var7 * -0.001F)).tex((double)var11, (double)var14).endVertex();
            var4.draw();
            GlStateManager.popMatrix();
            ++var7;
         }
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 0.0F, -0.04F);
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   static void access$000(MapItemRenderer$Instance var0) {
      var0.updateMapTexture();
   }

   static void access$100(MapItemRenderer$Instance var0, boolean var1) {
      var0.render(var1);
   }

   MapItemRenderer$Instance(MapItemRenderer var1, MapData var2, MapItemRenderer$1 var3) {
      this(var1, var2);
   }

   static ResourceLocation access$300(MapItemRenderer$Instance var0) {
      return var0.location;
   }
}
