package cc.novoline.utils.java;

import java.lang.ref.WeakReference;

public final class ReferenceUtil {
   private static final WeakReference WEAK_REFERENCE_STUB = new WeakReference((Object)null);

   private ReferenceUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static WeakReference weakReferenceStub() {
      return WEAK_REFERENCE_STUB;
   }
}
