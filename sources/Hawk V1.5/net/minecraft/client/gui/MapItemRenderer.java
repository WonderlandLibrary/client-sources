package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class MapItemRenderer {
   private final Map loadedMaps = Maps.newHashMap();
   private final TextureManager textureManager;
   private static final ResourceLocation mapIcons = new ResourceLocation("textures/map/map_icons.png");
   private static final String __OBFID = "CL_00000663";

   public void func_148250_a(MapData var1, boolean var2) {
      MapItemRenderer.Instance.access$1(this.func_148248_b(var1), var2);
   }

   public MapItemRenderer(TextureManager var1) {
      this.textureManager = var1;
   }

   private MapItemRenderer.Instance func_148248_b(MapData var1) {
      MapItemRenderer.Instance var2 = (MapItemRenderer.Instance)this.loadedMaps.get(var1.mapName);
      if (var2 == null) {
         var2 = new MapItemRenderer.Instance(this, var1, (Object)null);
         this.loadedMaps.put(var1.mapName, var2);
      }

      return var2;
   }

   public void func_148249_a() {
      Iterator var1 = this.loadedMaps.values().iterator();

      while(var1.hasNext()) {
         MapItemRenderer.Instance var2 = (MapItemRenderer.Instance)var1.next();
         this.textureManager.deleteTexture(MapItemRenderer.Instance.access$2(var2));
      }

      this.loadedMaps.clear();
   }

   static TextureManager access$0(MapItemRenderer var0) {
      return var0.textureManager;
   }

   public void func_148246_a(MapData var1) {
      MapItemRenderer.Instance.access$0(this.func_148248_b(var1));
   }

   static ResourceLocation access$1() {
      return mapIcons;
   }

   class Instance {
      final MapItemRenderer this$0;
      private final DynamicTexture field_148243_c;
      private final int[] field_148241_e;
      private static final String __OBFID = "CL_00000665";
      private final MapData field_148242_b;
      private final ResourceLocation field_148240_d;

      static void access$1(MapItemRenderer.Instance var0, boolean var1) {
         var0.func_148237_a(var1);
      }

      static ResourceLocation access$2(MapItemRenderer.Instance var0) {
         return var0.field_148240_d;
      }

      static void access$0(MapItemRenderer.Instance var0) {
         var0.func_148236_a();
      }

      Instance(MapItemRenderer var1, MapData var2, Object var3) {
         this(var1, var2);
      }

      private void func_148237_a(boolean var1) {
         byte var2 = 0;
         byte var3 = 0;
         Tessellator var4 = Tessellator.getInstance();
         WorldRenderer var5 = var4.getWorldRenderer();
         float var6 = 0.0F;
         MapItemRenderer.access$0(this.this$0).bindTexture(this.field_148240_d);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
         GlStateManager.disableAlpha();
         var5.startDrawingQuads();
         var5.addVertexWithUV((double)((float)var2 + var6), (double)((float)(var3 + 128) - var6), -0.009999999776482582D, 0.0D, 1.0D);
         var5.addVertexWithUV((double)((float)(var2 + 128) - var6), (double)((float)(var3 + 128) - var6), -0.009999999776482582D, 1.0D, 1.0D);
         var5.addVertexWithUV((double)((float)(var2 + 128) - var6), (double)((float)var3 + var6), -0.009999999776482582D, 1.0D, 0.0D);
         var5.addVertexWithUV((double)((float)var2 + var6), (double)((float)var3 + var6), -0.009999999776482582D, 0.0D, 0.0D);
         var4.draw();
         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
         MapItemRenderer.access$0(this.this$0).bindTexture(MapItemRenderer.access$1());
         int var7 = 0;
         Iterator var8 = this.field_148242_b.playersVisibleOnMap.values().iterator();

         while(true) {
            Vec4b var9;
            do {
               if (!var8.hasNext()) {
                  GlStateManager.pushMatrix();
                  GlStateManager.translate(0.0F, 0.0F, -0.04F);
                  GlStateManager.scale(1.0F, 1.0F, 1.0F);
                  GlStateManager.popMatrix();
                  return;
               }

               var9 = (Vec4b)var8.next();
            } while(var1 && var9.func_176110_a() != 1);

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
            var5.startDrawingQuads();
            var5.addVertexWithUV(-1.0D, 1.0D, (double)((float)var7 * 0.001F), (double)var11, (double)var12);
            var5.addVertexWithUV(1.0D, 1.0D, (double)((float)var7 * 0.001F), (double)var13, (double)var12);
            var5.addVertexWithUV(1.0D, -1.0D, (double)((float)var7 * 0.001F), (double)var13, (double)var14);
            var5.addVertexWithUV(-1.0D, -1.0D, (double)((float)var7 * 0.001F), (double)var11, (double)var14);
            var4.draw();
            GlStateManager.popMatrix();
            ++var7;
         }
      }

      private void func_148236_a() {
         for(int var1 = 0; var1 < 16384; ++var1) {
            int var2 = this.field_148242_b.colors[var1] & 255;
            if (var2 / 4 == 0) {
               this.field_148241_e[var1] = (var1 + var1 / 128 & 1) * 8 + 16 << 24;
            } else {
               this.field_148241_e[var1] = MapColor.mapColorArray[var2 / 4].func_151643_b(var2 & 3);
            }
         }

         this.field_148243_c.updateDynamicTexture();
      }

      private Instance(MapItemRenderer var1, MapData var2) {
         this.this$0 = var1;
         this.field_148242_b = var2;
         this.field_148243_c = new DynamicTexture(128, 128);
         this.field_148241_e = this.field_148243_c.getTextureData();
         this.field_148240_d = MapItemRenderer.access$0(var1).getDynamicTextureLocation(String.valueOf((new StringBuilder("map/")).append(var2.mapName)), this.field_148243_c);

         for(int var3 = 0; var3 < this.field_148241_e.length; ++var3) {
            this.field_148241_e[var3] = 0;
         }

      }
   }
}
