package net;

import java.util.function.Function;
import net.Dl;
import net.Oz;
import net.aR5;
import net.ac0;
import net.acE;
import net.acR;
import net.acT;
import net.acY;
import net.acy;
import net.agN;
import net.alB;
import net.aqr;
import net.rX;
import net.yb;
import viaversion.viabackwards.api.entities.meta.MetaHandler;
import viaversion.viabackwards.api.entities.storage.EntityData;
import viaversion.viabackwards.api.entities.storage.EntityData$MetaCreator;
import viaversion.viabackwards.api.entities.storage.MetaStorage;
import viaversion.viabackwards.api.exceptions.RemovedValueException;
import viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.entities.ObjectType;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public class aqp extends aqr {
   private static acE[] m;

   public aqp(Protocol1_10To1_11 var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((Protocol1_10To1_11)this.c).a(agN.EFFECT, new acT(this));
      ((Protocol1_10To1_11)this.c).a(agN.SPAWN_ENTITY, new ac0(this));
      this.registerExtraTracker(agN.SPAWN_EXPERIENCE_ORB, Dl.EXPERIENCE_ORB);
      this.registerExtraTracker(agN.SPAWN_GLOBAL_ENTITY, Dl.WEATHER);
      ((Protocol1_10To1_11)this.c).a(agN.SPAWN_MOB, new acy(this));
      this.registerExtraTracker(agN.SPAWN_PAINTING, Dl.PAINTING);
      this.b(agN.JOIN_GAME, Dl.PLAYER);
      this.b(agN.RESPAWN);
      ((Protocol1_10To1_11)this.c).a(agN.SPAWN_PLAYER, new acR(this));
      this.registerEntityDestroy(agN.DESTROY_ENTITIES);
      this.a((ClientboundPacketType)agN.ENTITY_METADATA, (Type)rX.a);
      ((Protocol1_10To1_11)this.c).a(agN.ENTITY_STATUS, new acY(this));
   }

   protected void registerRewrites() {
      this.mapEntity(Dl.ELDER_GUARDIAN, Dl.GUARDIAN);
      this.mapEntity(Dl.WITHER_SKELETON, Dl.SKELETON).mobName("Wither Skeleton").spawnMetadata(this::lambda$registerRewrites$0);
      this.mapEntity(Dl.STRAY, Dl.SKELETON).mobName("Stray").spawnMetadata(this::lambda$registerRewrites$1);
      d();
      this.mapEntity(Dl.HUSK, Dl.ZOMBIE).mobName("Husk").spawnMetadata(this::lambda$registerRewrites$2);
      this.mapEntity(Dl.ZOMBIE_VILLAGER, Dl.ZOMBIE).spawnMetadata(this::lambda$registerRewrites$3);
      this.mapEntity(Dl.HORSE, Dl.HORSE).spawnMetadata(this::lambda$registerRewrites$4);
      this.mapEntity(Dl.DONKEY, Dl.HORSE).spawnMetadata(this::lambda$registerRewrites$5);
      this.mapEntity(Dl.MULE, Dl.HORSE).spawnMetadata(this::lambda$registerRewrites$6);
      this.mapEntity(Dl.SKELETON_HORSE, Dl.HORSE).spawnMetadata(this::lambda$registerRewrites$7);
      this.mapEntity(Dl.ZOMBIE_HORSE, Dl.HORSE).spawnMetadata(this::lambda$registerRewrites$8);
      this.mapEntity(Dl.EVOCATION_FANGS, Dl.SHULKER);
      this.mapEntity(Dl.EVOCATION_ILLAGER, Dl.VILLAGER).mobName("Evoker");
      this.mapEntity(Dl.VEX, Dl.BAT).mobName("Vex");
      this.mapEntity(Dl.VINDICATION_ILLAGER, Dl.VILLAGER).mobName("Vindicator").spawnMetadata(aqp::lambda$registerRewrites$9);
      this.mapEntity(Dl.LIAMA, Dl.HORSE).mobName("Llama").spawnMetadata(this::lambda$registerRewrites$10);
      this.mapEntity(Dl.LIAMA_SPIT, Dl.SNOWBALL);
      this.a((ObjectType)Oz.LIAMA_SPIT, (ObjectType)Oz.SNOWBALL, -1);
      this.a((ObjectType)Oz.EVOCATION_FANGS, (ObjectType)Oz.FALLING_BLOCK, 4294);
      this.registerMetaHandler().filter(Dl.GUARDIAN, true, 12).handle(aqp::lambda$registerRewrites$11);
      this.registerMetaHandler().filter(Dl.ABSTRACT_SKELETON, true, 12).handleIndexChange(13);
      this.registerMetaHandler().filter(Dl.ZOMBIE, true).handle(aqp::lambda$registerRewrites$12);
      this.registerMetaHandler().filter(Dl.EVOCATION_ILLAGER, 12).handle(aqp::lambda$registerRewrites$13);
      this.registerMetaHandler().filter(Dl.VEX, 12).handle(aqp::lambda$registerRewrites$14);
      this.registerMetaHandler().filter(Dl.VINDICATION_ILLAGER, 12).handle(aqp::lambda$registerRewrites$15);
      this.registerMetaHandler().filter(Dl.ABSTRACT_HORSE, true, 13).handle(aqp::lambda$registerRewrites$16);
      this.registerMetaHandler().filter(Dl.CHESTED_HORSE, true).handle(aqp::lambda$registerRewrites$17);
      this.registerMetaHandler().filter(Dl.HORSE, 16).handleIndexChange(17);
      this.registerMetaHandler().filter(Dl.CHESTED_HORSE, true, 15).handle(aqp::lambda$registerRewrites$18);
      this.registerMetaHandler().filter(Dl.LIAMA).handle(aqp::lambda$registerRewrites$19);
      this.registerMetaHandler().filter(Dl.ABSTRACT_HORSE, true, 14).handleIndexChange(16);
      this.registerMetaHandler().filter(Dl.VILLAGER, 13).handle(aqp::lambda$registerRewrites$20);
      this.registerMetaHandler().filter(Dl.SHULKER, 15).removed();
   }

   private Metadata c(int var1) {
      return new Metadata(12, aR5.VarInt, Integer.valueOf(var1));
   }

   private Metadata a(int var1) {
      return new Metadata(13, aR5.VarInt, Integer.valueOf(var1));
   }

   private void a(MetaStorage var1, int var2) {
      Metadata var3 = var1.get(13);
      var1.add(this.a(var2));
   }

   private Metadata b(int var1) {
      return new Metadata(14, aR5.VarInt, Integer.valueOf(var1));
   }

   protected EntityType getTypeFromId(int var1) {
      return alB.a(var1, false);
   }

   protected EntityType getObjectTypeFromId(int var1) {
      return alB.a(var1, true);
   }

   private static Metadata lambda$registerRewrites$20(yb var0) throws RemovedValueException {
      d();
      Metadata var2 = var0.i();
      if(((Integer)var2.getValue()).intValue() == 5) {
         var2.setValue(Integer.valueOf(0));
      }

      return var2;
   }

   private static Metadata lambda$registerRewrites$19(yb var0) throws RemovedValueException {
      d();
      Metadata var2 = var0.i();
      ChestedHorseStorage var3 = (ChestedHorseStorage)var0.h().get(ChestedHorseStorage.class);
      int var4 = var0.a();
      switch(var4) {
      case 16:
         var3.setLiamaStrength(((Integer)var2.getValue()).intValue());
         throw RemovedValueException.EX;
      case 17:
         var3.setLiamaCarpetColor(((Integer)var2.getValue()).intValue());
         throw RemovedValueException.EX;
      case 18:
         var3.setLiamaVariant(((Integer)var2.getValue()).intValue());
         throw RemovedValueException.EX;
      default:
         return var0.i();
      }
   }

   private static Metadata lambda$registerRewrites$18(yb var0) throws RemovedValueException {
      ChestedHorseStorage var1 = (ChestedHorseStorage)var0.h().get(ChestedHorseStorage.class);
      boolean var2 = ((Boolean)var0.i().getValue()).booleanValue();
      var1.setChested(var2);
      throw RemovedValueException.EX;
   }

   private static Metadata lambda$registerRewrites$17(yb var0) throws RemovedValueException {
      acE[] var1 = d();
      if(!var0.h().has(ChestedHorseStorage.class)) {
         var0.h().put(new ChestedHorseStorage());
      }

      return var0.i();
   }

   private static Metadata lambda$registerRewrites$16(yb var0) throws RemovedValueException {
      Metadata var2 = var0.i();
      d();
      byte var3 = ((Byte)var2.getValue()).byteValue();
      if(var0.h().has(ChestedHorseStorage.class) && ((ChestedHorseStorage)var0.h().get(ChestedHorseStorage.class)).isChested()) {
         var3 = (byte)(var3 | 8);
         var2.setValue(Byte.valueOf(var3));
      }

      return var2;
   }

   private static Metadata lambda$registerRewrites$15(yb var0) throws RemovedValueException {
      d();
      Metadata var2 = var0.i();
      var2.setId(13);
      var2.setMetaType(aR5.VarInt);
      var2.setValue(Integer.valueOf(((Number)var2.getValue()).intValue() == 1?2:4));
      return var2;
   }

   private static Metadata lambda$registerRewrites$14(yb var0) throws RemovedValueException {
      Metadata var1 = var0.i();
      var1.setValue(Byte.valueOf((byte)0));
      return var1;
   }

   private static Metadata lambda$registerRewrites$13(yb var0) throws RemovedValueException {
      Metadata var1 = var0.i();
      var1.setId(13);
      var1.setMetaType(aR5.VarInt);
      var1.setValue(Integer.valueOf(((Byte)var1.getValue()).intValue()));
      return var1;
   }

   private static Metadata lambda$registerRewrites$12(yb var0) throws RemovedValueException {
      d();
      Metadata var2 = var0.i();
      switch(var2.getId()) {
      case 13:
         throw RemovedValueException.EX;
      case 14:
         var2.setId(15);
      case 15:
         var2.setId(14);
      case 16:
         var2.setId(13);
         var2.setValue(Integer.valueOf(1 + ((Integer)var2.getValue()).intValue()));
      default:
         return var2;
      }
   }

   private static Metadata lambda$registerRewrites$11(yb var0) throws RemovedValueException {
      d();
      Metadata var2 = var0.i();
      boolean var3 = ((Boolean)var2.getValue()).booleanValue();
      int var4 = var3?2:0;
      if(var0.h().getType().is((EntityType)Dl.ELDER_GUARDIAN)) {
         var4 |= 4;
      }

      var2.setMetaType(aR5.Byte);
      var2.setValue(Byte.valueOf((byte)var4));
      return var2;
   }

   private void lambda$registerRewrites$10(MetaStorage var1) {
      var1.add(this.b(1));
   }

   private static void lambda$registerRewrites$9(MetaStorage var0) {
      var0.add(new Metadata(13, aR5.VarInt, Integer.valueOf(4)));
   }

   private void lambda$registerRewrites$8(MetaStorage var1) {
      var1.add(this.b(3));
   }

   private void lambda$registerRewrites$7(MetaStorage var1) {
      var1.add(this.b(4));
   }

   private void lambda$registerRewrites$6(MetaStorage var1) {
      var1.add(this.b(2));
   }

   private void lambda$registerRewrites$5(MetaStorage var1) {
      var1.add(this.b(1));
   }

   private void lambda$registerRewrites$4(MetaStorage var1) {
      var1.add(this.b(0));
   }

   private void lambda$registerRewrites$3(MetaStorage var1) {
      this.a(var1, 1);
   }

   private void lambda$registerRewrites$2(MetaStorage var1) {
      this.a(var1, 6);
   }

   private void lambda$registerRewrites$1(MetaStorage var1) {
      var1.add(this.c(2));
   }

   private void lambda$registerRewrites$0(MetaStorage var1) {
      var1.add(this.c(1));
   }

   static PacketHandler a(aqp var0) {
      return var0.c();
   }

   static PacketHandler a(aqp var0, Function var1) {
      return var0.a((Function)var1);
   }

   static PacketHandler a(aqp var0, Type var1, int var2) {
      return var0.getTrackerHandler(var1, var2);
   }

   static EntityType a(aqp var0, UserConnection var1, int var2) {
      return var0.getEntityType(var1, var2);
   }

   static MetaStorage a(aqp var0, UserConnection var1, int var2, MetaStorage var3) throws Exception {
      return var0.handleMeta(var1, var2, var3);
   }

   static EntityData a(aqp var0, EntityType var1) {
      return var0.getEntityData(var1);
   }

   static PacketHandler a(aqp var0, Type var1, EntityType var2) {
      return var0.a((Type)var1, (EntityType)var2);
   }

   public static void c(acE[] var0) {
      m = var0;
   }

   public static acE[] d() {
      return m;
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }

   static {
      c((acE[])null);
   }
}
