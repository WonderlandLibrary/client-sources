package net;

import java.util.function.Function;
import net.aR5;
import net.aZ9;
import net.agN;
import net.anG;
import net.anX;
import net.ane;
import net.aqr;
import net.rX;
import net.t4;
import net.yb;
import viaversion.viabackwards.api.entities.meta.MetaHandler;
import viaversion.viabackwards.api.entities.storage.EntityData;
import viaversion.viabackwards.api.entities.storage.MetaStorage;
import viaversion.viabackwards.api.exceptions.RemovedValueException;
import viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public class aq1 extends aqr {
   private static String[] m;

   public aq1(Protocol1_9_4To1_10 var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((Protocol1_9_4To1_10)this.c).a(agN.SPAWN_ENTITY, new ane(this));
      this.registerExtraTracker(agN.SPAWN_EXPERIENCE_ORB, t4.EXPERIENCE_ORB);
      this.registerExtraTracker(agN.SPAWN_GLOBAL_ENTITY, t4.WEATHER);
      ((Protocol1_9_4To1_10)this.c).a(agN.SPAWN_MOB, new anX(this));
      this.registerExtraTracker(agN.SPAWN_PAINTING, t4.PAINTING);
      this.b(agN.JOIN_GAME, t4.PLAYER);
      this.b(agN.RESPAWN);
      ((Protocol1_9_4To1_10)this.c).a(agN.SPAWN_PLAYER, new anG(this));
      this.registerEntityDestroy(agN.DESTROY_ENTITIES);
      this.a((ClientboundPacketType)agN.ENTITY_METADATA, (Type)rX.a);
   }

   protected void registerRewrites() {
      this.mapEntity(t4.POLAR_BEAR, t4.SHEEP).mobName("Polar Bear");
      this.registerMetaHandler().filter(t4.POLAR_BEAR, 13).handle(aq1::lambda$registerRewrites$0);
      this.registerMetaHandler().filter(t4.ZOMBIE, 13).handle(aq1::lambda$registerRewrites$1);
      this.registerMetaHandler().filter(t4.SKELETON, 12).handle(aq1::lambda$registerRewrites$2);
      this.registerMetaHandler().handle(aq1::lambda$registerRewrites$3);
   }

   protected EntityType getTypeFromId(int var1) {
      return aZ9.a(var1, false);
   }

   protected EntityType getObjectTypeFromId(int var1) {
      return aZ9.a(var1, true);
   }

   private static Metadata lambda$registerRewrites$3(yb var0) throws RemovedValueException {
      a();
      Metadata var2 = var0.i();
      if(var2.getId() == 5) {
         throw RemovedValueException.EX;
      } else {
         if(var2.getId() >= 5) {
            var2.setId(var2.getId() - 1);
         }

         return var2;
      }
   }

   private static Metadata lambda$registerRewrites$2(yb var0) throws RemovedValueException {
      a();
      Metadata var2 = var0.i();
      if(((Integer)var2.getValue()).intValue() == 2) {
         var2.setValue(Integer.valueOf(0));
      }

      return var2;
   }

   private static Metadata lambda$registerRewrites$1(yb var0) throws RemovedValueException {
      a();
      Metadata var2 = var0.i();
      if(((Integer)var2.getValue()).intValue() == 6) {
         var2.setValue(Integer.valueOf(0));
      }

      return var2;
   }

   private static Metadata lambda$registerRewrites$0(yb var0) throws RemovedValueException {
      Metadata var2 = var0.i();
      a();
      boolean var3 = ((Boolean)var2.getValue()).booleanValue();
      var2.setMetaType(aR5.Byte);
      var2.setValue(Byte.valueOf((byte)(var3?14:0)));
      return var2;
   }

   static PacketHandler a(aq1 var0) {
      return var0.c();
   }

   static PacketHandler a(aq1 var0, Function var1) {
      return var0.a((Function)var1);
   }

   static PacketHandler a(aq1 var0, Type var1, int var2) {
      return var0.getTrackerHandler(var1, var2);
   }

   static EntityType a(aq1 var0, UserConnection var1, int var2) {
      return var0.getEntityType(var1, var2);
   }

   static MetaStorage a(aq1 var0, UserConnection var1, int var2, MetaStorage var3) throws Exception {
      return var0.handleMeta(var1, var2, var3);
   }

   static EntityData a(aq1 var0, EntityType var1) {
      return var0.getEntityData(var1);
   }

   static PacketHandler a(aq1 var0, Type var1, EntityType var2) {
      return var0.a((Type)var1, (EntityType)var2);
   }

   public static void b(String[] var0) {
      m = var0;
   }

   public static String[] a() {
      return m;
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }

   static {
      if(a() != null) {
         b(new String[5]);
      }

   }
}
