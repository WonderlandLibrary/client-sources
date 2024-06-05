package net;

import com.google.gson.JsonElement;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.exceptions.RemovedValueException;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import net.C4;
import net.Gh;
import net.YO;
import net.Zh;
import net.aMz;
import net.aNT;
import net.aR7;
import net.aSG;
import net.an8;
import net.anB;
import net.anl;
import net.anm;
import net.aqF;
import net.aqX;
import net.axs;
import net.g4;
import net.n3;
import net.y7;
import net.yb;

public class aqs extends aqF {
   private final ValueTransformer l = new YO(this, Type.STRING, Type.I);

   public aqs(aR7 var1) {
      super(var1);
   }

   protected void registerPackets() {
      this.b(C4.SPAWN_ENTITY, axs.FALLING_BLOCK);
      this.b(C4.SPAWN_MOB);
      ((aR7)this.c).a(C4.RESPAWN, new anl(this));
      ((aR7)this.c).a(C4.JOIN_GAME, new an8(this));
      this.a(C4.SPAWN_EXPERIENCE_ORB, axs.EXPERIENCE_ORB);
      this.a(C4.SPAWN_PAINTING, axs.PAINTING);
      this.a(C4.SPAWN_PLAYER, axs.PLAYER);
      this.a((y7)C4.DESTROY_ENTITIES);
      this.a(C4.ENTITY_METADATA, aSG.c);
      ((aR7)this.c).a(C4.ENTITY_PROPERTIES, new anB(this));
      ((aR7)this.c).a(C4.PLAYER_INFO, new anm(this));
   }

   protected void registerRewrites() {
      this.c().a(this::lambda$registerRewrites$0);
      this.b(axs.ZOMBIFIED_PIGLIN, g4.ZOMBIE_PIGMAN);
      this.a(axs.values(), g4.class);
      this.a(axs.HOGLIN, axs.COW).jsonName("Hoglin");
      this.a(axs.ZOGLIN, axs.COW).jsonName("Zoglin");
      this.a(axs.PIGLIN, axs.ZOMBIFIED_PIGLIN).jsonName("Piglin");
      this.a(axs.STRIDER, axs.MAGMA_CUBE).jsonName("Strider");
      aqX.a();
      this.c().a(axs.ZOGLIN, 16).d();
      this.c().a(axs.HOGLIN, 15).d();
      this.c().a(axs.PIGLIN, 16).d();
      this.c().a(axs.PIGLIN, 17).d();
      this.c().a(axs.PIGLIN, 18).d();
      this.c().a(axs.STRIDER, 15).a(aqs::lambda$registerRewrites$1);
      this.c().a(axs.STRIDER, 16).d();
      this.c().a(axs.STRIDER, 17).d();
      this.c().a(axs.STRIDER, 18).d();
      this.c().a(axs.FISHING_BOBBER, 8).d();
      this.c().a(axs.ABSTRACT_ARROW, true, 8).d();
      this.c().a(axs.ABSTRACT_ARROW, true).a(aqs::lambda$registerRewrites$2);
      if(PacketRemapper.b() == null) {
         aqX.b(new String[1]);
      }

   }

   protected ConnectionManager b(int var1) {
      return aNT.a(var1);
   }

   private static Metadata lambda$registerRewrites$2(yb var0) throws RemovedValueException {
      String[] var1 = aqX.a();
      if(var0.a() >= 8) {
         var0.i().setId(var0.a() + 1);
      }

      return var0.i();
   }

   private static Metadata lambda$registerRewrites$1(yb var0) throws RemovedValueException {
      aqX.a();
      boolean var2 = ((Boolean)var0.i().value()).booleanValue();
      var0.i().a(Integer.valueOf(var2?1:3));
      var0.i().setMetaTypeUnsafe(n3.VarInt);
      return var0.i();
   }

   private Metadata lambda$registerRewrites$0(yb var1) throws RemovedValueException {
      aqX.a();
      Metadata var3 = var1.i();
      MetaType var4 = var3.metaType();
      if(var4 == n3.Slot) {
         var3.a(((aR7)this.c).c().a((aMz)((aMz)var3.getValue())));
      }

      if(var4 == n3.BlockID) {
         var3.a(Integer.valueOf(((aR7)this.c).a().c(((Integer)var3.getValue()).intValue())));
      }

      if(var4 == n3.PARTICLE) {
         this.a((Gh)((Gh)var3.getValue()));
      }

      if(var4 == n3.OptChat) {
         JsonElement var5 = (JsonElement)var3.value();
         ((aR7)this.c).b().a(var5);
      }

      return var3;
   }

   static ValueTransformer c(aqs var0) {
      return var0.l;
   }

   static BackwardsProtocol a(aqs var0) {
      return var0.c;
   }

   static BackwardsProtocol b(aqs var0) {
      return var0.c;
   }

   static BackwardsProtocol d(aqs var0) {
      return var0.c;
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }
}
