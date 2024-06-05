package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import net.a66;
import net.aC1;
import net.aC4;
import net.aC6;
import net.aCG;
import net.aCI;
import net.aCT;
import net.aCX;
import net.aCe;
import net.aCh;
import net.aCq;
import net.aqu;
import net.ayk;
import net.q1;
import net.r;

public class MetadataRewriter1_14To1_13_2 extends aqu {
   public MetadataRewriter1_14To1_13_2(ayk var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((ayk)this.c).b(a66.LOGIN, 4, -1, new aC1(this));
      ((ayk)this.c).a(q1.PLUGIN_MESSAGE, new aCq(this));
      ((ayk)this.c).a(q1.SPAWN_PARTICLE, new aCI(this));
      ((ayk)this.c).a(q1.PLAYER_INFO, new aC6(this));
      ((ayk)this.c).a(q1.SCOREBOARD_OBJECTIVE, new aCe(this));
      ((ayk)this.c).a(q1.TEAMS, new aCX(this));
      ((ayk)this.c).a(q1.TAB_COMPLETE, new aCG(this));
      ((ayk)this.c).a(r.TAB_COMPLETE, new aCh(this));
      ((ayk)this.c).a(r.PLUGIN_MESSAGE, new aCT(this));
      ((ayk)this.c).a(q1.STATISTICS, new aC4(this));
   }

   static BackwardsProtocol a(MetadataRewriter1_14To1_13_2 var0) {
      return var0.c;
   }

   static BackwardsProtocol b(MetadataRewriter1_14To1_13_2 var0) {
      return var0.c;
   }

   static BackwardsProtocol c(MetadataRewriter1_14To1_13_2 var0) {
      return var0.c;
   }
}
