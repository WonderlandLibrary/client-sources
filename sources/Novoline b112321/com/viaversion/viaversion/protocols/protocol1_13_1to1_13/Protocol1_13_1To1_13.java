package com.viaversion.viaversion.protocols.protocol1_13_1to1_13;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.EntityPackets;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import net.HR;
import net.aKE;
import net.aKf;
import net.aKi;
import net.aKl;
import net.aTK;
import net.aiL;
import net.ayx;
import net.bgR;
import net.cA;
import net.cP;
import net.cT;
import net.g1;
import net.q1;
import net.uN;
import net.xN;
import net.y7;

public class Protocol1_13_1To1_13 extends ayx {
   public static final aiL j = new aiL("1.13", "1.13.2", true);
   private static PacketRemapper[] i;

   public Protocol1_13_1To1_13() {
      super(q1.class, q1.class, uN.class, uN.class);
   }

   protected void registerPackets() {
      new aTK(this);
      a();
      EntityPackets.register(this);
      xN.a(this);
      HR.a(this);
      this.a(uN.TAB_COMPLETE, new aKf(this));
      this.a(uN.EDIT_BOOK, new aKi(this));
      this.a(q1.TAB_COMPLETE, new aKE(this));
      this.a(q1.BOSSBAR, new aKl(this));
      (new g1(this, (IdRewriteFunction)null)).a((y7)q1.TAGS);
      (new StatisticsRewriter(this, (IdRewriteFunction)null)).a((y7)q1.STATISTICS);
   }

   public void a(bgR var1) {
      a();
      var1.a((cA)(new cP(var1)));
      if(!var1.a(cT.class)) {
         var1.a((cA)(new cT(var1)));
      }

   }

   public aiL d() {
      return j;
   }

   static {
      a(new PacketRemapper[4]);
   }

   public static void a(PacketRemapper[] var0) {
      i = var0;
   }

   public static PacketRemapper[] a() {
      return i;
   }
}
