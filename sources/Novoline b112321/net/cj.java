package net;

import org.jetbrains.annotations.NotNull;

public final class cj implements Comparable {
   public double c;
   public double d;
   public String b;
   private static final String a = "CL_00001498";

   public cj(String var1, double var2, double var4) {
      this.b = var1;
      this.c = var2;
      this.d = var4;
   }

   public int a(cj var1) {
      return var1.c < this.c?-1:(var1.c > this.c?1:var1.b.compareTo(this.b));
   }

   public int a() {
      return (this.b.hashCode() & 11184810) + 4473924;
   }

   public int compareTo(@NotNull Object var1) {
      return this.a((cj)var1);
   }
}
