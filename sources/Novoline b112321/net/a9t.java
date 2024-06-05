package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import net.EN;
import net.cT;

final class a9t extends PacketRemapper {
   public void registerMap() {
      this.a(Type.I);
      this.a(Type.M);
      this.a(Type.M);
      this.a(Type.STRING);
      this.a(a9t::lambda$registerMap$0);
      this.a(a9t::lambda$registerMap$1);
      this.a(a9t::lambda$registerMap$2);
   }

   private static void lambda$registerMap$2(PacketWrapperImpl var0) throws Exception {
      cT var1 = (cT)var0.e().b(cT.class);
      var1.c(((Integer)var0.b(Type.I, 0)).intValue());
   }

   private static void lambda$registerMap$1(PacketWrapperImpl var0) throws Exception {
      ((BossBarStorage)var0.e().b(BossBarStorage.class)).updateLocation();
      ((BossBarStorage)var0.e().b(BossBarStorage.class)).changeWorld();
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      ((EntityTracker)var0.e().b(EntityTracker.class)).setPlayerGamemode(((Short)var0.b(Type.M, 1)).shortValue());
   }
}
