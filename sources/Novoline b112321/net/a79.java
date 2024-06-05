package net;

import net.S3;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a79 extends acE {
   public void registerMap() {
      this.a(Type.STRING);
      this.a(a79::lambda$registerMap$0);
      this.a(a79::lambda$registerMap$1);
      this.a(Type.INT);
      this.a(Type.INT);
      this.a(Type.INT);
      this.a(Type.FLOAT);
      this.a(Type.UNSIGNED_BYTE);
   }

   private static void lambda$registerMap$1(PacketWrapper var0) throws Exception {
      Integer var10000 = (Integer)var0.read(Type.VAR_INT);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      String var2 = (String)var0.get(Type.STRING, 0);
      var2 = SoundRemapper.getOldName(var2);
      var0.cancel();
      var0.set(Type.STRING, 0, var2);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
