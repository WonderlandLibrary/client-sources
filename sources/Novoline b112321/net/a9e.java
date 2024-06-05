package net;

import io.netty.buffer.ByteBuf;
import net.aRE;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.packets.PlayerPackets$17$1;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;

final class a9e extends acE {
   public void registerMap() {
      this.a(this::lambda$registerMap$0);
      this.a(a9e::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapper var0) throws Exception {
      ((BlockPlaceDestroyTracker)var0.user().b(BlockPlaceDestroyTracker.class)).updateMining();
      ((Cooldown)var0.user().b(Cooldown.class)).hit();
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      var1.cancel();
      PacketWrapper var2 = new PacketWrapper(26, (ByteBuf)null, var1.user());
      var2.write(Type.VAR_INT, Integer.valueOf(0));
      aRE.l.schedule(new PlayerPackets$17$1(this, var2), 5L);
   }
}
