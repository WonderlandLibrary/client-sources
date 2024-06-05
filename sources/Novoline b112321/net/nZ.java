package net;

import java.util.Objects;
import net.OL;
import net.nW;

public abstract class nZ extends nW implements OL {
   protected int h;
   private static int[] g;

   public nZ(int var1, int var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.h = var1;
   }

   public int e() {
      return this.h;
   }

   public void f(int var1) {
      this.h = var1;
   }

   public boolean equals(Object var1) {
      int[] var2 = a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof nZ)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         nZ var3 = (nZ)var1;
         return this.h == var3.h;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), Integer.valueOf(this.h)});
   }

   public String toString() {
      return "AbstractGroup{color=" + this.h + '}';
   }

   public static void a(int[] var0) {
      g = var0;
   }

   public static int[] a() {
      return g;
   }

   static {
      if(a() == null) {
         a(new int[2]);
      }

   }
}
