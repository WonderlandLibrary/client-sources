package net;

import com.google.common.base.Joiner;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Level;
import net.a66;
import net.a7h;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.protocol.ProtocolRegistry;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.base.ProtocolInfo;

class aed implements PacketHandler {
   final a7h a;

   aed(a7h var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      ProtocolInfo var3 = var1.user().getProtocolInfo();
      ProtocolInfo.d();
      var3.a(a66.PLAY);
      UUID var4 = this.a.c.passthroughLoginUUID(var1);
      var3.setUuid(var4);
      String var5 = (String)var1.passthrough(Type.STRING);
      var3.setUsername(var5);
      Via.getManager().handleLoginSuccess(var1.user());
      if(var3.getPipeline().pipes().stream().allMatch(ProtocolRegistry::isBaseProtocol)) {
         var1.user().setActive(false);
      }

      if(Via.getManager().isDebug()) {
         Via.getPlatform().getLogger().log(Level.INFO, "{0} logged in with protocol {1}, Route: {2}", new Object[]{var5, Integer.valueOf(var3.getProtocolVersion()), Joiner.on(", ").join(var3.getPipeline().pipes(), ", ", new Object[0])});
      }

      if(acE.b() == null) {
         ProtocolInfo.b("J4apEb");
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
