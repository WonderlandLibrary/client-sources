package net;

import com.google.gson.JsonElement;
import net.aRY;
import net.acE;
import net.ay_;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class act extends acE {
   final ay_ c;

   act(ay_ var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(act::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      ay_.d();
      int var2 = ((Integer)var0.passthrough(Type.VAR_INT)).intValue();
      if(var2 >= 0 && var2 <= 2) {
         JsonElement var3 = (JsonElement)var0.read(Type.COMPONENT);
         var0.write(Type.COMPONENT, aRY.b(var3.toString()));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
