package net;

import java.io.File;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;

public class a1h {
   private static String b;

   public static CrashReport a(Throwable var0, String var1) {
      return CrashReport.makeCrashReport(var0, var1);
   }

   public static CrashReportCategory a(CrashReport var0, String var1) {
      return var0.makeCategory(var1);
   }

   public static Throwable b(CrashReport var0) {
      return var0.getCrashCause();
   }

   public static String a(CrashReport var0) {
      return var0.getCompleteReport();
   }

   public static String e(CrashReport var0) {
      return var0.getDescription();
   }

   public static File c(CrashReport var0) {
      return var0.getFile();
   }

   public static boolean a(CrashReport var0, File var1) {
      return var0.saveToFile(var1);
   }

   public static CrashReportCategory d(CrashReport var0) {
      return var0.getCategory();
   }

   public static CrashReportCategory a(CrashReport var0, String var1, int var2) {
      return var0.makeCategoryDepth(var1, var2);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("XOClTb");
      }

   }
}
