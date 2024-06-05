package net;

import net.S3;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a9M extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(a9M::lambda$registerMap$0);
      this.a(Type.ITEM);
      this.a(a9M::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapper var0) throws Exception {
      var0.set(Type.ITEM, 0, ItemRewriter.toClient((Item)var0.get(Type.ITEM, 0)));
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      int var2 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      if(var2 == 1) {
         var0.cancel();
      }

      if(var2 > 1) {
         --var2;
      }

      var0.write(Type.SHORT, Short.valueOf((short)var2));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
