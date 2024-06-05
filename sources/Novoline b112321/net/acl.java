package net;

import net.acE;
import net.aqF;
import net.aqu;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class acl extends acE {
   final EntityType c;
   final aqF d;

   acl(aqF var1, EntityType var2) {
      this.d = var1;
      this.c = var2;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(EntityType var1, PacketWrapper var2) throws Exception {
      aqu.d();
      EntityType var4 = aqF.a(this.d, var2);
      if(var4 == var1) {
         int var5 = ((Integer)var2.get(Type.INT, 0)).intValue();
         var2.set(Type.INT, 0, Integer.valueOf(this.d.c.getMappingData().getNewBlockStateId(var5)));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
