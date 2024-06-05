package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.SoundRewriter;

class SoundRewriter$1 extends PacketRemapper {
   final SoundRewriter this$0;

   SoundRewriter$1(SoundRewriter var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(this.this$0.a());
   }
}
