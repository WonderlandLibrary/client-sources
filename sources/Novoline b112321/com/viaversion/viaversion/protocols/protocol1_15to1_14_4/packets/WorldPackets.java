package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import net.aR6;
import net.aVK;
import net.aVW;
import net.awj;
import net.y7;

public class WorldPackets {
   public static void a(aR6 var0) {
      BlockRewriter var1 = new BlockRewriter(var0, Type.POSITION1_14);
      var1.c(awj.BLOCK_ACTION);
      var1.d(awj.BLOCK_CHANGE);
      var1.e(awj.MULTI_BLOCK_CHANGE);
      var1.b((y7)awj.ACKNOWLEDGE_PLAYER_DIGGING);
      var0.a(awj.CHUNK_DATA, new aVK(var0));
      var1.a(awj.EFFECT, 1010, 2001);
      var0.a(awj.SPAWN_PARTICLE, new aVW(var0));
   }
}
