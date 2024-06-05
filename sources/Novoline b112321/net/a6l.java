package net;

import java.lang.ref.WeakReference;

public final class a6l {
   private static final WeakReference a = new WeakReference((Object)null);

   private a6l() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static WeakReference a() {
      return a;
   }
}
