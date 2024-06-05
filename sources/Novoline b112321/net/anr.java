package net;

import net.acE;
import net.aqG;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class anr extends acE {
   final aqG c;

   anr(aqG var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      this.c.c((Item)var1.passthrough(Type.FLAT_VAR_INT_ITEM));
   }
}
