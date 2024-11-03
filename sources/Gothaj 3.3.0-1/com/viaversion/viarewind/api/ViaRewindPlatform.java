package com.viaversion.viarewind.api;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.Protocol1_7_2_5To1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.io.File;
import java.util.logging.Logger;

public interface ViaRewindPlatform {
   String VERSION = "3.0.6-SNAPSHOT";
   String IMPL_VERSION = "git-viarewind-common-3.0.6-SNAPSHOT:05dea07";

   default void init(File configFile) {
      com.viaversion.viarewind.ViaRewindConfig config = new com.viaversion.viarewind.ViaRewindConfig(configFile);
      config.reload();
      Via.getManager().getConfigurationProvider().register(config);
      ViaRewind.init(this, config);
      Via.getManager().getSubPlatforms().add("git-viarewind-common-3.0.6-SNAPSHOT:05dea07");
      ProtocolManager protocolManager = Via.getManager().getProtocolManager();
      protocolManager.registerProtocol(new Protocol1_7_2_5To1_7_6_10(), ProtocolVersion.v1_7_1, ProtocolVersion.v1_7_6);
      protocolManager.registerProtocol(new Protocol1_7_6_10To1_8(), ProtocolVersion.v1_7_6, ProtocolVersion.v1_8);
      protocolManager.registerProtocol(new Protocol1_8To1_9(), ProtocolVersion.v1_8, ProtocolVersion.v1_9);
   }

   Logger getLogger();
}
