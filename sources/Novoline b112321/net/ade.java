package net;

import net.S3;
import net.a9r;
import viaversion.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ade implements PacketHandler {
   final a9r a;

   ade(a9r var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      short var3 = ((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue();
      S3.b();
      Item[] var4 = (Item[])var1.read(Type.ITEM_ARRAY);
      int var5 = 0;
      if(var5 < var4.length) {
         var4[var5] = ItemRewriter.toClient(var4[var5]);
         ++var5;
      }

      if(var3 == 0 && var4.length == 46) {
         Item[] var9 = var4;
         var4 = new Item[45];
         System.arraycopy(var9, 0, var4, 0, 45);
      } else {
         String var8 = ((Windows)var1.user().b(Windows.class)).get(var3);
         if(var8.equalsIgnoreCase("minecraft:brewing_stand")) {
            System.arraycopy(var4, 0, ((Windows)var1.user().b(Windows.class)).getBrewingItems(var3), 0, 4);
            Windows.updateBrewingStand(var1.user(), var4[4], var3);
            Item[] var6 = var4;
            var4 = new Item[var4.length - 1];
            System.arraycopy(var6, 0, var4, 0, 4);
            System.arraycopy(var6, 5, var4, 4, var6.length - 5);
         }
      }

      var1.write(Type.ITEM_ARRAY, var4);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
