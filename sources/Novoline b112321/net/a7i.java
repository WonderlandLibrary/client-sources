package net;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import net.EN;
import net.aTD;

class a7i extends PacketRemapper {
   final BlockRewriter c;

   a7i(BlockRewriter var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.I);
      this.a(Type.I);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      aTD.c();
      BlockChangeRecord[] var3 = (BlockChangeRecord[])var1.a(Type.BLOCK_CHANGE_RECORD_ARRAY);
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         BlockChangeRecord var6 = var3[var5];
         var6.setBlockId(BlockRewriter.b(this.c).d().c(var6.getBlockId()));
         ++var5;
      }

   }
}
