package net;

import java.util.List;
import net.aSG;
import net.anc;
import net.aqU;
import net.jh;
import viaversion.viabackwards.api.entities.storage.MetaStorage;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class zI implements PacketHandler {
   final anc a;

   zI(anc var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 1)).intValue();
      aqU.a(this.a.c, (PacketWrapper)var1, var2, (EntityType)jh.a(var3));
      MetaStorage var4 = new MetaStorage((List)var1.get(aSG.c, 0));
      aqU.a(this.a.c, var1.user(), var2, var4);
   }
}
