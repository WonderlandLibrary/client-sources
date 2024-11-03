package com.viaversion.viaversion.bungee.service;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bungee.platform.BungeeViaConfig;
import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
import com.viaversion.viaversion.platform.AbstractProtocolDetectorService;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public final class ProtocolDetectorService extends AbstractProtocolDetectorService {
   public void probeServer(ServerInfo serverInfo) {
      String serverName = serverInfo.getName();
      serverInfo.ping((serverPing, throwable) -> {
         if (throwable == null && serverPing != null && serverPing.getVersion() != null && serverPing.getVersion().getProtocol() > 0) {
            int oldProtocolVersion = this.serverProtocolVersion(serverName);
            if (oldProtocolVersion != serverPing.getVersion().getProtocol()) {
               this.setProtocolVersion(serverName, serverPing.getVersion().getProtocol());
               BungeeViaConfig config = (BungeeViaConfig)Via.getConfig();
               if (config.isBungeePingSave()) {
                  Map<String, Integer> servers = config.getBungeeServerProtocols();
                  Integer protocol = servers.get(serverName);
                  if (protocol != null && protocol == serverPing.getVersion().getProtocol()) {
                     return;
                  }

                  synchronized (Via.getPlatform().getConfigurationProvider()) {
                     servers.put(serverName, serverPing.getVersion().getProtocol());
                  }

                  config.save();
               }
            }
         }
      });
   }

   @Override
   public void probeAllServers() {
      Collection<ServerInfo> servers = ProxyServer.getInstance().getServers().values();
      Set<String> serverNames = new HashSet<>(servers.size());

      for (ServerInfo serverInfo : servers) {
         this.probeServer(serverInfo);
         serverNames.add(serverInfo.getName());
      }

      this.lock.writeLock().lock();

      try {
         this.detectedProtocolIds.keySet().retainAll(serverNames);
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   @Override
   protected Map<String, Integer> configuredServers() {
      return ((BungeeViaConfig)Via.getConfig()).getBungeeServerProtocols();
   }

   @Override
   protected int lowestSupportedProtocolVersion() {
      return BungeeVersionProvider.getLowestSupportedVersion();
   }
}
