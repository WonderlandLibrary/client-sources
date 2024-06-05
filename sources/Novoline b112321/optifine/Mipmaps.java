package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Dimension;
import java.nio.IntBuffer;
import java.util.ArrayList;
import net.minecraft.client.renderer.GLAllocation;
import optifine.Config;
import optifine.MatchBlock;
import optifine.TextureUtils;
import org.lwjgl.opengl.GL11;

public class Mipmaps {
   private final String iconName;
   private final int width;
   private final int height;
   private final int[] data;
   private final boolean direct;
   private int[][] mipmapDatas;
   private IntBuffer[] mipmapBuffers;
   private Dimension[] mipmapDimensions;

   public Mipmaps(String var1, int var2, int var3, int[] var4, boolean var5) {
      this.iconName = var1;
      MatchBlock.b();
      this.width = var2;
      this.height = var3;
      this.data = var4;
      this.direct = var5;
      this.mipmapDimensions = makeMipmapDimensions(var2, var3, var1);
      this.mipmapDatas = generateMipMapData(var4, var2, var3, this.mipmapDimensions);
      this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
   }

   public static Dimension[] makeMipmapDimensions(int var0, int var1, String var2) {
      int var4 = TextureUtils.e(var0);
      MatchBlock.b();
      int var5 = TextureUtils.e(var1);
      if(var4 == var0 && var5 == var1) {
         ArrayList var6 = new ArrayList();
         int var7 = var4 / 2;
         int var8 = var5 / 2;
         if(var8 <= 0) {
            Dimension[] var11 = (Dimension[])((Dimension[])((Dimension[])var6.toArray(new Dimension[var6.size()])));
            return var11;
         }

         if(var7 <= 0) {
            var7 = 1;
         }

         if(var8 <= 0) {
            var8 = 1;
         }

         int var9 = var7 * var8 * 4;
         Dimension var10 = new Dimension(var7, var8);
         var6.add(var10);
      }

      Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + var2 + ", dim: " + var0 + "x" + var1);
      return new Dimension[0];
   }

   public static int[][] generateMipMapData(int[] var0, int var1, int var2, Dimension[] var3) {
      PacketRemapper[] var4 = MatchBlock.b();
      boolean var7 = true;
      int[][] var8 = new int[var3.length][];
      int var9 = 0;
      if(var9 < var3.length) {
         Dimension var10 = var3[var9];
         int var11 = var10.width;
         int var12 = var10.height;
         int[] var13 = new int[var11 * var12];
         var8[var9] = var13;
         int var14 = var9 + 1;
         if(var7) {
            int var15 = 0;
            if(var15 < var11) {
               int var16 = 0;
               if(var16 < var12) {
                  int var17 = var0[var15 * 2 + 0 + (var16 * 2 + 0) * var1];
                  int var18 = var0[var15 * 2 + 1 + (var16 * 2 + 0) * var1];
                  int var19 = var0[var15 * 2 + 1 + (var16 * 2 + 1) * var1];
                  int var20 = var0[var15 * 2 + 0 + (var16 * 2 + 1) * var1];
                  int var21 = alphaBlend(var17, var18, var19, var20);
                  var13[var15 + var16 * var11] = var21;
                  ++var16;
               }

               ++var15;
            }
         }

         if(var11 <= 1 || var12 <= 1) {
            var7 = false;
         }

         ++var9;
      }

      return var8;
   }

   public static int alphaBlend(int var0, int var1, int var2, int var3) {
      int var4 = b(var0, var1);
      int var5 = b(var2, var3);
      int var6 = b(var4, var5);
      return var6;
   }

   private static int b(int var0, int var1) {
      MatchBlock.b();
      int var3 = (var0 & -16777216) >> 24 & 255;
      int var4 = (var1 & -16777216) >> 24 & 255;
      int var5 = (var3 + var4) / 2;
      if(var3 == 0 && var4 == 0) {
         var3 = 1;
         var4 = 1;
      }

      if(var3 == 0) {
         var0 = var1;
         var5 /= 2;
      }

      if(var4 == 0) {
         var1 = var0;
         var5 /= 2;
      }

      int var6 = (var0 >> 16 & 255) * var3;
      int var7 = (var0 >> 8 & 255) * var3;
      int var8 = (var0 & 255) * var3;
      int var9 = (var1 >> 16 & 255) * var4;
      int var10 = (var1 >> 8 & 255) * var4;
      int var11 = (var1 & 255) * var4;
      int var12 = (var6 + var9) / (var3 + var4);
      int var13 = (var7 + var10) / (var3 + var4);
      int var14 = (var8 + var11) / (var3 + var4);
      return var5 << 24 | var12 << 16 | var13 << 8 | var14;
   }

   private int averageColor(int var1, int var2) {
      int var3 = (var1 & -16777216) >> 24 & 255;
      int var4 = (var2 & -16777216) >> 24 & 255;
      return (var3 + var4 >> 1 << 24) + ((var1 & 16711422) + (var2 & 16711422) >> 1);
   }

   public static IntBuffer[] makeMipmapBuffers(Dimension[] var0, int[][] var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0 == null) {
         return null;
      } else {
         IntBuffer[] var3 = new IntBuffer[var0.length];
         int var4 = 0;
         if(var4 < var0.length) {
            Dimension var5 = var0[var4];
            int var6 = var5.width * var5.height;
            IntBuffer var7 = GLAllocation.createDirectIntBuffer(var6);
            int[] var8 = var1[var4];
            var7.clear();
            var7.put(var8);
            var7.clear();
            var3[var4] = var7;
            ++var4;
         }

         return var3;
      }
   }

   public static void allocateMipmapTextures(int var0, int var1, String var2) {
      MatchBlock.b();
      Dimension[] var4 = makeMipmapDimensions(var0, var1, var2);
      int var5 = 0;
      if(var5 < var4.length) {
         Dimension var6 = var4[var5];
         int var7 = var6.width;
         int var8 = var6.height;
         int var9 = var5 + 1;
         GL11.glTexImage2D(3553, var9, 6408, var7, var8, 0, '胡', '荧', (IntBuffer)null);
         ++var5;
      }

   }
}
