package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import net.EN;

class BlockRewriter$2 extends PacketRemapper {
   final BlockRewriter this$0;

   BlockRewriter$2(BlockRewriter var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(BlockRewriter.access$000(this.this$0));
      this.a(Type.VAR_INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      var1.a(Type.VAR_INT, 0, Integer.valueOf(BlockRewriter.b(this.this$0).d().c(((Integer)var1.b(Type.VAR_INT, 0)).intValue())));
   }
}
