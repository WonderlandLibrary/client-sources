package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
import net.EN;

final class aDw extends PacketRemapper {
   public void registerMap() {
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.c);
      this.a(aDw::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      PlayerPosition var1 = (PlayerPosition)var0.e().b(PlayerPosition.class);
      var1.b(((Float)var0.b(Type.FLOAT, 0)).floatValue());
      var1.a(((Float)var0.b(Type.FLOAT, 1)).floatValue());
      var1.c(((Boolean)var0.b(Type.c, 0)).booleanValue());
   }
}
