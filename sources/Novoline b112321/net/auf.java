package net;

import java.util.Iterator;
import java.util.List;
import net.a0G;
import net.aCO;
import net.aEY;
import net.aTx;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.minecraft.metadata.types.MetaType1_13_2;
import viaversion.viaversion.api.remapper.PacketHandler;

class auf implements PacketHandler {
   final aCO a;

   auf(aCO var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      a0G.a();
      Iterator var3 = ((List)var1.get(aEY.a, 0)).iterator();
      if(var3.hasNext()) {
         Metadata var4 = (Metadata)var3.next();
         if(var4.getMetaType() == MetaType1_13_2.Slot) {
            var4.setMetaType(aTx.Slot);
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
