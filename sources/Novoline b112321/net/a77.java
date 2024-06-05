package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.S3;

final class a77 extends PacketRemapper {
   public void registerMap() {
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(a77::lambda$registerMap$0);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      int var2 = ((Integer)var0.b(Type.I)).intValue();
      var0.a(Type.I, Integer.valueOf(var2));
      S3.b();
      int var3 = 0;
      if(var3 < var2) {
         var0.a(Type.M);
         var0.a(Type.M);
         var0.a(Type.M);
         ++var3;
      }

   }
}
