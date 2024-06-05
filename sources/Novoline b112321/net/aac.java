package net;

public class aac {
   public static boolean a(StackTraceElement var0) {
      return var0.isNativeMethod();
   }

   public static String d(StackTraceElement var0) {
      return var0.getClassName();
   }

   public static String c(StackTraceElement var0) {
      return var0.getFileName();
   }

   public static String e(StackTraceElement var0) {
      return var0.getMethodName();
   }

   public static int b(StackTraceElement var0) {
      return var0.getLineNumber();
   }
}
