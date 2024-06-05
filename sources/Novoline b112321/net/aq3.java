package net;

import net.Gh;
import net.Mo;
import net.aSG;
import net.acE;
import net.agc;
import net.anA;
import net.ao_;
import net.aoj;
import net.aqF;
import net.g4;
import net.n3;
import net.yb;
import viaversion.viabackwards.api.entities.meta.MetaHandler;
import viaversion.viabackwards.api.entities.storage.EntityData$MetaCreator;
import viaversion.viabackwards.api.entities.storage.MetaStorage;
import viaversion.viabackwards.api.exceptions.RemovedValueException;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.EntityTypeMapping;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15$3;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15$4;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15$5;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15$6;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.minecraft.metadata.MetaType;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public class aq3 extends aqF {
   private static String l;

   public aq3(Protocol1_14_4To1_15 var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((Protocol1_14_4To1_15)this.c).a(Mo.UPDATE_HEALTH, new ao_(this));
      ((Protocol1_14_4To1_15)this.c).a(Mo.GAME_EVENT, new aoj(this));
      this.b(Mo.SPAWN_ENTITY, g4.FALLING_BLOCK);
      a();
      ((Protocol1_14_4To1_15)this.c).a(Mo.SPAWN_MOB, new EntityPackets1_15$3(this));
      ((Protocol1_14_4To1_15)this.c).a(Mo.RESPAWN, new EntityPackets1_15$4(this));
      ((Protocol1_14_4To1_15)this.c).a(Mo.JOIN_GAME, new EntityPackets1_15$5(this));
      this.registerExtraTracker(Mo.SPAWN_EXPERIENCE_ORB, g4.EXPERIENCE_ORB);
      this.registerExtraTracker(Mo.SPAWN_GLOBAL_ENTITY, g4.LIGHTNING_BOLT);
      this.registerExtraTracker(Mo.SPAWN_PAINTING, g4.PAINTING);
      ((Protocol1_14_4To1_15)this.c).a(Mo.SPAWN_PLAYER, new EntityPackets1_15$6(this));
      this.registerEntityDestroy(Mo.DESTROY_ENTITIES);
      this.a(Mo.ENTITY_METADATA, aSG.c);
      ((Protocol1_14_4To1_15)this.c).a(Mo.ENTITY_PROPERTIES, new anA(this));
      if(acE.b() == null) {
         b("kM7AZb");
      }

   }

   protected void registerRewrites() {
      this.registerMetaHandler().handle(this::lambda$registerRewrites$0);
      this.registerMetaHandler().filter(g4.LIVINGENTITY, true).handle(aq3::lambda$registerRewrites$1);
      this.registerMetaHandler().filter(g4.BEE, 15).removed();
      this.registerMetaHandler().filter(g4.BEE, 16).removed();
      this.mapEntity(g4.BEE, g4.PUFFERFISH).jsonName("Bee").spawnMetadata(aq3::lambda$registerRewrites$2);
      this.registerMetaHandler().filter(g4.ENDERMAN, 16).removed();
      this.registerMetaHandler().filter(g4.TRIDENT, 10).removed();
      this.registerMetaHandler().filter(g4.WOLF).handle(aq3::lambda$registerRewrites$3);
   }

   protected EntityType getTypeFromId(int var1) {
      return agc.a(var1);
   }

   public int getOldEntityId(int var1) {
      return EntityTypeMapping.getOldEntityId(var1);
   }

   private static Metadata lambda$registerRewrites$3(yb var0) throws RemovedValueException {
      int var1 = var0.a();
      if(var1 >= 17) {
         var0.i().setId(var1 + 1);
      }

      return var0.i();
   }

   private static void lambda$registerRewrites$2(MetaStorage var0) {
      var0.add(new Metadata(14, n3.Boolean, Boolean.valueOf(false)));
      var0.add(new Metadata(15, n3.VarInt, Integer.valueOf(2)));
   }

   private static Metadata lambda$registerRewrites$1(yb var0) throws RemovedValueException {
      a();
      int var2 = var0.a();
      if(var2 == 12) {
         throw RemovedValueException.EX;
      } else {
         if(var2 > 12) {
            var0.i().setId(var2 - 1);
         }

         return var0.i();
      }
   }

   private Metadata lambda$registerRewrites$0(yb var1) throws RemovedValueException {
      a();
      Metadata var3 = var1.i();
      MetaType var4 = var3.getMetaType();
      if(var4 == n3.Slot) {
         Item var5 = (Item)var3.getValue();
         var3.setValue(((Protocol1_14_4To1_15)this.c).getBlockItemPackets().a(var5));
      }

      if(var4 == n3.BlockID) {
         int var6 = ((Integer)var3.getValue()).intValue();
         var3.setValue(Integer.valueOf(((Protocol1_14_4To1_15)this.c).getMappingData().getNewBlockStateId(var6)));
      }

      if(var4 == n3.PARTICLE) {
         this.a((Gh)var3.getValue());
      }

      return var3;
   }

   static void a(aq3 var0, PacketWrapper var1, int var2, EntityType var3) throws Exception {
      var0.addTrackedEntity(var1, var2, var3);
   }

   static PacketHandler b(aq3 var0, EntityType var1, Type var2) {
      return var0.getTrackerHandler(var1, var2);
   }

   static PacketHandler a(aq3 var0, EntityType var1, Type var2) {
      return var0.getTrackerHandler(var1, var2);
   }

   static EntityType a(aq3 var0, UserConnection var1, int var2) {
      return var0.getEntityType(var1, var2);
   }

   public static void b(String var0) {
      l = var0;
   }

   public static String a() {
      return l;
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }

   static {
      if(a() != null) {
         b("xJuUpc");
      }

   }
}
