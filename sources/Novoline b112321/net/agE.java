package net;

import javax.vecmath.Matrix4f;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ITransformation;

public class agE {
   private static int b;

   public static EnumFacing a(ITransformation var0, EnumFacing var1) {
      return var0.rotate(var1);
   }

   public static Matrix4f a(ITransformation var0) {
      return var0.getMatrix();
   }

   public static int a(ITransformation var0, EnumFacing var1, int var2) {
      return var0.rotate(var1, var2);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 5;
   }

   static {
      if(b() != 0) {
         b(95);
      }

   }
}
