package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.SoundPackets1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import net.aR4;
import net.aSG;
import net.ahW;
import net.aoA;
import net.aoJ;
import net.aq6;
import net.aqb;
import net.awj;
import net.bgR;
import net.cA;
import net.cK;
import net.cQ;
import net.cT;
import net.ku;
import net.q1;
import net.uN;
import net.y7;

public class Protocol1_13_2To1_14 extends BackwardsProtocol {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.14", "1.13.2", aR4.class, true);
   private aq6 j;
   private aqb k;

   public Protocol1_13_2To1_14() {
      super(awj.class, q1.class, ahW.class, uN.class);
   }

   protected void registerPackets() {
      BackwardsMappings var10002 = MAPPINGS;
      MAPPINGS.getClass();
      this.executeAsyncAfterLoaded(aR4.class, var10002::h);
      ku var2 = new ku(this, "1.14");
      var2.b(awj.BOSSBAR);
      var2.a(awj.CHAT_MESSAGE);
      var2.c(awj.COMBAT_EVENT);
      var2.g(awj.DISCONNECT);
      var2.f(awj.TAB_LIST);
      var2.d(awj.TITLE);
      aSG.b();
      var2.a();
      this.j = new aq6(this, var2);
      this.j.f();
      this.k = new aqb(this);
      this.k.f();
      (new PlayerPackets1_14(this)).f();
      (new SoundPackets1_14(this)).f();
      aqb var10004 = this.k;
      this.k.getClass();
      (new StatisticsRewriter(this, var10004::newEntityId)).a((y7)awj.STATISTICS);
      this.a(awj.UPDATE_VIEW_POSITION);
      this.a(awj.UPDATE_VIEW_DISTANCE);
      this.a(awj.ACKNOWLEDGE_PLAYER_DIGGING);
      this.a(awj.TAGS, new aoJ(this));
      this.a(awj.UPDATE_LIGHT, (y7)null, new aoA(this));
   }

   public void a(bgR var1) {
      PacketRemapper[] var2 = aSG.b();
      if(!var1.a(cT.class)) {
         var1.a((cA)(new cT(var1)));
      }

      if(!var1.a(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      ((cQ)var1.b(cQ.class)).b(this);
      if(!var1.a(cK.class)) {
         var1.a((cA)(new cK(var1)));
      }

   }

   public aq6 b() {
      return this.j;
   }

   public aqb a() {
      return this.k;
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }
}
