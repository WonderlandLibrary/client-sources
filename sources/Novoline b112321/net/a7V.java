package net;

import net.S3;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
import viaversion.viarewind.protocol.protocol1_8to1_9.sound.Effect;
import viaversion.viarewind.storage.BlockState;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a7V extends acE {
   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.POSITION);
      this.a(Type.INT);
      this.a(Type.BOOLEAN);
      this.a(a7V::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      int var2 = ((Integer)var0.get(Type.INT, 0)).intValue();
      var2 = Effect.getOldId(var2);
      if(var2 == -1) {
         var0.cancel();
      } else {
         var0.set(Type.INT, 0, Integer.valueOf(var2));
         if(var2 == 2001) {
            BlockState var3 = BlockState.rawToState(((Integer)var0.get(Type.INT, 1)).intValue());
            var3 = ReplacementRegistry1_8to1_9.replace(var3);
            var0.set(Type.INT, 1, Integer.valueOf(BlockState.stateToRaw(var3)));
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
