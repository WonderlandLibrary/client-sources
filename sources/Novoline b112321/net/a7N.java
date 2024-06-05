package net;

import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.storage.EntityTracker;
import viaversion.viaversion.api.type.Type;

class a7N extends acE {
   final EntityType d;
   final MetadataRewriter c;

   a7N(MetadataRewriter var1, EntityType var2) {
      this.c = var1;
      this.d = var2;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      MetadataRewriter.e();
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.INT);
      this.a(this.c.getTracker());
      this.a(this::lambda$registerMap$0);
      if(acE.b() == null) {
         MetadataRewriter.b(false);
      }

   }

   private void lambda$registerMap$0(EntityType var1, PacketWrapper var2) throws Exception {
      int var3 = ((Integer)var2.get(Type.VAR_INT, 0)).intValue();
      EntityType var4 = ((EntityTracker)var2.user().b(MetadataRewriter.access$000(this.c))).getEntity(var3);
      if(var4 == var1) {
         var2.set(Type.INT, 0, Integer.valueOf(this.c.protocol.getMappingData().getNewBlockStateId(((Integer)var2.get(Type.INT, 0)).intValue())));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
