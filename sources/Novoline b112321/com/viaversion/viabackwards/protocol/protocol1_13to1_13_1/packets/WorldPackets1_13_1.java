package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import net.aoR;
import net.ayx;
import net.q1;
import net.y7;

public class WorldPackets1_13_1 {
   public static void a(ayx var0) {
      BlockRewriter var1 = new BlockRewriter(var0, Type.POSITION);
      var0.a((y7)q1.CHUNK_DATA, (PacketRemapper)(new aoR(var0)));
      var1.c(q1.BLOCK_ACTION);
      var1.d(q1.BLOCK_CHANGE);
      var1.e(q1.MULTI_BLOCK_CHANGE);
      var1.a(q1.EFFECT, 1010, 2001);
   }
}
