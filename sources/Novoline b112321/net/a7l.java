package net;

import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.BlockRewriter;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.type.Type;

class a7l extends acE {
   final int c;
   final int e;
   final BlockRewriter d;

   a7l(BlockRewriter var1, int var2, int var3) {
      this.d = var1;
      this.c = var2;
      this.e = var3;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(BlockRewriter.access$000(this.d));
      this.a(Type.INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(int var1, int var2, PacketWrapper var3) throws Exception {
      int var5 = ((Integer)var3.get(Type.INT, 0)).intValue();
      MetadataRewriter.e();
      int var6 = ((Integer)var3.get(Type.INT, 1)).intValue();
      if(var5 == var1) {
         var3.set(Type.INT, 1, Integer.valueOf(BlockRewriter.access$100(this.d).getMappingData().getNewItemId(var6)));
      }

      if(var5 == var2) {
         var3.set(Type.INT, 1, Integer.valueOf(BlockRewriter.access$100(this.d).getMappingData().getNewBlockStateId(var6)));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
