package net;

import net.aQU;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.data.ParticleMappings;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.type.Type;

class a7F extends acE {
   final Type e;
   final Type d;
   final aQU c;

   a7F(aQU var1, Type var2, Type var3) {
      this.c = var1;
      this.e = var2;
      this.d = var3;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.BOOLEAN);
      this.a(this.e);
      MetadataRewriter.e();
      this.a(this.e);
      this.a(this.e);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(Type var1, PacketWrapper var2) throws Exception {
      MetadataRewriter.c();
      int var4 = ((Integer)var2.get(Type.INT, 0)).intValue();
      if(var4 != -1) {
         ParticleMappings var5 = aQU.a(this.c).getMappingData().getParticleMappings();
         if(var4 == var5.getBlockId() || var4 == var5.getFallingDustId()) {
            int var6 = ((Integer)var2.passthrough(Type.VAR_INT)).intValue();
            var2.set(Type.VAR_INT, 0, Integer.valueOf(aQU.a(this.c).getMappingData().getNewBlockStateId(var6)));
         }

         if(var4 == var5.getItemId()) {
            aQU.b(this.c).rewrite((Item)var2.passthrough(var1));
         }

         int var7 = aQU.a(this.c).getMappingData().getNewParticleId(var4);
         if(var7 != var4) {
            var2.set(Type.INT, 0, Integer.valueOf(var7));
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
