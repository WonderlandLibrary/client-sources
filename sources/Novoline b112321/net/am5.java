package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;

public enum am5 {
   SCALAR,
   MAP,
   LIST,
   NULL;

   private static PacketRemapper[] b;

   public boolean c() {
      PacketRemapper[] var1 = b();
      return this == MAP || this == LIST;
   }

   static {
      b(new PacketRemapper[4]);
   }

   public static void b(PacketRemapper[] var0) {
      b = var0;
   }

   public static PacketRemapper[] b() {
      return b;
   }
}
