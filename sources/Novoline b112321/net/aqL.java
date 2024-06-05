package net;

import java.util.function.Function;
import net.Dl;
import net.aC7;
import net.aC9;
import net.aCK;
import net.acE;
import net.agN;
import net.alB;
import net.aqr;
import net.ayj;
import net.rX;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public class aqL extends aqr {
   private static String m;

   public aqL(ayj var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((ayj)this.c).a(agN.SPAWN_ENTITY, new aC9(this));
      this.registerExtraTracker(agN.SPAWN_EXPERIENCE_ORB, Dl.EXPERIENCE_ORB);
      this.registerExtraTracker(agN.SPAWN_GLOBAL_ENTITY, Dl.WEATHER);
      ((ayj)this.c).a(agN.SPAWN_MOB, new aC7(this));
      this.registerExtraTracker(agN.SPAWN_PAINTING, Dl.PAINTING);
      this.b(agN.JOIN_GAME, Dl.PLAYER);
      a();
      this.b((ClientboundPacketType)agN.RESPAWN);
      ((ayj)this.c).a(agN.SPAWN_PLAYER, new aCK(this));
      this.registerEntityDestroy(agN.DESTROY_ENTITIES);
      this.a((ClientboundPacketType)agN.ENTITY_METADATA, (Type)rX.a);
      if(acE.b() == null) {
         b("vcKUm");
      }

   }

   protected void registerRewrites() {
      this.registerMetaHandler().filter(Dl.FIREWORK, 7).removed();
      this.registerMetaHandler().filter(Dl.PIG, 14).removed();
   }

   protected EntityType getTypeFromId(int var1) {
      return alB.a(var1, false);
   }

   protected EntityType getObjectTypeFromId(int var1) {
      return alB.a(var1, true);
   }

   static PacketHandler a(aqL var0) {
      return var0.c();
   }

   static PacketHandler a(aqL var0, Function var1) {
      return var0.a(var1);
   }

   static PacketHandler b(aqL var0) {
      return var0.getTrackerHandler();
   }

   static PacketHandler a(aqL var0, Type var1) {
      return var0.a(var1);
   }

   static PacketHandler a(aqL var0, Type var1, EntityType var2) {
      return var0.a((Type)var1, (EntityType)var2);
   }

   public static void b(String var0) {
      m = var0;
   }

   public static String a() {
      return m;
   }

   static {
      if(a() == null) {
         b("swkOSc");
      }

   }
}
