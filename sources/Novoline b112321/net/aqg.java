package net;

import net.a9L;
import viaversion.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aqg implements PacketHandler {
   final a9L a;

   aqg(a9L var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      var1.set(Type.ITEM, 0, ItemRewriter.toServer((Item)var1.get(Type.ITEM, 0)));
   }
}
