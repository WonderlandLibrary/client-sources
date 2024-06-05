package net;

import java.util.Optional;
import net.BS;
import net.aWm;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;

class awz implements PacketHandler {
   final aWm a;

   awz(aWm var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      PaintingProvider var3 = (PaintingProvider)Via.getManager().f().b(PaintingProvider.class);
      BS.b();
      String var4 = (String)var1.read(Type.STRING);
      Optional var5 = var3.getIntByIdentifier(var4);
      if(!var5.isPresent() && (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug())) {
         Via.getPlatform().getLogger().warning("Could not find painting motive: " + var4 + " falling back to default (0)");
      }

      var1.write(Type.VAR_INT, var5.orElse(Integer.valueOf(0)));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
