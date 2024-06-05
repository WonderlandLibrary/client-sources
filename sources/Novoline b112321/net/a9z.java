package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import net.EN;
import net.cT;
import net.t4;

final class a9z extends PacketRemapper {
   public void registerMap() {
      this.a(Type.I);
      this.a(Type.M);
      this.a(Type.k);
      this.a(Type.M);
      this.a(Type.M);
      this.a(Type.STRING);
      this.a(Type.c);
      this.a(a9z::lambda$registerMap$0);
      this.a(a9z::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapperImpl var0) throws Exception {
      cT var1 = (cT)var0.e().b(cT.class);
      var1.c(((Byte)var0.b(Type.k, 0)).byteValue());
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      EntityTracker var1 = (EntityTracker)var0.e().b(EntityTracker.class);
      var1.setPlayerId(((Integer)var0.b(Type.I, 0)).intValue());
      var1.setPlayerGamemode(((Short)var0.b(Type.M, 0)).shortValue());
      var1.getClientEntityTypes().put(Integer.valueOf(var1.getPlayerId()), t4.ENTITY_HUMAN);
   }
}
