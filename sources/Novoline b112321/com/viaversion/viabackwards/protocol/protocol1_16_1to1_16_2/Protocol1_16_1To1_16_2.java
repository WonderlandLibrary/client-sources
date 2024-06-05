package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import net.C4;
import net.UN;
import net.aAX;
import net.aYz;
import net.anS;
import net.anu;
import net.aqG;
import net.aqv;
import net.bgR;
import net.cA;
import net.cQ;
import net.g1;
import net.ku;
import net.lx;
import net.sM;
import net.y7;

public class Protocol1_16_1To1_16_2 extends BackwardsProtocol {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.16.2", "1.16", Protocol1_16_2To1_16_1.class, true);
   private aqG k;
   private ku l;

   public Protocol1_16_1To1_16_2() {
      super(UN.class, C4.class, lx.class, sM.class);
   }

   protected void registerPackets() {
      BackwardsMappings var10002 = MAPPINGS;
      MAPPINGS.getClass();
      this.executeAsyncAfterLoaded(Protocol1_16_2To1_16_1.class, var10002::h);
      this.l = new ku(this, "1.16.2");
      this.l.b(UN.BOSSBAR);
      this.l.c(UN.COMBAT_EVENT);
      this.l.g(UN.DISCONNECT);
      this.l.f(UN.TAB_LIST);
      this.l.d(UN.TITLE);
      this.l.h(UN.OPEN_WINDOW);
      aYz.b();
      this.l.a();
      (this.k = new aqG(this, this.l)).f();
      aqv var2 = new aqv(this);
      var2.f();
      aAX var3 = new aAX(this);
      var3.a(UN.SOUND);
      var3.a(UN.ENTITY_SOUND);
      var3.b((y7)UN.NAMED_SOUND);
      var3.c(UN.STOP_SOUND);
      this.a(UN.CHAT_MESSAGE, new anu(this));
      this.a(sM.RECIPE_BOOK_DATA, new anS(this));
      var2.getClass();
      (new g1(this, var2::newEntityId)).a((y7)UN.TAGS);
      var2.getClass();
      (new StatisticsRewriter(this, var2::newEntityId)).a((y7)UN.STATISTICS);
   }

   public void a(bgR var1) {
      String[] var2 = aYz.b();
      if(!var1.a(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      ((cQ)var1.b(cQ.class)).b(this);
   }

   public aqG a() {
      return this.k;
   }

   public ku b() {
      return this.l;
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   static ku a(Protocol1_16_1To1_16_2 var0) {
      return var0.l;
   }
}
