package net;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.BackwardsMappings;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import net.C4;
import net.Mo;
import net.a0V;
import net.a66;
import net.aAX;
import net.ahW;
import net.an7;
import net.an9;
import net.anC;
import net.anD;
import net.anK;
import net.ann;
import net.ano;
import net.aqX;
import net.aqs;
import net.bgR;
import net.cA;
import net.cM;
import net.cQ;
import net.cT;
import net.cV;
import net.g1;
import net.h0;
import net.kF;
import net.ku;
import net.sM;
import net.y7;

public class aR7 extends BackwardsProtocol {
   public static final BackwardsMappings l = new BackwardsMappings();
   private aqX j;
   private ku k;

   public aR7() {
      super(C4.class, Mo.class, sM.class, ahW.class);
   }

   protected void registerPackets() {
      BackwardsMappings var10002 = l;
      l.getClass();
      this.executeAsyncAfterLoaded(Protocol1_16To1_15_2.class, var10002::h);
      this.k = new kF(this, "1.16");
      this.k.b(C4.BOSSBAR);
      this.k.c(C4.COMBAT_EVENT);
      this.k.g(C4.DISCONNECT);
      this.k.f(C4.TAB_LIST);
      this.k.d(C4.TITLE);
      a0V.b();
      this.k.a();
      (this.j = new aqX(this, this.k)).f();
      aqs var2 = new aqs(this);
      var2.f();
      this.b(a66.STATUS, 0, 0, new anC(this));
      this.a(C4.CHAT_MESSAGE, new ano(this));
      this.a(C4.OPEN_WINDOW, new ann(this));
      aAX var3 = new aAX(this);
      var3.a(C4.SOUND);
      var3.a(C4.ENTITY_SOUND);
      var3.b((y7)C4.NAMED_SOUND);
      var3.c(C4.STOP_SOUND);
      this.b(a66.LOGIN, 2, 2, new anD(this));
      var2.getClass();
      (new g1(this, var2::newEntityId)).a((y7)C4.TAGS);
      var2.getClass();
      (new StatisticsRewriter(this, var2::newEntityId)).a((y7)C4.STATISTICS);
      this.a(ahW.ENTITY_ACTION, new an9(this));
      this.a(ahW.INTERACT_ENTITY, new an7(this));
      this.a(ahW.PLAYER_ABILITIES, new anK(this));
      this.a((h0)ahW.UPDATE_JIGSAW_BLOCK);
   }

   public void a(bgR var1) {
      PacketRemapper[] var2 = a0V.b();
      if(!var1.a(cT.class)) {
         var1.a((cA)(new cT(var1)));
      }

      if(!var1.a(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      var1.a((cA)(new cM(var1)));
      var1.a((cA)(new cV(var1)));
      ((cQ)var1.b(cQ.class)).b(this);
      if(PacketRemapper.b() == null) {
         a0V.b((PacketRemapper[])(new PacketRemapper[1]));
      }

   }

   public aqX c() {
      return this.j;
   }

   public ku b() {
      return this.k;
   }

   public BackwardsMappings a() {
      return l;
   }

   static ku a(aR7 var0) {
      return var0.k;
   }
}
