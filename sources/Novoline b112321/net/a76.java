package net;

import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.IdRewriteFunction;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.rewriters.RegistryType;
import viaversion.viaversion.api.rewriters.StatisticsRewriter;
import viaversion.viaversion.api.type.Type;

class a76 extends acE {
   final StatisticsRewriter c;

   a76(StatisticsRewriter var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      MetadataRewriter.c();
      int var4 = var3;
      int var5 = 0;
      if(var5 < var3) {
         int var6;
         int var7;
         int var8;
         label30: {
            var6 = ((Integer)var1.read(Type.VAR_INT)).intValue();
            var7 = ((Integer)var1.read(Type.VAR_INT)).intValue();
            var8 = ((Integer)var1.read(Type.VAR_INT)).intValue();
            if(var6 == 8 && StatisticsRewriter.access$000(this.c).getMappingData().getStatisticsMappings() != null) {
               var7 = StatisticsRewriter.access$000(this.c).getMappingData().getStatisticsMappings().getNewId(var7);
               if(var7 != -1) {
                  break label30;
               }

               var4 = var3 - 1;
            }

            RegistryType var9 = this.c.getRegistryTypeForStatistic(var6);
            IdRewriteFunction var10;
            if((var10 = this.c.getRewriter(var9)) != null) {
               var7 = var10.rewrite(var7);
            }
         }

         var1.write(Type.VAR_INT, Integer.valueOf(var6));
         var1.write(Type.VAR_INT, Integer.valueOf(var7));
         var1.write(Type.VAR_INT, Integer.valueOf(var8));
         ++var5;
      }

      if(var4 != var3) {
         var1.set(Type.VAR_INT, 0, Integer.valueOf(var4));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
