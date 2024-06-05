package net;

import java.util.Arrays;
import java.util.Optional;
import net.acE;
import net.aqT;
import net.aqp;
import viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ac4 extends acE {
   final aqT c;

   ac4(aqT var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.ITEM_ARRAY);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aqp.d();
      Item[] var3 = (Item[])var1.get(Type.ITEM_ARRAY, 0);
      int var4 = 0;
      if(var4 < var3.length) {
         var3[var4] = this.c.a(var3[var4]);
         ++var4;
      }

      if(aqT.b(this.c, var1.user())) {
         Optional var9 = aqT.a(this.c, var1.user());
         if(!var9.isPresent()) {
            return;
         }

         ChestedHorseStorage var5 = (ChestedHorseStorage)var9.get();
         var3 = (Item[])Arrays.copyOf(var3, !var5.isChested()?38:53);
         int var6 = var3.length - 1;
         var3[aqT.a(this.c, var5, var6)] = var3[var6];
         var3[var6] = aqT.a(this.c, var5, var6, var3[var6]);
         --var6;
         var1.set(Type.ITEM_ARRAY, 0, var3);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
