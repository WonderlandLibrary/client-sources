package net;

import java.util.UUID;
import viaversion.viaversion.api.type.types.UUIDIntArrayType;

public class at5 {
   public static int[] a(UUID var0) {
      return UUIDIntArrayType.uuidToIntArray(var0);
   }

   public static UUID a(int[] var0) {
      return UUIDIntArrayType.uuidFromIntArray(var0);
   }

   public static int[] a(long var0, long var2) {
      return UUIDIntArrayType.bitsToIntArray(var0, var2);
   }
}
