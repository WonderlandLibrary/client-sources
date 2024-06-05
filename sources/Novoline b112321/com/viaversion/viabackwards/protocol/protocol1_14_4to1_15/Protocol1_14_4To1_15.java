package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15$1;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import net.Mo;
import net.aAX;
import net.aR6;
import net.ahW;
import net.aq3;
import net.aqF;
import net.awj;
import net.bgR;
import net.cA;
import net.cQ;
import net.cW;
import net.g1;
import net.ku;
import net.y4;
import net.y7;

public class Protocol1_14_4To1_15 extends BackwardsProtocol {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.15", "1.14", aR6.class, true);
   private BlockItemPackets1_15 j;

   public Protocol1_14_4To1_15() {
      super(Mo.class, awj.class, ahW.class, ahW.class);
   }

   protected void registerPackets() {
      BackwardsMappings var10002 = MAPPINGS;
      MAPPINGS.getClass();
      this.executeAsyncAfterLoaded(aR6.class, var10002::h);
      ku var2 = new ku(this, "1.15");
      var2.b(Mo.BOSSBAR);
      var2.a(Mo.CHAT_MESSAGE);
      var2.c(Mo.COMBAT_EVENT);
      var2.g(Mo.DISCONNECT);
      var2.h(Mo.OPEN_WINDOW);
      var2.f(Mo.TAB_LIST);
      var2.d(Mo.TITLE);
      var2.a();
      (this.j = new BlockItemPackets1_15(this, var2)).f();
      (new aq3(this)).f();
      aAX var3 = new aAX(this);
      aqF.b();
      var3.a(Mo.SOUND);
      var3.a(Mo.ENTITY_SOUND);
      var3.b((y7)Mo.NAMED_SOUND);
      var3.c(Mo.STOP_SOUND);
      this.a(Mo.EXPLOSION, new Protocol1_14_4To1_15$1(this));
      (new g1(this, y4::a)).a((y7)Mo.TAGS);
      (new StatisticsRewriter(this, y4::a)).a((y7)Mo.STATISTICS);
   }

   public void a(bgR var1) {
      PacketRemapper[] var2 = aqF.b();
      if(!var1.a(cW.class)) {
         var1.a((cA)(new cW(var1)));
      }

      if(!var1.a(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      ((cQ)var1.b(cQ.class)).b(this);
      if(PacketRemapper.b() == null) {
         aqF.b((PacketRemapper[])(new PacketRemapper[3]));
      }

   }

   public BlockItemPackets1_15 getItemRewriter() {
      return this.j;
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }
}
