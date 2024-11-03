package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.BungeePlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.bungee.handlers.BungeeServerHandler;
import com.viaversion.viaversion.bungee.listeners.ElytraPatch;
import com.viaversion.viaversion.bungee.listeners.UpdateListener;
import com.viaversion.viaversion.bungee.providers.BungeeBossBarProvider;
import com.viaversion.viaversion.bungee.providers.BungeeEntityIdProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMainHandProvider;
import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class BungeeViaLoader implements ViaPlatformLoader {
   private final Set<Listener> listeners = new HashSet<>();
   private final Set<ScheduledTask> tasks = new HashSet<>();
   private final BungeePlugin plugin;

   public BungeeViaLoader(BungeePlugin plugin) {
      this.plugin = plugin;
   }

   private void registerListener(Listener listener) {
      this.listeners.add(listener);
      ProxyServer.getInstance().getPluginManager().registerListener(this.plugin, listener);
   }

   @Override
   public void load() {
      this.registerListener(this.plugin);
      this.registerListener(new UpdateListener());
      this.registerListener(new BungeeServerHandler());
      if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
         this.registerListener(new ElytraPatch());
      }

      Via.getManager().getProviders().use(VersionProvider.class, new BungeeVersionProvider());
      Via.getManager().getProviders().use(EntityIdProvider.class, new BungeeEntityIdProvider());
      if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
         Via.getManager().getProviders().use(BossBarProvider.class, new BungeeBossBarProvider());
         Via.getManager().getProviders().use(MainHandProvider.class, new BungeeMainHandProvider());
      }

      if (this.plugin.getConf().getBungeePingInterval() > 0) {
         this.tasks
            .add(
               this.plugin
                  .getProxy()
                  .getScheduler()
                  .schedule(
                     this.plugin,
                     () -> Via.proxyPlatform().protocolDetectorService().probeAllServers(),
                     0L,
                     (long)this.plugin.getConf().getBungeePingInterval(),
                     TimeUnit.SECONDS
                  )
            );
      }
   }

   @Override
   public void unload() {
      for (Listener listener : this.listeners) {
         ProxyServer.getInstance().getPluginManager().unregisterListener(listener);
      }

      this.listeners.clear();

      for (ScheduledTask task : this.tasks) {
         task.cancel();
      }

      this.tasks.clear();
   }
}
