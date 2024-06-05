package optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class NaturalProperties {
   public boolean flip = false;
   private Map[] quadMaps = new Map[8];
   public int rotation = 1;

   private int[] transformVertexData(int[] var1, int var2, boolean var3) {
      int[] var4 = (int[])var1.clone();
      int var5 = 4 - var2;
      if (var3) {
         var5 += 3;
      }

      var5 %= 4;
      int var6 = var4.length / 4;

      for(int var7 = 0; var7 < 4; ++var7) {
         int var8 = var7 * var6;
         int var9 = var5 * var6;
         var4[var9 + 4] = var1[var8 + 4];
         var4[var9 + 4 + 1] = var1[var8 + 4 + 1];
         if (var3) {
            --var5;
            if (var5 < 0) {
               var5 = 3;
            }
         } else {
            ++var5;
            if (var5 > 3) {
               var5 = 0;
            }
         }
      }

      return var4;
   }

   public NaturalProperties(String var1) {
      if (var1.equals("4")) {
         this.rotation = 4;
      } else if (var1.equals("2")) {
         this.rotation = 2;
      } else if (var1.equals("F")) {
         this.flip = true;
      } else if (var1.equals("4F")) {
         this.rotation = 4;
         this.flip = true;
      } else if (var1.equals("2F")) {
         this.rotation = 2;
         this.flip = true;
      } else {
         Config.warn(String.valueOf((new StringBuilder("NaturalTextures: Unknown type: ")).append(var1)));
      }

   }

   private BakedQuad makeQuad(BakedQuad var1, int var2, boolean var3) {
      int[] var4 = var1.func_178209_a();
      int var5 = var1.func_178211_c();
      EnumFacing var6 = var1.getFace();
      TextureAtlasSprite var7 = var1.getSprite();
      if (!this.isFullSprite(var1)) {
         return var1;
      } else {
         var4 = this.transformVertexData(var4, var2, var3);
         BakedQuad var8 = new BakedQuad(var4, var5, var6, var7);
         return var8;
      }
   }

   private boolean equalsDelta(float var1, float var2, float var3) {
      float var4 = MathHelper.abs(var1 - var2);
      return var4 < var3;
   }

   public synchronized BakedQuad getQuad(BakedQuad var1, int var2, boolean var3) {
      int var4 = var2;
      if (var3) {
         var4 = var2 | 4;
      }

      if (var4 > 0 && var4 < this.quadMaps.length) {
         Object var5 = this.quadMaps[var4];
         if (var5 == null) {
            var5 = new IdentityHashMap(1);
            this.quadMaps[var4] = (Map)var5;
         }

         BakedQuad var6 = (BakedQuad)((Map)var5).get(var1);
         if (var6 == null) {
            var6 = this.makeQuad(var1, var2, var3);
            ((Map)var5).put(var1, var6);
         }

         return var6;
      } else {
         return var1;
      }
   }

   public boolean isValid() {
      return this.rotation != 2 && this.rotation != 4 ? this.flip : true;
   }

   private boolean isFullSprite(BakedQuad var1) {
      TextureAtlasSprite var2 = var1.getSprite();
      float var3 = var2.getMinU();
      float var4 = var2.getMaxU();
      float var5 = var4 - var3;
      float var6 = var5 / 256.0F;
      float var7 = var2.getMinV();
      float var8 = var2.getMaxV();
      float var9 = var8 - var7;
      float var10 = var9 / 256.0F;
      int[] var11 = var1.func_178209_a();
      int var12 = var11.length / 4;

      for(int var13 = 0; var13 < 4; ++var13) {
         int var14 = var13 * var12;
         float var15 = Float.intBitsToFloat(var11[var14 + 4]);
         float var16 = Float.intBitsToFloat(var11[var14 + 4 + 1]);
         if (!this.equalsDelta(var15, var3, var6) && !this.equalsDelta(var15, var4, var6)) {
            return false;
         }

         if (!this.equalsDelta(var16, var7, var10) && !this.equalsDelta(var16, var8, var10)) {
            return false;
         }
      }

      return true;
   }
}
