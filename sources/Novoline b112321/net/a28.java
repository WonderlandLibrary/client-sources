package net;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class a28 {
   private static int[] b;

   public static void c(Reader var0) {
      IOUtils.closeQuietly(var0);
   }

   public static String b(InputStream var0, Charset var1) {
      return IOUtils.toString(var0, var1);
   }

   public static void a(Writer var0) {
      IOUtils.closeQuietly(var0);
   }

   public static void a(InputStream var0) {
      IOUtils.closeQuietly(var0);
   }

   public static List a(InputStream var0, Charset var1) {
      return IOUtils.readLines(var0, var1);
   }

   public static byte[] b(InputStream var0) {
      return IOUtils.toByteArray(var0);
   }

   public static List a(Reader var0) {
      return IOUtils.readLines(var0);
   }

   public static void a(Closeable var0) {
      IOUtils.closeQuietly(var0);
   }

   public static String b(Reader var0) {
      return IOUtils.toString(var0);
   }

   public static void a(OutputStream var0) {
      IOUtils.closeQuietly(var0);
   }

   public static String c(InputStream var0) {
      return IOUtils.toString(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[1]);
      }

   }
}
