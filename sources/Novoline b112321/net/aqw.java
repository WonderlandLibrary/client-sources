package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import java.util.function.Function;
import net.aCA;
import net.aCQ;
import net.aCc;
import net.aCo;
import net.aFf;
import net.ahY;
import net.aqr;
import net.ay_;
import net.lV;
import net.yb;
import viaversion.viabackwards.api.entities.meta.MetaHandler;
import viaversion.viabackwards.api.entities.storage.EntityData$MetaCreator;
import viaversion.viabackwards.api.entities.storage.MetaStorage;
import viaversion.viabackwards.api.exceptions.RemovedValueException;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ParrotStorage;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12$4;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.version.Types1_12;

public class aqw extends aqr {
   private static String m;

   public aqw(ay_ var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((ay_)this.c).a(lV.SPAWN_ENTITY, new aCA(this));
      this.registerExtraTracker(lV.SPAWN_EXPERIENCE_ORB, ahY.EXPERIENCE_ORB);
      this.registerExtraTracker(lV.SPAWN_GLOBAL_ENTITY, ahY.WEATHER);
      ((ay_)this.c).a(lV.SPAWN_MOB, new aCQ(this));
      this.registerExtraTracker(lV.SPAWN_PAINTING, ahY.PAINTING);
      ((ay_)this.c).a(lV.SPAWN_PLAYER, new aCc(this));
      ((ay_)this.c).a(lV.JOIN_GAME, new EntityPackets1_12$4(this));
      this.b((ClientboundPacketType)lV.RESPAWN);
      this.registerEntityDestroy(lV.DESTROY_ENTITIES);
      this.a((ClientboundPacketType)lV.ENTITY_METADATA, (Type)Types1_12.METADATA_LIST);
      ((ay_)this.c).a(lV.ENTITY_PROPERTIES, new aCo(this));
   }

   protected void registerRewrites() {
      this.mapEntity(ahY.PARROT, ahY.BAT).mobName("Parrot").spawnMetadata(aqw::lambda$registerRewrites$0);
      this.mapEntity(ahY.ILLUSION_ILLAGER, ahY.EVOCATION_ILLAGER).mobName("Illusioner");
      this.registerMetaHandler().filter(ahY.EVOCATION_ILLAGER, true, 12).removed();
      this.registerMetaHandler().filter(ahY.EVOCATION_ILLAGER, true, 13).handleIndexChange(12);
      this.registerMetaHandler().filter(ahY.ILLUSION_ILLAGER, 0).handle(aqw::lambda$registerRewrites$1);
      this.registerMetaHandler().filter(ahY.PARROT, true).handle(aqw::lambda$registerRewrites$2);
      this.registerMetaHandler().filter(ahY.PARROT, 12).removed();
      this.registerMetaHandler().filter(ahY.PARROT, 13).handle(aqw::lambda$registerRewrites$3);
      this.registerMetaHandler().filter(ahY.PARROT, 14).removed();
      this.registerMetaHandler().filter(ahY.PARROT, 15).removed();
      this.registerMetaHandler().filter(ahY.PLAYER, 15).handle(aqw::lambda$registerRewrites$4);
      this.registerMetaHandler().filter(ahY.PLAYER, 16).handle(aqw::lambda$registerRewrites$5);
   }

   protected EntityType getTypeFromId(int var1) {
      return aFf.a(var1, false);
   }

   protected EntityType getObjectTypeFromId(int var1) {
      return aFf.a(var1, true);
   }

   private static Metadata lambda$registerRewrites$5(yb var0) throws RemovedValueException {
      CompoundTag var2 = (CompoundTag)var0.i().getValue();
      a();
      ShoulderTracker var3 = (ShoulderTracker)var0.f().b(ShoulderTracker.class);
      if(var2.isEmpty() && var3.h() != null) {
         var3.a((String)null);
         var3.update();
      }

      if(var2.contains("id") && var0.h().getEntityId() == var3.getEntityId()) {
         String var4 = (String)var2.get("id").getValue();
         if(var3.h() == null || !var3.h().equals(var4)) {
            var3.a(var4);
            var3.update();
         }
      }

      throw RemovedValueException.EX;
   }

   private static Metadata lambda$registerRewrites$4(yb var0) throws RemovedValueException {
      a();
      CompoundTag var2 = (CompoundTag)var0.i().getValue();
      ShoulderTracker var3 = (ShoulderTracker)var0.f().b(ShoulderTracker.class);
      if(var2.isEmpty() && var3.g() != null) {
         var3.c((String)null);
         var3.update();
      }

      if(var2.contains("id") && var0.h().getEntityId() == var3.getEntityId()) {
         String var4 = (String)var2.get("id").getValue();
         if(var3.g() == null || !var3.g().equals(var4)) {
            var3.c(var4);
            var3.update();
         }
      }

      throw RemovedValueException.EX;
   }

   private static Metadata lambda$registerRewrites$3(yb var0) throws RemovedValueException {
      a();
      Metadata var2 = var0.i();
      ParrotStorage var3 = (ParrotStorage)var0.h().get(ParrotStorage.class);
      boolean var4 = (((Byte)var2.getValue()).byteValue() & 1) == 1;
      boolean var5 = (((Byte)var2.getValue()).byteValue() & 4) == 4;
      if(!var3.isTamed()) {
         ;
      }

      var3.setTamed(var5);
      if(var4) {
         var2.setId(12);
         var2.setValue(Byte.valueOf((byte)1));
         var3.setSitting(true);
      }

      if(var3.isSitting()) {
         var2.setId(12);
         var2.setValue(Byte.valueOf((byte)0));
         var3.setSitting(false);
      }

      throw RemovedValueException.EX;
   }

   private static Metadata lambda$registerRewrites$2(yb var0) throws RemovedValueException {
      String var1 = a();
      if(!var0.h().has(ParrotStorage.class)) {
         var0.h().put(new ParrotStorage());
      }

      return var0.i();
   }

   private static Metadata lambda$registerRewrites$1(yb var0) throws RemovedValueException {
      a();
      byte var2 = ((Byte)var0.i().getValue()).byteValue();
      if((var2 & 32) == 32) {
         var2 &= -33;
      }

      var0.i().setValue(Byte.valueOf(var2));
      return var0.i();
   }

   private static void lambda$registerRewrites$0(MetaStorage var0) {
      var0.add(new Metadata(12, MetaType1_12.Byte, Byte.valueOf((byte)0)));
   }

   static PacketHandler b(aqw var0) {
      return var0.c();
   }

   static PacketHandler a(aqw var0, Function var1) {
      return var0.a((Function)var1);
   }

   static PacketHandler a(aqw var0) {
      return var0.getTrackerHandler();
   }

   static PacketHandler a(aqw var0, Type var1) {
      return var0.a((Type)var1);
   }

   static PacketHandler a(aqw var0, Type var1, EntityType var2) {
      return var0.a((Type)var1, (EntityType)var2);
   }

   static PacketHandler a(aqw var0, EntityType var1, Type var2) {
      return var0.getTrackerHandler(var1, var2);
   }

   static PacketHandler a(aqw var0, int var1) {
      return var0.getDimensionHandler(var1);
   }

   public static void b(String var0) {
      m = var0;
   }

   public static String a() {
      return m;
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }

   static {
      b("CPhDSc");
   }
}
