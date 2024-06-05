package net;

import java.util.Optional;
import net.aQU;
import net.acE;
import net.aqT;
import net.aqp;
import viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class acN extends acE {
   final aQU c;
   final aqT d;

   acN(aqT var1, aQU var2) {
      this.d = var1;
      this.c = var2;
   }

   public void registerMap() {
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      this.a(Type.ITEM);
      this.a(this.c.a(Type.ITEM));
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      acE[] var2 = aqp.d();
      if(aqT.b(this.d, var1.user())) {
         Optional var3 = aqT.a(this.d, var1.user());
         if(!var3.isPresent()) {
            return;
         }

         ChestedHorseStorage var4 = (ChestedHorseStorage)var3.get();
         int var5 = ((Short)var1.get(Type.SHORT, 0)).shortValue();
         var1.set(Type.SHORT, 0, Short.valueOf(Integer.valueOf(var5 = aqT.a(this.d, var4, var5)).shortValue()));
         var1.set(Type.ITEM, 0, aqT.a(this.d, var4, var5, (Item)var1.get(Type.ITEM, 0)));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
