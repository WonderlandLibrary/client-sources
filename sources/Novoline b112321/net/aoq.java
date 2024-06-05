package net;

import net.VV;
import net.acE;
import net.aq6;
import net.cQ;
import viaversion.viabackwards.api.entities.storage.EntityTracker$StoredEntity;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.SoundPackets1_14;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.EntityPositionStorage1_14;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aoq extends acE {
   final SoundPackets1_14 c;

   aoq(SoundPackets1_14 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aq6.a();
      var1.cancel();
      int var3 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      int var4 = ((Protocol1_13_2To1_14)SoundPackets1_14.a(this.c)).getMappingData().getSoundMappings().getNewId(var3);
      if(var4 != -1) {
         int var5 = ((Integer)var1.read(Type.VAR_INT)).intValue();
         int var6 = ((Integer)var1.read(Type.VAR_INT)).intValue();
         EntityTracker$StoredEntity var7 = ((cQ)var1.user().b(cQ.class)).a(SoundPackets1_14.b(this.c)).getEntity(var6);
         EntityPositionStorage1_14 var8;
         if((var8 = (EntityPositionStorage1_14)var7.get(EntityPositionStorage1_14.class)) == null) {
            VV.d().getLogger().warning("Untracked entity with id " + var6);
         } else {
            float var9 = ((Float)var1.read(Type.FLOAT)).floatValue();
            float var10 = ((Float)var1.read(Type.FLOAT)).floatValue();
            int var11 = (int)(var8.a() * 8.0D);
            int var12 = (int)(var8.c() * 8.0D);
            int var13 = (int)(var8.b() * 8.0D);
            PacketWrapper var14 = var1.create(77);
            var14.write(Type.VAR_INT, Integer.valueOf(var4));
            var14.write(Type.VAR_INT, Integer.valueOf(var5));
            var14.write(Type.INT, Integer.valueOf(var11));
            var14.write(Type.INT, Integer.valueOf(var12));
            var14.write(Type.INT, Integer.valueOf(var13));
            var14.write(Type.FLOAT, Float.valueOf(var9));
            var14.write(Type.FLOAT, Float.valueOf(var10));
            var14.send(Protocol1_13_2To1_14.class);
         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
