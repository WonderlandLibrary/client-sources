package net;

import java.util.Objects;
import net.cf;

public class a2_ {
   private short b = 0;
   private short c = 0;
   private short a = 0;
   private short d = 200;

   public a2_() {
   }

   public a2_(short var1, short var2, short var3, short var4) {
      this.b = var1;
      this.c = var2;
      this.a = var3;
      this.d = var4;
   }

   public short a() {
      return this.b;
   }

   public void c(short var1) {
      this.b = var1;
   }

   public short d() {
      return this.c;
   }

   public void d(short var1) {
      this.c = var1;
   }

   public short c() {
      return this.a;
   }

   public void a(short var1) {
      this.a = var1;
   }

   public short b() {
      return this.d;
   }

   public void b(short var1) {
      this.d = var1;
   }

   public boolean equals(Object var1) {
      String var2 = cf.b();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof a2_)) {
         return false;
      } else {
         a2_ var3 = (a2_)var1;
         return this.b == var3.b && this.c == var3.c && this.a == var3.a && this.d == var3.d;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Short.valueOf(this.b), Short.valueOf(this.c), Short.valueOf(this.a), Short.valueOf(this.d)});
   }

   public String toString() {
      return "Furnace{fuelLeft=" + this.b + ", maxFuel=" + this.c + ", progress=" + this.a + ", maxProgress=" + this.d + '}';
   }
}
