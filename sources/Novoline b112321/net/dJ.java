package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import net.C4;
import net.aRX;
import net.aSG;
import net.aTH;
import net.acE;
import net.awD;
import net.lH;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.EntityPackets$1;
import viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.EntityPackets$2;

public class dJ {
   public static void a(aRX var0) {
      aTH var2 = (aTH)var0.get(aTH.class);
      var2.registerSpawnTrackerWithData(C4.SPAWN_ENTITY, awD.FALLING_BLOCK);
      lH.b();
      var2.registerTracker(C4.SPAWN_MOB);
      var2.registerTracker(C4.SPAWN_PLAYER, awD.PLAYER);
      var2.registerMetadataRewriter(C4.ENTITY_METADATA, aSG.c);
      var2.registerEntityDestroy(C4.DESTROY_ENTITIES);
      var0.a(C4.JOIN_GAME, new EntityPackets$1(var0));
      var0.a(C4.RESPAWN, new EntityPackets$2());
      if(acE.b() == null) {
         lH.b(new acE[1]);
      }

   }

   public static CompoundTag a(String var0) {
      lH.b();
      CompoundTag var2 = (CompoundTag)aRX.j.getDimensionDataMap().get(var0);
      if(var2 == null) {
         Via.getPlatform().getLogger().severe("Could not get dimension data of " + var0);
         throw new NullPointerException("Dimension data for " + var0 + " is null!");
      } else {
         return var2;
      }
   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
