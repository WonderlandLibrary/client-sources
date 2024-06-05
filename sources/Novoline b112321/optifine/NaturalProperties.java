package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import optifine.Config;
import optifine.MatchBlock;

public class NaturalProperties {
   public int rotation;
   public boolean flip;
   private Map[] quadMaps;

   public NaturalProperties(String var1) {
      MatchBlock.b();
      super();
      this.rotation = 1;
      this.flip = false;
      this.quadMaps = new Map[8];
      if(var1.equals("4")) {
         this.rotation = 4;
      }

      if(var1.equals("2")) {
         this.rotation = 2;
      }

      if(var1.equals("F")) {
         this.flip = true;
      }

      if(var1.equals("4F")) {
         this.rotation = 4;
         this.flip = true;
      }

      if(var1.equals("2F")) {
         this.rotation = 2;
         this.flip = true;
      }

      Config.warn("NaturalTextures: Unknown type: " + var1);
   }

   public boolean a() {
      PacketRemapper[] var1 = MatchBlock.b();
      return this.rotation != 2 && this.rotation != 4?this.flip:true;
   }

   public synchronized BakedQuad b(BakedQuad var1, int var2, boolean var3) {
      MatchBlock.b();
      int var5 = var2;
      if(var3) {
         var5 = var2 | 4;
      }

      if(var5 > 0 && var5 < this.quadMaps.length) {
         Object var6 = this.quadMaps[var5];
         if(var6 == null) {
            var6 = new IdentityHashMap(1);
            this.quadMaps[var5] = (Map)var6;
         }

         BakedQuad var7 = (BakedQuad)((Map)var6).get(var1);
         if(var7 == null) {
            var7 = this.makeQuad(var1, var2, var3);
            ((Map)var6).put(var1, var7);
         }

         return var7;
      } else {
         return var1;
      }
   }

   private BakedQuad makeQuad(BakedQuad var1, int var2, boolean var3) {
      int[] var5 = var1.getVertexData();
      MatchBlock.b();
      int var6 = var1.getTintIndex();
      EnumFacing var7 = var1.getFace();
      TextureAtlasSprite var8 = var1.getSprite();
      if(!this.isFullSprite(var1)) {
         return var1;
      } else {
         var5 = this.transformVertexData(var5, var2, var3);
         BakedQuad var9 = new BakedQuad(var5, var6, var7, var8);
         return var9;
      }
   }

   private int[] transformVertexData(int[] var1, int var2, boolean var3) {
      MatchBlock.b();
      int[] var5 = (int[])((int[])var1.clone());
      int var6 = 4 - var2;
      if(var3) {
         var6 += 3;
      }

      var6 = var6 % 4;
      int var7 = var5.length / 4;
      int var8 = 0;
      if(var8 < 4) {
         int var9 = var8 * var7;
         int var10 = var6 * var7;
         var5[var10 + 4] = var1[var9 + 4];
         var5[var10 + 4 + 1] = var1[var9 + 4 + 1];
         if(var3) {
            --var6;
            var6 = 3;
         }

         ++var6;
         if(var6 > 3) {
            var6 = 0;
         }

         ++var8;
      }

      return var5;
   }

   private boolean isFullSprite(BakedQuad var1) {
      TextureAtlasSprite var3 = var1.getSprite();
      float var4 = var3.getMinU();
      float var5 = var3.getMaxU();
      float var6 = var5 - var4;
      float var7 = var6 / 256.0F;
      float var8 = var3.getMinV();
      MatchBlock.b();
      float var9 = var3.getMaxV();
      float var10 = var9 - var8;
      float var11 = var10 / 256.0F;
      int[] var12 = var1.getVertexData();
      int var13 = var12.length / 4;
      int var14 = 0;
      if(var14 < 4) {
         int var15 = var14 * var13;
         float var16 = Float.intBitsToFloat(var12[var15 + 4]);
         float var17 = Float.intBitsToFloat(var12[var15 + 4 + 1]);
         if(!this.equalsDelta(var16, var4, var7) && !this.equalsDelta(var16, var5, var7)) {
            return false;
         }

         if(!this.equalsDelta(var17, var8, var11) && !this.equalsDelta(var17, var9, var11)) {
            return false;
         }

         ++var14;
      }

      return true;
   }

   private boolean equalsDelta(float var1, float var2, float var3) {
      MatchBlock.b();
      float var5 = MathHelper.abs(var1 - var2);
      return var5 < var3;
   }
}
