package net;

import net.aMw;
import net.aXe;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;

class iS implements PacketHandler {
   final aMw a;

   iS(aMw var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.c();
      String var3 = (String)var1.get(Type.STRING, 0);
      if(var3.equalsIgnoreCase("MC|BOpen")) {
         var1.read(Type.REMAINING_BYTES);
         var1.write(Type.VAR_INT, Integer.valueOf(0));
      }

      if(var3.equalsIgnoreCase("MC|TrList")) {
         var1.passthrough(Type.INT);
         Short var4 = (Short)var1.passthrough(Type.UNSIGNED_BYTE);
         int var5 = 0;
         if(var5 < var4.shortValue()) {
            Item var6 = (Item)var1.passthrough(Type.ITEM);
            ItemRewriter.toClient(var6);
            Item var7 = (Item)var1.passthrough(Type.ITEM);
            ItemRewriter.toClient(var7);
            boolean var8 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
            Item var9 = (Item)var1.passthrough(Type.ITEM);
            ItemRewriter.toClient(var9);
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
