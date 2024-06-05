package net;

import com.google.common.collect.ComparisonChain;

public class axY {
   public static ComparisonChain a() {
      return ComparisonChain.start();
   }

   public static ComparisonChain a(ComparisonChain var0, double var1, double var3) {
      return var0.compare(var1, var3);
   }

   public static int a(ComparisonChain var0) {
      return var0.result();
   }

   public static ComparisonChain a(ComparisonChain var0, int var1, int var2) {
      return var0.compare(var1, var2);
   }

   public static ComparisonChain a(ComparisonChain var0, boolean var1, boolean var2) {
      return var0.compareTrueFirst(var1, var2);
   }

   public static ComparisonChain a(ComparisonChain var0, Comparable var1, Comparable var2) {
      return var0.compare(var1, var2);
   }
}
