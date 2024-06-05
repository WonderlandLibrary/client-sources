package net.skidunion;

import net.skidunion.i;
import novoline0.Loader;

public class k extends i {
   long p;

   public native long a();

   public native void c(byte[] var1);

   public native void a(long var1);

   public native void d(byte[] var1);

   public k() {
      this.a(0L);
   }

   public k(long var1) {
      this.a(var1);
   }

   public k(byte[] var1) {
      this.d(var1);
   }

   public native void a();

   private native long b(long var1);

   private native long d(long var1);

   public native void a(byte[] var1, byte[] var2);

   public native void b(byte[] var1);

   public native void a(int[] var1, int[] var2);

   public native void a(int[] var1);

   public native void a(long[] var1, long[] var2);

   public native void a(long[] var1);

   public native void b(byte[] var1, byte[] var2);

   public native void a(byte[] var1);

   public native void b(int[] var1, int[] var2);

   public native void b(int[] var1);

   public native void b(long[] var1, long[] var2);

   public native void b(long[] var1);

   static {
      Loader.registerNativesForClass(6);
      native_special_clinit22();
   }

   // $FF: synthetic method
   private static native void native_special_clinit22();
}
