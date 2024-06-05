package net;

import net.aME;
import net.aXe;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;

class A9 implements PacketHandler {
   final aME a;

   A9(aME var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.b();
      String var3 = (String)var1.get(Type.STRING, 0);
      if(var3.equalsIgnoreCase("MC|BSign")) {
         Item var4 = (Item)var1.passthrough(Type.ITEM);
         if(var4 != null) {
            var4.setIdentifier(387);
            ItemRewriter.b(var4);
         }
      }

      if(var3.equalsIgnoreCase("MC|AutoCmd")) {
         var1.set(Type.STRING, 0, "MC|AdvCdm");
         var1.write(Type.BYTE, Byte.valueOf((byte)0));
         var1.passthrough(Type.INT);
         var1.passthrough(Type.INT);
         var1.passthrough(Type.INT);
         var1.passthrough(Type.STRING);
         var1.passthrough(Type.BOOLEAN);
         var1.clearInputBuffer();
      }

      if(var3.equalsIgnoreCase("MC|AdvCmd")) {
         var1.set(Type.STRING, 0, "MC|AdvCdm");
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
