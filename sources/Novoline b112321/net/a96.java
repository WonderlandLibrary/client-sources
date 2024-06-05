package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import net.EN;

final class a96 extends PacketRemapper {
   public void registerMap() {
      this.a(a96::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      ((Cooldown)var0.e().b(Cooldown.class)).hit();
   }
}
