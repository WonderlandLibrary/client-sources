package net.minecraft.client.renderer.block.model;

public class BlockFaceUV {
   public float[] uvs;
   public final int rotation;

   public BlockFaceUV(float[] var1, int var2) {
      this.uvs = var1;
      this.rotation = var2;
   }

   public float func_178348_a(int var1) {
      if(this.uvs == null) {
         throw new NullPointerException("uvs");
      } else {
         int var2 = this.func_178347_d(var1);
         return var2 != 1?this.uvs[2]:this.uvs[0];
      }
   }

   public float func_178346_b(int var1) {
      if(this.uvs == null) {
         throw new NullPointerException("uvs");
      } else {
         int var2 = this.func_178347_d(var1);
         return var2 != 3?this.uvs[3]:this.uvs[1];
      }
   }

   private int func_178347_d(int var1) {
      return (var1 + this.rotation / 90) % 4;
   }

   public int func_178345_c(int var1) {
      return (var1 + 4 - this.rotation / 90) % 4;
   }

   public void setUvs(float[] var1) {
      if(this.uvs == null) {
         this.uvs = var1;
      }

   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
