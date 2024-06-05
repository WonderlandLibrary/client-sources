package net;

import net.S3;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a9q extends acE {
   public void registerMap() {
      this.a(Type.BYTE, Type.VAR_INT);
      this.a(Type.POSITION);
      this.a(Type.BYTE);
      this.a(a9q::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      int var2 = ((Integer)var0.get(Type.VAR_INT, 0)).intValue();
      if(var2 == 0) {
         ((BlockPlaceDestroyTracker)var0.user().b(BlockPlaceDestroyTracker.class)).setMining(true);
      }

      if(var2 == 2) {
         BlockPlaceDestroyTracker var3 = (BlockPlaceDestroyTracker)var0.user().b(BlockPlaceDestroyTracker.class);
         var3.setMining(false);
         var3.setLastMining(System.currentTimeMillis() + 100L);
         ((Cooldown)var0.user().b(Cooldown.class)).setLastHit(0L);
      }

      if(var2 == 1) {
         BlockPlaceDestroyTracker var4 = (BlockPlaceDestroyTracker)var0.user().b(BlockPlaceDestroyTracker.class);
         var4.setMining(false);
         var4.setLastMining(0L);
         ((Cooldown)var0.user().b(Cooldown.class)).hit();
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
