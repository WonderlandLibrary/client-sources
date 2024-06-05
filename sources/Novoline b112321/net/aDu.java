package net;

import java.util.UUID;
import net.acE;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import viaversion.viarewind.utils.Utils;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class aDu extends acE {
   public void registerMap() {
      this.a(aDu::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      var0.cancel();
      afz.b();
      TitleRenderProvider var2 = (TitleRenderProvider)Via.getManager().f().b(TitleRenderProvider.class);
      if(var2 != null) {
         int var3 = ((Integer)var0.read(Type.VAR_INT)).intValue();
         UUID var4 = Utils.getUUID(var0.user());
         switch(var3) {
         case 0:
            var2.setTitle(var4, (String)var0.read(Type.STRING));
         case 1:
            var2.setSubTitle(var4, (String)var0.read(Type.STRING));
         case 2:
            var2.setTimings(var4, ((Integer)var0.read(Type.INT)).intValue(), ((Integer)var0.read(Type.INT)).intValue(), ((Integer)var0.read(Type.INT)).intValue());
         case 3:
            var2.clear(var4);
         case 4:
            var2.reset(var4);
         default:
         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
