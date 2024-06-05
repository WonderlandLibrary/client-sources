package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.Eb;
import net.aSv;
import net.ahs;

public abstract class apC implements Eb, ahs {
   private boolean a;
   private static PacketRemapper[] b;

   @aSv
   public boolean isCancelled() {
      return this.a;
   }

   @aSv
   public void setCancelled(boolean var1) {
      this.a = var1;
   }

   public static void b(PacketRemapper[] var0) {
      b = var0;
   }

   public static PacketRemapper[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new PacketRemapper[1]);
      }

   }
}
