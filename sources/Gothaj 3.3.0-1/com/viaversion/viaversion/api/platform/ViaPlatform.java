package com.viaversion.viaversion.api.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Logger;

public interface ViaPlatform<T> {
   Logger getLogger();

   String getPlatformName();

   String getPlatformVersion();

   default boolean isProxy() {
      return false;
   }

   String getPluginVersion();

   PlatformTask runAsync(Runnable var1);

   PlatformTask runRepeatingAsync(Runnable var1, long var2);

   PlatformTask runSync(Runnable var1);

   PlatformTask runSync(Runnable var1, long var2);

   PlatformTask runRepeatingSync(Runnable var1, long var2);

   ViaCommandSender[] getOnlinePlayers();

   void sendMessage(UUID var1, String var2);

   boolean kickPlayer(UUID var1, String var2);

   default boolean disconnect(UserConnection connection, String message) {
      if (connection.isClientSide()) {
         return false;
      } else {
         UUID uuid = connection.getProtocolInfo().getUuid();
         return uuid == null ? false : this.kickPlayer(uuid, message);
      }
   }

   boolean isPluginEnabled();

   ViaAPI<T> getApi();

   ViaVersionConfig getConf();

   @Deprecated
   default ConfigurationProvider getConfigurationProvider() {
      return Via.getManager().getConfigurationProvider();
   }

   File getDataFolder();

   void onReload();

   JsonObject getDump();

   default boolean isOldClientsAllowed() {
      return true;
   }

   default Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
      return Collections.emptyList();
   }

   boolean hasPlugin(String var1);
}
