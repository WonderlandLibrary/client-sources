package net.skidunion;

import java.util.Random;
import net.skidunion.j;
import novoline0.Loader;

public class aJ {
   private j b;
   private static Random c;
   static final char[] a;
   private static final String[] d;
   private static final String[] e;

   public aJ() {
      this.b();
   }

   private native void b();

   public native String b(String var1);

   private native String a(String var1, long var2);

   public native String a(String var1);

   public native void a();

   private static native long a(byte[] var0, int var1);

   private static native void a(long var0, byte[] var2, int var3);

   private static native long a(int[] var0, int var1);

   private static native void a(long var0, int[] var2, int var3);

   private static native long a(int var0, int var1);

   private static native int a(long var0);

   private static native int b(long var0);

   private static native String b(byte[] var0, int var1, int var2);

   private static native int a(String var0, byte[] var1, int var2, int var3, int var4);

   private static native String a(byte[] var0, int var1, int var2);

   static {
      Loader.registerNativesForClass(3);
      native_special_clinit16();
   }

   private static native String b(int var0, int var1);

   // $FF: synthetic method
   private static native void native_special_clinit16();
}
