package net;

import net.aV5;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;

class E4 implements PacketHandler {
   final aV5 a;

   E4(aV5 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      Item var2 = (Item)var1.get(Type.ITEM, 0);
      ItemRewriter.toClient(var2);
   }
}
