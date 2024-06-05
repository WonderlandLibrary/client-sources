package net;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import java.util.Set;

public class aps {
   public static void a(Properties var0, InputStream var1) {
      var0.load(var1);
   }

   public static String a(Properties var0, String var1) {
      return var0.getProperty(var1);
   }

   public static Object a(Properties var0, Object var1, Object var2) {
      return var0.put(var1, var2);
   }

   public static Object b(Properties var0, String var1, String var2) {
      return var0.setProperty(var1, var2);
   }

   public static void a(Properties var0, Reader var1) {
      var0.load(var1);
   }

   public static String a(Properties var0, String var1, String var2) {
      return var0.getProperty(var1, var2);
   }

   public static void a(Properties var0, Writer var1, String var2) {
      var0.store(var1, var2);
   }

   public static Set a(Properties var0) {
      return var0.keySet();
   }

   public static boolean b(Properties var0) {
      return var0.isEmpty();
   }

   public static void a(Properties var0, OutputStream var1, String var2) {
      var0.store(var1, var2);
   }

   public static int c(Properties var0) {
      return var0.size();
   }

   public static Object a(Properties var0, Object var1) {
      return var0.get(var1);
   }
}
