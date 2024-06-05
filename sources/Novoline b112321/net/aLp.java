package net;

import net.a87;
import net.acE;
import org.lwjgl.util.vector.Vector3f;

public class aLp {
   public static float a(float var0, float var1) {
      String[] var2 = a87.b();
      return var0 > var1?var1:var0;
   }

   public static Vector3f d(Vector3f var0, float var1) {
      a87.b();
      if(var0.x > var1) {
         var0.x = var1;
      }

      if(var0.y > var1) {
         var0.y = var1;
      }

      if(var0.z > var1) {
         var0.z = var1;
      }

      return var0;
   }

   public static float b(float var0, float var1) {
      String[] var2 = a87.b();
      return var0 < var1?var1:var0;
   }

   public static Vector3f b(Vector3f var0, Vector3f var1) {
      var0.x += var1.x;
      var0.y += var1.y;
      var0.z += var1.z;
      return var0;
   }

   public static Vector3f a(Vector3f var0, Vector3f var1) {
      var0.x *= var1.x;
      var0.y *= var1.y;
      var0.z *= var1.z;
      return var0;
   }

   public static Vector3f b(Vector3f var0, float var1) {
      Vector3f var3 = new Vector3f();
      Vector3f var4 = new Vector3f();
      var3.y = (float)Math.cos((double)((180.0F + var1) / 180.0F) * 3.141592653589793D);
      var3.z = (float)Math.sin((double)((180.0F + var1) / 180.0F) * 3.141592653589793D);
      var3.normalise();
      a87.b();
      var3.y *= -var0.y;
      var3.z *= var0.y;
      var4.y = (float)Math.sin((double)((180.0F + var1) / 180.0F) * 3.141592653589793D);
      var4.z = (float)Math.cos((double)((180.0F + var1) / 180.0F) * 3.141592653589793D);
      var4.normalise();
      var4.y *= -var0.z;
      var4.z *= -var0.z;
      var0 = new Vector3f(var0.x, var3.y + var4.y, var3.z + var4.z);
      if(acE.b() == null) {
         a87.b(new String[2]);
      }

      return var0;
   }

   public static Vector3f c(Vector3f var0, float var1) {
      Vector3f var2 = new Vector3f();
      Vector3f var3 = new Vector3f();
      var2.x = (float)Math.cos((double)(-var1 / 180.0F) * 3.141592653589793D);
      var2.z = (float)Math.sin((double)(-var1 / 180.0F) * 3.141592653589793D);
      var2.normalise();
      var2.x *= -var0.x;
      var2.z *= var0.x;
      var3.x = (float)Math.sin((double)(-var1 / 180.0F) * 3.141592653589793D);
      var3.z = (float)Math.cos((double)(-var1 / 180.0F) * 3.141592653589793D);
      var3.normalise();
      var3.x *= var0.z;
      var3.z *= var0.z;
      var0 = new Vector3f(var2.x + var3.x, var0.y, var2.z + var3.z);
      return var0;
   }

   public static Vector3f a(Vector3f var0, float var1) {
      Vector3f var3 = new Vector3f();
      Vector3f var4 = new Vector3f();
      var3.x = (float)Math.sin((double)((var1 - 90.0F) / 180.0F) * 3.141592653589793D);
      a87.b();
      var3.y = (float)Math.cos((double)((var1 - 90.0F) / 180.0F) * 3.141592653589793D);
      var3.normalise();
      var3.x *= -var0.x;
      var3.y *= var0.x;
      var4.x = (float)Math.cos((double)((var1 - 90.0F) / 180.0F) * 3.141592653589793D);
      var4.y = (float)Math.sin((double)((var1 - 90.0F) / 180.0F) * 3.141592653589793D);
      var4.normalise();
      var4.x *= -var0.y;
      var4.y *= -var0.y;
      var0 = new Vector3f(var4.x + var3.x, var4.y + var3.y, var0.z);
      return var0;
   }

   public static Vector3f[] a(Vector3f[] var0, Vector3f var1) {
      a87.b();
      int var3 = 0;
      if(var3 < var0.length) {
         var0[var3] = b(var0[var3], var1);
         ++var3;
      }

      return var0;
   }

   public static Vector3f[] b(Vector3f[] var0, Vector3f var1) {
      a87.b();
      int var3 = 0;
      if(var3 < var0.length) {
         var0[var3] = a(var0[var3], var1);
         ++var3;
      }

      return var0;
   }

   public static Vector3f[] c(Vector3f[] var0, float var1) {
      a87.b();
      int var3 = 0;
      if(var3 < var0.length) {
         var0[var3] = b(var0[var3], var1);
         ++var3;
      }

      return var0;
   }

   public static Vector3f[] b(Vector3f[] var0, float var1) {
      a87.b();
      int var3 = 0;
      if(var3 < var0.length) {
         var0[var3] = c(var0[var3], var1);
         ++var3;
      }

      return var0;
   }

   public static Vector3f[] a(Vector3f[] var0, float var1) {
      a87.b();
      int var3 = 0;
      if(var3 < var0.length) {
         var0[var3] = a(var0[var3], var1);
         ++var3;
      }

      return var0;
   }
}
