package net;

import net.HR;
import net.aKu;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets;

class qi implements PacketHandler {
   final aKu a;

   qi(aKu var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      HR.b();
      String var3 = (String)var1.get(Type.STRING, 0);
      if(var3.equals("minecraft:trader_list") || var3.equals("trader_list")) {
         var1.passthrough(Type.INT);
         short var4 = ((Short)var1.passthrough(Type.UNSIGNED_BYTE)).shortValue();
         int var5 = 0;
         if(var5 < var4) {
            InventoryPackets.toClient((Item)var1.passthrough(Type.FLAT_ITEM));
            InventoryPackets.toClient((Item)var1.passthrough(Type.FLAT_ITEM));
            boolean var6 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
            InventoryPackets.toClient((Item)var1.passthrough(Type.FLAT_ITEM));
            var1.passthrough(Type.BOOLEAN);
            var1.passthrough(Type.INT);
            var1.passthrough(Type.INT);
            ++var5;
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
