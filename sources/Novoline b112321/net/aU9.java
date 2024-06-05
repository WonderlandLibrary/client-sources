package net;

import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;

public class aU9 {
   private static String b;

   public static void a(CrashReportCategory var0, String var1, Callable var2) {
      var0.addCrashSectionCallable(var1, var2);
   }

   public static void a(CrashReportCategory var0, String var1, Object var2) {
      var0.addCrashSection(var1, var2);
   }

   public static String a(BlockPos var0) {
      return CrashReportCategory.getCoordinateInfo(var0);
   }

   public static void a(CrashReportCategory var0, BlockPos var1, Block var2, int var3) {
      CrashReportCategory.addBlockInfo(var0, var1, var2, var3);
   }

   public static void a(CrashReportCategory var0, BlockPos var1, IBlockState var2) {
      CrashReportCategory.addBlockInfo(var0, var1, var2);
   }

   public static StackTraceElement[] a(CrashReportCategory var0) {
      return var0.getStackTrace();
   }

   public static void a(CrashReportCategory var0, StringBuilder var1) {
      var0.appendToStringBuilder(var1);
   }

   public static int a(CrashReportCategory var0, int var1) {
      return var0.getPrunedStackTrace(var1);
   }

   public static boolean a(CrashReportCategory var0, StackTraceElement var1, StackTraceElement var2) {
      return var0.firstTwoElementsOfStackTraceMatch(var1, var2);
   }

   public static void b(CrashReportCategory var0, int var1) {
      var0.trimStackTraceEntriesFromBottom(var1);
   }

   public static String a(double var0, double var2, double var4) {
      return CrashReportCategory.getCoordinateInfo(var0, var2, var4);
   }

   public static void a(CrashReportCategory var0, String var1, Throwable var2) {
      var0.addCrashSectionThrowable(var1, var2);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("uQnxoc");
      }

   }
}
