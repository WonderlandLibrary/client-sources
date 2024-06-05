package net;

import java.util.Iterator;
import java.util.List;
import net.BS;
import net.Gh;
import net.aWu;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.Particle$ParticleData;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;

class adA implements PacketHandler {
   final aWu a;

   adA(aWu var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      BS.b();
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      byte var4 = 0;
      if(var3 == 37 || var3 == 38 || var3 == 46) {
         var4 = 1;
      }

      if(var3 == 36) {
         var4 = 2;
      }

      Integer[] var5 = new Integer[var4];
      int var6 = 0;
      if(var6 < var5.length) {
         var5[var6] = (Integer)var1.read(Type.VAR_INT);
         ++var6;
      }

      Gh var13 = ParticleRewriter.a(var3, var5);
      if(var13.c() == -1) {
         var1.cancel();
      } else {
         if(var13.c() == 11) {
            int var7 = ((Integer)var1.get(Type.INT, 1)).intValue();
            float var8 = ((Float)var1.get(Type.FLOAT, 6)).floatValue();
            var1.set(Type.INT, 1, Integer.valueOf(1));
            var1.set(Type.FLOAT, 6, Float.valueOf(0.0F));
            List var9 = var13.d();
            int var10 = 0;
            if(var10 < 3) {
               float var11 = ((Float)var1.get(Type.FLOAT, var10 + 3)).floatValue() * var8;
               if(var11 == 0.0F) {
                  var11 = 1.0F;
               }

               ((Particle$ParticleData)var9.get(var10)).setValue(Float.valueOf(var11));
               var1.set(Type.FLOAT, var10 + 3, Float.valueOf(0.0F));
               ++var10;
            }
         }

         var1.set(Type.INT, 0, Integer.valueOf(var13.c()));
         Iterator var14 = var13.d().iterator();
         if(var14.hasNext()) {
            Particle$ParticleData var15 = (Particle$ParticleData)var14.next();
            var1.write(var15.getType(), var15.getValue());
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
