package net;

import net.a10;
import net.aLp;
import org.lwjgl.util.vector.Vector3f;

public class a87 {
   public Vector3f e = new Vector3f(0.0F, 0.0F, 0.0F);
   public Vector3f a = new Vector3f(0.0F, 0.0F, 0.0F);
   public Vector3f b = new Vector3f(0.0F, 0.0F, 0.0F);
   public Vector3f d = new Vector3f(1.0F, 1.0F, 1.0F);
   public Vector3f f = new Vector3f(0.0F, 0.0F, 0.0F);
   private static String[] c;

   public void a(Vector3f var1, float var2) {
      String[] var3 = b();
      if(this.b != var1) {
         this.b = var1;
         this.e = this.a;
         this.f = new Vector3f(0.0F, 0.0F, 0.0F);
         this.d = new Vector3f(var2, var2, var2);
      }

   }

   public void a() {
      this.d(1.0F);
   }

   public void d(float var1) {
      this.a(new Vector3f(0.0F, 0.0F, 0.0F), var1);
   }

   public void a(Vector3f var1) {
      this.a(var1, 1.0F);
   }

   public void a(a10 var1, float var2, float var3) {
      String[] var4 = b();
      if((var1 == a10.X?this.b.x:(var1 == a10.Y?this.b.y:this.b.z)) != var2) {
         if(var1 == a10.X) {
            this.b.x = var2;
         }

         if(var1 == a10.Y) {
            this.b.y = var2;
         }

         if(var1 == a10.Z) {
            this.b.z = var2;
         }

         if(var1 == a10.X) {
            this.e.x = this.a.x;
         }

         if(var1 == a10.Y) {
            this.e.y = this.a.y;
         }

         if(var1 == a10.Z) {
            this.e.z = this.a.z;
         }

         if(var1 == a10.X) {
            this.f.x = 0.0F;
         }

         if(var1 == a10.Y) {
            this.f.y = 0.0F;
         }

         if(var1 == a10.Z) {
            this.f.z = 0.0F;
         }
      }

      if(var1 == a10.X) {
         this.d.x = var3;
      }

      if(var1 == a10.Y) {
         this.d.y = var3;
      }

      if(var1 == a10.Z) {
         this.d.z = var3;
      }

   }

   public void a(float var1, float var2) {
      String[] var3 = b();
      if(this.b.x != var1) {
         this.b.x = var1;
         this.e.x = this.a.x;
         this.f.x = 0.0F;
      }

      this.d.x = var2;
   }

   public void c(float var1, float var2) {
      String[] var3 = b();
      if(this.b.y != var1) {
         this.b.y = var1;
         this.e.y = this.a.y;
         this.f.y = 0.0F;
      }

      this.d.y = var2;
   }

   public void b(float var1, float var2) {
      String[] var3 = b();
      if(this.b.z != var1) {
         this.b.z = var1;
         this.e.z = this.a.z;
         this.f.z = 0.0F;
      }

      this.d.z = var2;
   }

   public void c(float var1) {
      this.a(var1, 0.6F);
   }

   public void e(float var1) {
      this.c(var1, 0.6F);
   }

   public void h(float var1) {
      this.b(var1, 0.6F);
   }

   public void f(float var1) {
      this.e.x = var1;
      this.a.x = var1;
      this.b.x = var1;
      this.f.x = 1.0F;
   }

   public void g(float var1) {
      this.e.y = var1;
      this.a.y = var1;
      this.b.y = var1;
      this.f.y = 1.0F;
   }

   public void b(float var1) {
      this.e.z = var1;
      this.a.z = var1;
      this.b.z = var1;
      this.f.z = 1.0F;
   }

   public void a(a87 var1) {
      this.f = var1.f;
      this.d = var1.d;
      this.b = var1.b;
      this.e = var1.e;
      this.a = var1.a;
   }

   public float c() {
      return this.a.x;
   }

   public float d() {
      return this.a.y;
   }

   public float e() {
      return this.a.z;
   }

   public void a(float var1) {
      this.f.x += var1 * this.d.x;
      b();
      this.f.y += var1 * this.d.y;
      this.f.z += var1 * this.d.z;
      this.f = aLp.d(this.f, 1.0F);
      if(this.f.x >= 1.0F) {
         this.e.x = this.a.x = this.b.x;
      }

      this.a.x = this.e.x + (this.b.x - this.e.x) * this.f.x;
      if(this.f.y >= 1.0F) {
         this.e.y = this.a.y = this.b.y;
      }

      this.a.y = this.e.y + (this.b.y - this.e.y) * this.f.y;
      if(this.f.z >= 1.0F) {
         this.e.z = this.a.z = this.b.z;
      }

      this.a.z = this.e.z + (this.b.z - this.e.z) * this.f.z;
      this.a(new Vector3f(0.0F, 0.0F, 0.0F), 0.5F);
   }

   public static void b(String[] var0) {
      c = var0;
   }

   public static String[] b() {
      return c;
   }

   static {
      if(b() != null) {
         b(new String[2]);
      }

   }
}
