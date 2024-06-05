package net;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
import com.viaversion.viabackwards.api.exceptions.RemovedValueException;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.EntityPositionStorage1_14;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import java.util.function.Supplier;
import net.CQ;
import net.EN;
import net.Gh;
import net.MB;
import net.N0;
import net.VV;
import net.Zh;
import net.aMz;
import net.aSG;
import net.a_4;
import net.ao3;
import net.ao8;
import net.aoB;
import net.aoF;
import net.aoS;
import net.aol;
import net.aom;
import net.aor;
import net.aos;
import net.aou;
import net.aov;
import net.aq6;
import net.aqr;
import net.awj;
import net.jh;
import net.vJ;
import net.y7;
import net.yb;
import net.z8;

public class aqb extends aqr {
   private EntityPositionHandler m;

   public aqb(Protocol1_13_2To1_14 var1) {
      super(var1, vJ.OptChat, vJ.Boolean);
   }

   protected void a(PacketWrapperImpl var1, int var2, ConnectionManager var3) throws Exception {
      aq6.a();
      super.a(var1, var2, var3);
      if(var3 == N0.PAINTING) {
         Position var5 = (Position)var1.b(Type.POSITION, 0);
         z8.a(this.m, var1, (double)var5.getX(), (double)var5.e(), (double)var5.getZ(), true, false);
      }

      if(var1.l() != 37) {
         this.m.a(var1, true, false);
      }

   }

   protected void registerPackets() {
      this.m = new EntityPositionHandler(this, EntityPositionStorage1_14.class, EntityPositionStorage1_14::<init>);
      ((Protocol1_13_2To1_14)this.c).a(awj.ENTITY_STATUS, new aol(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.ENTITY_TELEPORT, new aom(this));
      aou var1 = new aou(this);
      ((Protocol1_13_2To1_14)this.c).a(awj.ENTITY_POSITION, var1);
      ((Protocol1_13_2To1_14)this.c).a(awj.ENTITY_POSITION_AND_ROTATION, var1);
      ((Protocol1_13_2To1_14)this.c).a(awj.SPAWN_ENTITY, new aoS(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.SPAWN_MOB, new aoF(this));
      ((Protocol1_13_2To1_14)this.c()).a(awj.SPAWN_EXPERIENCE_ORB, new ao3(this));
      ((Protocol1_13_2To1_14)this.c()).a(awj.SPAWN_GLOBAL_ENTITY, new aos(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.SPAWN_PAINTING, new aov(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.SPAWN_PLAYER, new aor(this));
      this.a((y7)awj.DESTROY_ENTITIES);
      this.a((y7)awj.ENTITY_METADATA, (Type)aSG.c, (Type)CQ.c);
      ((Protocol1_13_2To1_14)this.c).a(awj.JOIN_GAME, new ao8(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.RESPAWN, new aoB(this));
   }

   protected void registerRewrites() {
      this.a((ConnectionManager[])N0.values(), (Class)a_4.class);
      this.a((ConnectionManager)N0.CAT, (ConnectionManager)N0.OCELOT).jsonName("Cat");
      this.a((ConnectionManager)N0.TRADER_LLAMA, (ConnectionManager)N0.LLAMA).jsonName("Trader Llama");
      this.a((ConnectionManager)N0.FOX, (ConnectionManager)N0.WOLF).jsonName("Fox");
      this.a((ConnectionManager)N0.PANDA, (ConnectionManager)N0.POLAR_BEAR).jsonName("Panda");
      this.a((ConnectionManager)N0.PILLAGER, (ConnectionManager)N0.VILLAGER).jsonName("Pillager");
      aq6.a();
      this.a((ConnectionManager)N0.WANDERING_TRADER, (ConnectionManager)N0.VILLAGER).jsonName("Wandering Trader");
      this.a((ConnectionManager)N0.RAVAGER, (ConnectionManager)N0.COW).jsonName("Ravager");
      this.c().a(this::lambda$registerRewrites$0);
      this.c().a(N0.PILLAGER, 15).d();
      this.c().a(N0.FOX, 15).d();
      this.c().a(N0.FOX, 16).d();
      this.c().a(N0.FOX, 17).d();
      this.c().a(N0.FOX, 18).d();
      this.c().a(N0.PANDA, 15).d();
      this.c().a(N0.PANDA, 16).d();
      this.c().a(N0.PANDA, 17).d();
      this.c().a(N0.PANDA, 18).d();
      this.c().a(N0.PANDA, 19).d();
      this.c().a(N0.PANDA, 20).d();
      this.c().a(N0.CAT, 18).d();
      this.c().a(N0.CAT, 19).d();
      this.c().a(N0.CAT, 20).d();
      this.c().a(aqb::lambda$registerRewrites$1);
      this.c().a(N0.AREA_EFFECT_CLOUD, 10).a(this::lambda$registerRewrites$2);
      this.c().a(N0.FIREWORK_ROCKET, 8).a(aqb::lambda$registerRewrites$3);
      this.c().a(N0.ABSTRACT_ARROW, true).a(aqb::lambda$registerRewrites$4);
      this.c().a(N0.VILLAGER, 15).d();
      Zh var2 = this::lambda$registerRewrites$5;
      this.c().a(N0.ZOMBIE_VILLAGER, 18).a(var2);
      this.c().a(N0.VILLAGER, 16).a(var2);
      this.c().a(N0.ABSTRACT_SKELETON, true, 13).a(aqb::lambda$registerRewrites$6);
      this.c().a(N0.ZOMBIE, true, 13).a(aqb::lambda$registerRewrites$7);
      this.c().a(N0.ZOMBIE, true).a(aqb::lambda$registerRewrites$8);
      this.c().a(N0.LIVINGENTITY, true).a(aqb::lambda$registerRewrites$9);
      this.c().a(aqb::lambda$registerRewrites$10);
      this.c().a(aqb::lambda$registerRewrites$11);
      this.c().a(N0.OCELOT, 13).a(aqb::lambda$registerRewrites$12);
      this.c().a((ConnectionManager)N0.CAT).a(aqb::lambda$registerRewrites$13);
   }

   public int a(MB var1) {
      PacketRemapper[] var2 = aq6.a();
      switch(var1.c()) {
      case 0:
      case 11:
      default:
         return 5;
      case 1:
      case 10:
      case 13:
      case 14:
         return 3;
      case 2:
      case 8:
         return 4;
      case 3:
      case 9:
         return 1;
      case 4:
         return 2;
      case 5:
      case 6:
      case 7:
      case 12:
         return 0;
      }
   }

   protected ConnectionManager b(int var1) {
      return jh.a(var1);
   }

   private static Metadata lambda$registerRewrites$13(yb var0) throws RemovedValueException {
      aq6.a();
      Metadata var2 = var0.i();
      if(var2.id() == 15) {
         var2.a(Integer.valueOf(1));
      }

      if(var2.id() == 13) {
         var2.a(Byte.valueOf((byte)(((Byte)var2.getValue()).byteValue() & 4)));
      }

      return var2;
   }

   private static Metadata lambda$registerRewrites$12(yb var0) throws RemovedValueException {
      Metadata var1 = var0.i();
      var1.setId(15);
      var1.setMetaTypeUnsafe(vJ.VarInt);
      var1.a(Integer.valueOf(0));
      return var1;
   }

   private static Metadata lambda$registerRewrites$11(yb var0) throws RemovedValueException {
      aq6.a();
      Metadata var2 = var0.i();
      int var3 = var2.metaType().typeId();
      if(var3 > 15) {
         VV.d().a().warning("New 1.14 metadata was not handled: " + var2 + " entity: " + var0.h().b());
         return null;
      } else {
         return var2;
      }
   }

   private static Metadata lambda$registerRewrites$10(yb var0) throws RemovedValueException {
      Metadata var2 = var0.i();
      aq6.a();
      int var3 = var0.a();
      if(var3 == 6) {
         throw RemovedValueException.EX;
      } else {
         if(var3 > 6) {
            var2.setId(var3 - 1);
         }

         return var2;
      }
   }

   private static Metadata lambda$registerRewrites$9(yb var0) throws RemovedValueException {
      aq6.a();
      Metadata var2 = var0.i();
      int var3 = var0.a();
      if(var3 == 12) {
         Position var4 = (Position)var2.getValue();
         PacketWrapperImpl var5 = new PacketWrapperImpl(51, (ByteBuf)null, var0.f());
         var5.a(Type.VAR_INT, Integer.valueOf(var0.h().a()));
         var5.a(Type.POSITION, var4);
         PacketWrapperImpl var10000 = var5;
         Class var10001 = Protocol1_13_2To1_14.class;

         try {
            var10000.a(var10001);
         } catch (Exception var7) {
            var7.printStackTrace();
         }

         throw RemovedValueException.EX;
      } else {
         if(var3 > 12) {
            var2.setId(var3 - 1);
         }

         return var2;
      }
   }

   private static Metadata lambda$registerRewrites$8(yb var0) throws RemovedValueException {
      Metadata var1 = var0.i();
      int var2 = var0.a();
      if(var2 >= 16) {
         var1.setId(var2 + 1);
      }

      return var1;
   }

   private static Metadata lambda$registerRewrites$7(yb var0) throws RemovedValueException {
      byte var1 = ((Byte)var0.i().getValue()).byteValue();
      if((var1 & 4) != 0) {
         var0.a(new Metadata(16, vJ.Boolean, Boolean.valueOf(true)));
      }

      return var0.i();
   }

   private static Metadata lambda$registerRewrites$6(yb var0) throws RemovedValueException {
      byte var1 = ((Byte)var0.i().getValue()).byteValue();
      if((var1 & 4) != 0) {
         var0.a(new Metadata(14, vJ.Boolean, Boolean.valueOf(true)));
      }

      return var0.i();
   }

   private Metadata lambda$registerRewrites$5(yb var1) throws RemovedValueException {
      aq6.a();
      Metadata var3 = var1.i();
      MB var4 = (MB)var3.getValue();
      var3.a(Integer.valueOf(this.a(var4)));
      var3.setMetaTypeUnsafe(vJ.VarInt);
      if(var3.id() == 16) {
         var3.setId(15);
      }

      return var3;
   }

   private static Metadata lambda$registerRewrites$4(yb var0) throws RemovedValueException {
      Metadata var2 = var0.i();
      aq6.a();
      int var3 = var0.a();
      if(var3 == 9) {
         throw RemovedValueException.EX;
      } else {
         if(var3 > 9) {
            var2.setId(var3 - 1);
         }

         return var2;
      }
   }

   private static Metadata lambda$registerRewrites$3(yb var0) throws RemovedValueException {
      Metadata var1 = var0.i();
      var1.setMetaTypeUnsafe(vJ.VarInt);
      Integer var2 = (Integer)var1.getValue();
      var1.a(Integer.valueOf(0));
      return var1;
   }

   private Metadata lambda$registerRewrites$2(yb var1) throws RemovedValueException {
      Metadata var2 = var1.i();
      this.a((Gh)((Gh)var2.getValue()));
      return var2;
   }

   private static Metadata lambda$registerRewrites$1(yb var0) throws RemovedValueException {
      aq6.a();
      ConnectionManager var2 = var0.h().b();
      Metadata var3 = var0.i();
      if(var2.a((ConnectionManager)N0.ABSTRACT_ILLAGER_BASE) || var2 == N0.RAVAGER || var2 == N0.WITCH) {
         int var4 = var0.a();
         if(var4 == 14) {
            throw RemovedValueException.EX;
         }

         if(var4 > 14) {
            var3.setId(var4 - 1);
         }
      }

      return var3;
   }

   private Metadata lambda$registerRewrites$0(yb var1) throws RemovedValueException {
      aq6.a();
      Metadata var3 = var1.i();
      int var4 = var3.metaType().typeId();
      if(var4 <= 15) {
         var3.setMetaTypeUnsafe(vJ.a(var4));
      }

      MetaType var5 = var3.metaType();
      if(var5 == vJ.Slot) {
         aMz var6 = (aMz)var3.getValue();
         var3.a(((Protocol1_13_2To1_14)this.c()).b().a((aMz)var6));
      }

      if(var5 == vJ.BlockID) {
         int var7 = ((Integer)var3.getValue()).intValue();
         var3.a(Integer.valueOf(((Protocol1_13_2To1_14)this.c).getMappingData().c(var7)));
      }

      return var3;
   }

   static EntityPositionHandler a(aqb var0) {
      return var0.m;
   }

   static EN c(aqb var0) {
      return var0.c();
   }

   static BackwardsProtocol b(aqb var0) {
      return var0.c;
   }

   static Int2IntMap d(aqb var0) {
      return var0.i;
   }

   static EntityData a(aqb var0, ConnectionManager var1) {
      return var0.a((ConnectionManager)var1);
   }

   static EN a(aqb var0, Type var1) {
      return var0.a((Type)var1);
   }

   static EN a(aqb var0, Type var1, ConnectionManager var2) {
      return var0.a((Type)var1, (ConnectionManager)var2);
   }

   static EN a(aqb var0, ConnectionManager var1, Type var2) {
      return var0.a((ConnectionManager)var1, (Type)var2);
   }

   static EN a(aqb var0, int var1) {
      return var0.a(var1);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
