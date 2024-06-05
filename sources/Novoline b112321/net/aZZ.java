package net;

import net.aM7;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;

class aZZ implements PacketHandler {
   final aM7 a;

   aZZ(aM7 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      Item var2 = (Item)var1.get(Type.ITEM, 0);
      ItemRewriter.toServer(var2);
   }
}
