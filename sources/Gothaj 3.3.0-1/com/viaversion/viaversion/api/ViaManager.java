package com.viaversion.viaversion.api;

import com.viaversion.viaversion.api.command.ViaVersionCommand;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.scheduler.Scheduler;
import java.util.Set;

public interface ViaManager {
   ProtocolManager getProtocolManager();

   ViaPlatform<?> getPlatform();

   ConnectionManager getConnectionManager();

   ViaProviders getProviders();

   ViaInjector getInjector();

   ViaVersionCommand getCommandHandler();

   ViaPlatformLoader getLoader();

   Scheduler getScheduler();

   ConfigurationProvider getConfigurationProvider();

   default boolean isDebug() {
      return this.debugHandler().enabled();
   }

   @Deprecated
   default void setDebug(boolean debug) {
      this.debugHandler().setEnabled(debug);
   }

   DebugHandler debugHandler();

   Set<String> getSubPlatforms();

   void addEnableListener(Runnable var1);

   boolean isInitialized();
}
