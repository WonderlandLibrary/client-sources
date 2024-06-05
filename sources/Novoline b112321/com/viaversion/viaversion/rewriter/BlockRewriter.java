package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.BlockRewriter$2;
import net.a7E;
import net.a7H;
import net.a7i;
import net.a7l;
import net.ayx;
import net.y7;

public class BlockRewriter {
   private final ayx b;
   private final Type positionType;

   public BlockRewriter(ayx var1, Type var2) {
      this.b = var1;
      this.positionType = var2;
   }

   public void c(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new a7H(this)));
   }

   public void d(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new BlockRewriter$2(this)));
   }

   public void e(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new a7i(this)));
   }

   public void a(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new a7E(this)));
   }

   public void b(y7 var1) {
      this.d(var1);
   }

   public void a(y7 var1, int var2, int var3) {
      this.b.a((y7)var1, (PacketRemapper)(new a7l(this, var2, var3)));
   }

   static Type access$000(BlockRewriter var0) {
      return var0.positionType;
   }

   static ayx b(BlockRewriter var0) {
      return var0.b;
   }
}
