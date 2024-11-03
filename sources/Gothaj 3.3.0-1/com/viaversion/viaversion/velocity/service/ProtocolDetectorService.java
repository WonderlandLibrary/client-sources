package com.viaversion.viaversion.velocity.service;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.platform.AbstractProtocolDetectorService;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ProtocolDetectorService extends AbstractProtocolDetectorService {
   @Override
   public void probeAllServers() {
      Collection<RegisteredServer> servers = VelocityPlugin.PROXY.getAllServers();
      Set<String> serverNames = new HashSet<>(servers.size());

      for (RegisteredServer server : servers) {
         this.probeServer(server);
         serverNames.add(server.getServerInfo().getName());
      }

      this.lock.writeLock().lock();

      try {
         this.detectedProtocolIds.keySet().retainAll(serverNames);
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   public void probeServer(RegisteredServer server) {
      String serverName = server.getServerInfo().getName();
      server.ping().thenAccept(serverPing -> {
         if (serverPing != null && serverPing.getVersion() != null) {
            int oldProtocolVersion = this.serverProtocolVersion(serverName);
            if (oldProtocolVersion == -1 || oldProtocolVersion != serverPing.getVersion().getProtocol()) {
               this.setProtocolVersion(serverName, serverPing.getVersion().getProtocol());
               VelocityViaConfig config = (VelocityViaConfig)Via.getConfig();
               if (config.isVelocityPingSave()) {
                  Map<String, Integer> servers = this.configuredServers();
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
   protected Map<String, Integer> configuredServers() {
      return ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
   }

   @Override
   protected int lowestSupportedProtocolVersion() {
      try {
         return ProtocolVersion.getProtocol(Via.getManager().getInjector().getServerProtocolVersion()).getVersion();
      } catch (Exception var2) {
         var2.printStackTrace();
         return ProtocolVersion.v1_8.getVersion();
      }
   }
}
