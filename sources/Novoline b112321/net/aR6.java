package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.WorldPackets;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import net.Ch;
import net.Mo;
import net.Q3;
import net.aTZ;
import net.aVo;
import net.ahW;
import net.ai4;
import net.awA;
import net.awj;
import net.ayx;
import net.bgR;
import net.cA;
import net.cU;
import net.g1;
import net.vs;
import net.y7;

public class aR6 extends ayx {
   public static final ai4 j = new ai4();
   private g1 i;

   public aR6() {
      super(awj.class, Mo.class, ahW.class, ahW.class);
   }

   protected void registerPackets() {
      aTZ var2 = new aTZ(this);
      awA.a(this);
      vs.a(this);
      WorldPackets.a(this);
      Q3.a((aR6)this);
      Mo.b();
      SoundRewriter var3 = new SoundRewriter(this);
      var3.a((y7)awj.ENTITY_SOUND);
      var3.a((y7)awj.SOUND);
      var2.getClass();
      (new StatisticsRewriter(this, var2::b)).a((y7)awj.STATISTICS);
      this.a(ahW.EDIT_BOOK, new aVo(this));
      this.i = new g1(this, awA::a);
      this.i.a((y7)awj.TAGS);
   }

   protected void onMappingDataLoaded() {
      Mo.b();
      int[] var2 = new int[17];
      short var3 = 501;
      int var4 = 0;
      if(var4 < 17) {
         var2[var4] = var3 + var4;
         ++var4;
      }

      this.i.a(Ch.BLOCK, "minecraft:shulker_boxes", var2);
      if(PacketRemapper.b() == null) {
         Mo.b(new String[3]);
      }

   }

   public void a(bgR var1) {
      var1.a((cA)(new cU(var1)));
   }

   public ai4 a() {
      return j;
   }
}
