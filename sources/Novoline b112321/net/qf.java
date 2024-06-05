package net;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.stream.IntStream;

public class qf {
   private static int b;

   public static String a(CharSequence var0, CharSequence[] var1) {
      return String.join(var0, var1);
   }

   public static int h(String var0) {
      return var0.length();
   }

   public static String b(String var0, int var1, int var2) {
      return var0.substring(var1, var2);
   }

   public static boolean a(String var0, CharSequence var1) {
      return var0.contains(var1);
   }

   public static String c(String var0, int var1) {
      return var0.substring(var1);
   }

   public static boolean i(String var0, String var1) {
      return var0.equalsIgnoreCase(var1);
   }

   public static boolean c(String var0, String var1) {
      return var0.endsWith(var1);
   }

   public static String a(String var0, Object[] var1) {
      return String.format(var0, var1);
   }

   public static boolean f(String var0) {
      return var0.isEmpty();
   }

   public static String c(String var0) {
      return var0.toLowerCase();
   }

   public static int h(String var0, String var1) {
      return var0.compareTo(var1);
   }

   public static String a(String var0, CharSequence var1, CharSequence var2) {
      return var0.replace(var1, var2);
   }

   public static String e(String var0) {
      return var0.toUpperCase();
   }

   public static String[] j(String var0, String var1) {
      return var0.split(var1);
   }

   public static int d(String var0, int var1) {
      return var0.lastIndexOf(var1);
   }

   public static String a(Object var0) {
      return String.valueOf(var0);
   }

   public static char b(String var0, int var1) {
      return var0.charAt(var1);
   }

   public static boolean a(String var0, String var1) {
      return var0.startsWith(var1);
   }

   public static String c(int var0) {
      return String.valueOf(var0);
   }

   public static String a(boolean var0) {
      return String.valueOf(var0);
   }

   public static byte[] e(String var0, String var1) {
      return var0.getBytes(var1);
   }

   public static String a(String var0) {
      return var0.trim();
   }

   public static int b(String var0, String var1) {
      return var0.indexOf(var1);
   }

   public static int f(String var0, String var1) {
      return var0.lastIndexOf(var1);
   }

   public static byte[] a(String var0, Charset var1) {
      return var0.getBytes(var1);
   }

   public static String[] b(String var0, String var1, int var2) {
      return var0.split(var1, var2);
   }

   public static String a(char var0) {
      return String.valueOf(var0);
   }

   public static int a(String var0, int var1) {
      return var0.indexOf(var1);
   }

   public static char[] d(String var0) {
      return var0.toCharArray();
   }

   public static String b(String var0, Locale var1) {
      return var0.toLowerCase(var1);
   }

   public static String a(String var0, String var1, String var2) {
      return var0.replaceAll(var1, var2);
   }

   public static int d(String var0, String var1) {
      return var0.compareToIgnoreCase(var1);
   }

   public static boolean a(String var0, boolean var1, int var2, String var3, int var4, int var5) {
      return var0.regionMatches(var1, var2, var3, var4, var5);
   }

   public static String a(String var0, Locale var1) {
      return var0.toUpperCase(var1);
   }

   public static String a(String var0, char var1, char var2) {
      return var0.replace(var1, var2);
   }

   public static boolean k(String var0, String var1) {
      return var0.matches(var1);
   }

   public static String a(Locale var0, String var1, Object[] var2) {
      return String.format(var0, var1, var2);
   }

   public static String a(double var0) {
      return String.valueOf(var0);
   }

   public static int a(String var0, int var1, int var2) {
      return var0.indexOf(var1, var2);
   }

   public static int a(String var0, String var1, int var2) {
      return var0.indexOf(var1, var2);
   }

   public static String g(String var0, String var1) {
      return var0.concat(var1);
   }

   public static String a(long var0) {
      return String.valueOf(var0);
   }

   public static byte[] g(String var0) {
      return var0.getBytes();
   }

   public static IntStream b(String var0) {
      return var0.chars();
   }

   public static String b(String var0, String var1, String var2) {
      return var0.replaceFirst(var1, var2);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 24;
   }

   static {
      if(b() != 0) {
         b(46);
      }

   }
}
