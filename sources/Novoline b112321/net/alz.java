package net;

import net.aKa;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;

class alz implements PacketHandler {
   final aKa a;

   alz(aKa var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      Item var2 = (Item)var1.read(Type.FLAT_ITEM);
      boolean var3 = ((Boolean)var1.read(Type.BOOLEAN)).booleanValue();
      InventoryPackets.a(var2);
      var1.write(Type.STRING, "MC|BSign");
      var1.write(Type.ITEM, var2);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
