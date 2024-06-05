package net;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import net.a2M;
import net.a2f;
import net.aAP;
import net.axM;
import net.bgR;
import net.bgY;
import us.myles.ViaVersion.api.ViaAPI;

public class i0 implements ViaAPI {
   public int getPlayerVersion(UUID var1) {
      bgY.b();
      bgR var3 = Via.b().a(var1);
      if(var3 != null) {
         return var3.c().f();
      } else {
         try {
            return Via.b().g().f();
         } catch (Exception var5) {
            throw new AssertionError(var5);
         }
      }
   }

   public boolean isInjected(UUID var1) {
      return Via.b().b(var1);
   }

   public String getVersion() {
      return Via.d().f();
   }

   public void sendRawPacket(UUID var1, ByteBuf var2) throws IllegalArgumentException {
      bgR var3 = Via.b().a(var1);
      var3.c(var2);
   }

   public a2M a(String var1, axM var2, aAP var3) {
      return new a2f(var1, 1.0F, var2, var3);
   }

   public a2M a(String var1, float var2, axM var3, aAP var4) {
      return new a2f(var1, var2, var3, var4);
   }

   public SortedSet getFullSupportedVersions() {
      TreeSet var1 = new TreeSet(ProtocolManagerImpl.e());
      var1.removeAll(Via.d().getConf().a());
      return var1;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
