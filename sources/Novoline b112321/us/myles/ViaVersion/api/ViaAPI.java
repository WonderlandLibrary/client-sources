package us.myles.ViaVersion.api;

import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.UUID;
import net.a2M;
import net.aAP;
import net.axM;

public interface ViaAPI {
   int getPlayerVersion(Object var1);

   int getPlayerVersion(UUID var1);

   /** @deprecated */
   @Deprecated
   default boolean isPorted(UUID var1) {
      return this.isInjected(var1);
   }

   boolean isInjected(UUID var1);

   String getVersion();

   void sendRawPacket(Object var1, ByteBuf var2);

   void sendRawPacket(UUID var1, ByteBuf var2);

   a2M a(String var1, axM var2, aAP var3);

   a2M a(String var1, float var2, axM var3, aAP var4);

   SortedSet getFullSupportedVersions();
}
