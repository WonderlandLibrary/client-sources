package net;

import net.aRY;
import net.aVd;
import net.aXe;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueTransformer;
import viaversion.viaversion.api.type.Type;

class Z7 extends ValueTransformer {
   final aVd d;

   Z7(aVd var1, Type var2) {
      super(var2);
      this.d = var1;
   }

   public Void a(PacketWrapper var1, Boolean var2) throws Exception {
      aXe.b();
      cq var4 = (cq)var1.user().b(cq.class);
      if(!var2.booleanValue()) {
         int var5 = ((Integer)var1.get(Type.INT, 0)).intValue();
         int var6 = ((Integer)var1.get(Type.INT, 1)).intValue();
         var1.cancel();
         PacketWrapper var7 = var1.create(64);
         if(var6 == -1) {
            if(!var4.b().containsKey(Integer.valueOf(var5))) {
               return null;
            }

            var7.write(Type.VAR_INT, var4.b().remove(Integer.valueOf(var5)));
            var7.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
         }

         var7.write(Type.VAR_INT, Integer.valueOf(var6));
         var7.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{var5});
         var4.b().put(Integer.valueOf(var5), Integer.valueOf(var6));
         var7.send(aRY.class);
      }

      return null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
