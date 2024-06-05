package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ViewFrustum {
   protected int field_178168_c;
   protected int field_178165_d;
   public RenderChunk[] field_178164_f;
   protected final World field_178167_b;
   protected final RenderGlobal field_178169_a;
   protected int field_178166_e;

   public ViewFrustum(World var1, int var2, RenderGlobal var3, IRenderChunkFactory var4) {
      this.field_178169_a = var3;
      this.field_178167_b = var1;
      this.func_178159_a(var2);
      this.func_178158_a(var4);
   }

   public void func_178163_a(double var1, double var3) {
      int var5 = MathHelper.floor_double(var1) - 8;
      int var6 = MathHelper.floor_double(var3) - 8;
      int var7 = this.field_178165_d * 16;

      for(int var8 = 0; var8 < this.field_178165_d; ++var8) {
         int var9 = this.func_178157_a(var5, var7, var8);

         for(int var10 = 0; var10 < this.field_178166_e; ++var10) {
            int var11 = this.func_178157_a(var6, var7, var10);

            for(int var12 = 0; var12 < this.field_178168_c; ++var12) {
               int var13 = var12 * 16;
               RenderChunk var14 = this.field_178164_f[(var10 * this.field_178168_c + var12) * this.field_178165_d + var8];
               BlockPos var15 = var14.func_178568_j();
               if (var15.getX() != var9 || var15.getY() != var13 || var15.getZ() != var11) {
                  BlockPos var16 = new BlockPos(var9, var13, var11);
                  if (!var16.equals(var14.func_178568_j())) {
                     var14.func_178576_a(var16);
                  }
               }
            }
         }
      }

   }

   private int func_178157_a(int var1, int var2, int var3) {
      int var4 = var3 * 16;
      int var5 = var4 - var1 + var2 / 2;
      if (var5 < 0) {
         var5 -= var2 - 1;
      }

      return var4 - var5 / var2 * var2;
   }

   protected void func_178159_a(int var1) {
      int var2 = var1 * 2 + 1;
      this.field_178165_d = var2;
      this.field_178168_c = 16;
      this.field_178166_e = var2;
   }

   protected RenderChunk func_178161_a(BlockPos var1) {
      int var2 = MathHelper.bucketInt(var1.getX(), 16);
      int var3 = MathHelper.bucketInt(var1.getY(), 16);
      int var4 = MathHelper.bucketInt(var1.getZ(), 16);
      if (var3 >= 0 && var3 < this.field_178168_c) {
         var2 %= this.field_178165_d;
         if (var2 < 0) {
            var2 += this.field_178165_d;
         }

         var4 %= this.field_178166_e;
         if (var4 < 0) {
            var4 += this.field_178166_e;
         }

         int var5 = (var4 * this.field_178168_c + var3) * this.field_178165_d + var2;
         return this.field_178164_f[var5];
      } else {
         return null;
      }
   }

   public void func_178160_a() {
      RenderChunk[] var1 = this.field_178164_f;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         RenderChunk var4 = var1[var3];
         var4.func_178566_a();
      }

   }

   public void func_178162_a(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = MathHelper.bucketInt(var1, 16);
      int var8 = MathHelper.bucketInt(var2, 16);
      int var9 = MathHelper.bucketInt(var3, 16);
      int var10 = MathHelper.bucketInt(var4, 16);
      int var11 = MathHelper.bucketInt(var5, 16);
      int var12 = MathHelper.bucketInt(var6, 16);

      for(int var13 = var7; var13 <= var10; ++var13) {
         int var14 = var13 % this.field_178165_d;
         if (var14 < 0) {
            var14 += this.field_178165_d;
         }

         for(int var15 = var8; var15 <= var11; ++var15) {
            int var16 = var15 % this.field_178168_c;
            if (var16 < 0) {
               var16 += this.field_178168_c;
            }

            for(int var17 = var9; var17 <= var12; ++var17) {
               int var18 = var17 % this.field_178166_e;
               if (var18 < 0) {
                  var18 += this.field_178166_e;
               }

               int var19 = (var18 * this.field_178168_c + var16) * this.field_178165_d + var14;
               RenderChunk var20 = this.field_178164_f[var19];
               var20.func_178575_a(true);
            }
         }
      }

   }

   protected void func_178158_a(IRenderChunkFactory var1) {
      int var2 = this.field_178165_d * this.field_178168_c * this.field_178166_e;
      this.field_178164_f = new RenderChunk[var2];
      int var3 = 0;

      for(int var4 = 0; var4 < this.field_178165_d; ++var4) {
         for(int var5 = 0; var5 < this.field_178168_c; ++var5) {
            for(int var6 = 0; var6 < this.field_178166_e; ++var6) {
               int var7 = (var6 * this.field_178168_c + var5) * this.field_178165_d + var4;
               BlockPos var8 = new BlockPos(var4 * 16, var5 * 16, var6 * 16);
               this.field_178164_f[var7] = var1.func_178602_a(this.field_178167_b, this.field_178169_a, var8, var3++);
            }
         }
      }

   }
}
