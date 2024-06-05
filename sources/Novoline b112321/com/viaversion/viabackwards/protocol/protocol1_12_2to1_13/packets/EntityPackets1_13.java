package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData$MetaCreator;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viabackwards.api.exceptions.RemovedValueException;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$ParticleData;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13$4;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13$6;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13$8;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import net.EN;
import net.Gh;
import net.RL;
import net.VV;
import net.YD;
import net.Zh;
import net.aCN;
import net.aCP;
import net.aCp;
import net.aCr;
import net.aCw;
import net.aEY;
import net.aMz;
import net.a_4;
import net.aci;
import net.aqI;
import net.aqr;
import net.arX;
import net.arq;
import net.ayk;
import net.q1;
import net.r;
import net.y7;
import net.yb;

public class EntityPackets1_13 extends aqr {
   public EntityPackets1_13(ayk var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((ayk)this.c).a(q1.PLAYER_POSITION, new aCr(this));
      ((ayk)this.c).a(q1.SPAWN_ENTITY, new aCp(this));
      this.a((y7)q1.SPAWN_EXPERIENCE_ORB, (ConnectionManager)a_4.EXPERIENCE_ORB);
      this.a((y7)q1.SPAWN_GLOBAL_ENTITY, (ConnectionManager)a_4.LIGHTNING_BOLT);
      ((ayk)this.c).a(q1.SPAWN_MOB, new aCw(this));
      ((ayk)this.c).a(q1.SPAWN_PLAYER, new EntityPackets1_13$4(this));
      ((ayk)this.c).a(q1.SPAWN_PAINTING, new aCP(this));
      this.b(q1.JOIN_GAME, a_4.PLAYER);
      ((ayk)this.c).a(q1.RESPAWN, new EntityPackets1_13$6(this));
      this.a((y7)q1.DESTROY_ENTITIES);
      this.a((y7)q1.ENTITY_METADATA, (Type)aEY.a, (Type)YD.b);
      ((ayk)this.c).a(q1.FACE_PLAYER, (y7)null, new aCN(this));
      if(VV.c().isFix1_13FacePlayer()) {
         EntityPackets1_13$8 var1 = new EntityPackets1_13$8(this);
         ((ayk)this.c).a(r.PLAYER_POSITION, var1);
         ((ayk)this.c).a(r.PLAYER_POSITION_AND_ROTATION, var1);
         ((ayk)this.c).a(r.VEHICLE_MOVE, var1);
      }

   }

   protected void registerRewrites() {
      this.a((ConnectionManager)a_4.DROWNED, (ConnectionManager)a_4.ZOMBIE_VILLAGER).mobName("Drowned");
      this.a((ConnectionManager)a_4.COD, (ConnectionManager)a_4.SQUID).mobName("Cod");
      this.a((ConnectionManager)a_4.SALMON, (ConnectionManager)a_4.SQUID).mobName("Salmon");
      this.a((ConnectionManager)a_4.PUFFERFISH, (ConnectionManager)a_4.SQUID).mobName("Puffer Fish");
      this.a((ConnectionManager)a_4.TROPICAL_FISH, (ConnectionManager)a_4.SQUID).mobName("Tropical Fish");
      this.a((ConnectionManager)a_4.PHANTOM, (ConnectionManager)a_4.PARROT).mobName("Phantom").spawnMetadata(EntityPackets1_13::lambda$registerRewrites$0);
      aqI.a();
      this.a((ConnectionManager)a_4.DOLPHIN, (ConnectionManager)a_4.SQUID).mobName("Dolphin");
      this.a((ConnectionManager)a_4.TURTLE, (ConnectionManager)a_4.OCELOT).mobName("Turtle");
      this.c().a(this::lambda$registerRewrites$1);
      this.c().a(a_4.ENTITY, true, 2).a(EntityPackets1_13::lambda$registerRewrites$2);
      this.c().a(a_4.ZOMBIE, true, 15).d();
      this.c().a(a_4.ZOMBIE, true).a(EntityPackets1_13::lambda$registerRewrites$3);
      this.c().a(a_4.TURTLE, 13).d();
      this.c().a(a_4.TURTLE, 14).d();
      this.c().a(a_4.TURTLE, 15).d();
      this.c().a(a_4.TURTLE, 16).d();
      this.c().a(a_4.TURTLE, 17).d();
      this.c().a(a_4.TURTLE, 18).d();
      this.c().a(a_4.ABSTRACT_FISHES, true, 12).d();
      this.c().a(a_4.ABSTRACT_FISHES, true, 13).d();
      this.c().a(a_4.PHANTOM, 12).d();
      this.c().a(a_4.BOAT, 12).d();
      this.c().a(a_4.TRIDENT, 7).d();
      this.c().a(a_4.WOLF, 17).a(EntityPackets1_13::lambda$registerRewrites$4);
      this.c().a(a_4.AREA_EFFECT_CLOUD, 9).a(this::lambda$registerRewrites$5);
   }

   protected ConnectionManager b(int var1) {
      return RL.a(var1, false);
   }

   protected ConnectionManager a(int var1) {
      return RL.a(var1, true);
   }

   public int newEntityId(int var1) {
      return arX.a(var1);
   }

   private Metadata lambda$registerRewrites$5(yb var1) throws RemovedValueException {
      aqI.a();
      Metadata var3 = var1.i();
      Gh var4 = (Gh)var3.getValue();
      ParticleMapping$ParticleData var5 = aci.a(var4.c());
      int var6 = 0;
      int var7 = 0;
      int[] var8 = var5.a((ayk)this.c, var4.d());
      if(var8.length != 0) {
         if(var5.getHandler().isBlockHandler() && var8[0] == 0) {
            var8[0] = 102;
         }

         var6 = var8[0];
         var7 = var8.length == 2?var8[1]:0;
      }

      var1.a(new Metadata(9, arq.VarInt, Integer.valueOf(var5.getHistoryId())));
      var1.a(new Metadata(10, arq.VarInt, Integer.valueOf(var6)));
      var1.a(new Metadata(11, arq.VarInt, Integer.valueOf(var7)));
      throw RemovedValueException.EX;
   }

   private static Metadata lambda$registerRewrites$4(yb var0) throws RemovedValueException {
      Metadata var1 = var0.i();
      var1.a(Integer.valueOf(15 - ((Integer)var1.getValue()).intValue()));
      return var1;
   }

   private static Metadata lambda$registerRewrites$3(yb var0) throws RemovedValueException {
      aqI.a();
      Metadata var2 = var0.i();
      if(var2.id() > 15) {
         var2.setId(var2.id() - 1);
      }

      return var2;
   }

   private static Metadata lambda$registerRewrites$2(yb var0) throws RemovedValueException {
      Metadata var2 = var0.i();
      aqI.a();
      String var3 = var2.getValue().toString();
      if(var3.isEmpty()) {
         return var2;
      } else {
         var2.a(ChatRewriter.c(var3));
         return var2;
      }
   }

   private Metadata lambda$registerRewrites$1(yb var1) throws RemovedValueException {
      aqI.a();
      Metadata var3 = var1.i();
      int var4 = var3.metaType().typeId();
      if(var4 == 5) {
         var3.setMetaTypeUnsafe(arq.String);
         if(var3.getValue() != null) {
            return var3;
         }

         var3.a("");
      }

      if(var4 == 6) {
         var3.setMetaTypeUnsafe(arq.Slot);
         aMz var5 = (aMz)var3.getValue();
         var3.a(((ayk)this.c).a().a((aMz)var5));
      }

      if(var4 == 15) {
         var3.setMetaTypeUnsafe(arq.Discontinued);
      }

      if(var4 > 5) {
         var3.setMetaTypeUnsafe(arq.a(var4 - 1));
      }

      return var3;
   }

   private static void lambda$registerRewrites$0(WrappedMetadata var0) {
      var0.add(new Metadata(15, arq.VarInt, Integer.valueOf(3)));
   }

   static EN a(EntityPackets1_13 var0) {
      return var0.c();
   }

   static void a(EntityPackets1_13 var0, PacketWrapperImpl var1, int var2, ConnectionManager var3) throws Exception {
      var0.a(var1, var2, var3);
   }

   static boolean a(EntityPackets1_13 var0, ConnectionManager var1) {
      return var0.b(var1);
   }

   static EN a(EntityPackets1_13 var0, Type var1) {
      return var0.a((Type)var1);
   }

   static EN a(EntityPackets1_13 var0, Type var1, ConnectionManager var2) {
      return var0.a((Type)var1, (ConnectionManager)var2);
   }

   static EN a(EntityPackets1_13 var0, ConnectionManager var1, Type var2) {
      return var0.a((ConnectionManager)var1, (Type)var2);
   }

   static EN a(EntityPackets1_13 var0, int var1) {
      return var0.a(var1);
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }
}
