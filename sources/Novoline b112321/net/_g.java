package net;

import java.util.Collections;
import java.util.logging.Logger;
import net.JM;
import net.aRE;
import net.aRf;
import net.aRi;
import net.bY;
import viaversion.viarewind.api.ViaRewindConfig;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.protocol.ProtocolRegistry;
import viaversion.viaversion.api.protocol.ProtocolVersion;

public interface _g {
   String a = "UNKNOWN";

   default void a(ViaRewindConfig var1) {
      bY.b();
      JM.a(this, var1);
      String var3 = JM.class.getPackage().getImplementationVersion();
      Via.getManager().getSubPlatforms().add(var3 != null?var3:a);
      ProtocolRegistry.registerProtocol(new aRE(), Collections.singletonList(Integer.valueOf(ProtocolVersion.v1_8.getId())), ProtocolVersion.v1_9.getId());
      ProtocolRegistry.registerProtocol(new aRi(), Collections.singletonList(Integer.valueOf(ProtocolVersion.v1_7_6.getId())), ProtocolVersion.v1_8.getId());
      ProtocolRegistry.registerProtocol(new aRf(), Collections.singletonList(Integer.valueOf(ProtocolVersion.v1_7_1.getId())), ProtocolVersion.v1_7_6.getId());
   }

   Logger a();
}
