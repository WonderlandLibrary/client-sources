package net;

import net.S3;
import net.a9p;
import viaversion.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aaS implements PacketHandler {
   final a9p a;

   aaS(a9p var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      var1.set(Type.ITEM, 0, ItemRewriter.toClient((Item)var1.get(Type.ITEM, 0)));
      S3.b();
      byte var3 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      short var4 = ((Short)var1.get(Type.SHORT, 0)).shortValue();
      if(var3 == 0 && var4 == 45) {
         var1.cancel();
      } else {
         String var5 = ((Windows)var1.user().b(Windows.class)).get((short)var3);
         if(var5 != null) {
            if(var5.equalsIgnoreCase("minecraft:brewing_stand")) {
               if(var4 > 4) {
                  --var4;
                  var1.set(Type.SHORT, 0, Short.valueOf(var4));
               }

               if(var4 == 4) {
                  var1.cancel();
                  Windows.updateBrewingStand(var1.user(), (Item)var1.get(Type.ITEM, 0), (short)var3);
                  return;
               }

               ((Windows)var1.user().b(Windows.class)).getBrewingItems((short)var3)[var4] = (Item)var1.get(Type.ITEM, 0);
            }

         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
