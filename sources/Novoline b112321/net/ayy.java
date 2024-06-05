package net;

import net.aKZ;
import net.ahp;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_12to1_11_1.BedRewriter;

class ayy implements PacketHandler {
   final aKZ a;

   ayy(aKZ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      boolean var2 = ahp.a();
      if(((String)var1.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
         var1.passthrough(Type.INT);
         short var3 = ((Short)var1.passthrough(Type.UNSIGNED_BYTE)).shortValue();
         int var4 = 0;
         if(var4 < var3) {
            BedRewriter.toClientItem((Item)var1.passthrough(Type.ITEM));
            BedRewriter.toClientItem((Item)var1.passthrough(Type.ITEM));
            boolean var5 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
            BedRewriter.toClientItem((Item)var1.passthrough(Type.ITEM));
            var1.passthrough(Type.BOOLEAN);
            var1.passthrough(Type.INT);
            var1.passthrough(Type.INT);
            ++var4;
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
