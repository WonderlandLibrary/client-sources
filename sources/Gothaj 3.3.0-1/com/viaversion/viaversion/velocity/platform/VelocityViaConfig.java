package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class VelocityViaConfig extends AbstractViaConfig {
   private static final List<String> UNSUPPORTED = Arrays.asList(
      "nms-player-ticking",
      "item-cache",
      "quick-move-action-fix",
      "bungee-ping-interval",
      "bungee-ping-save",
      "bungee-servers",
      "blockconnection-method",
      "change-1_9-hitbox",
      "change-1_14-hitbox"
   );
   private int velocityPingInterval;
   private boolean velocityPingSave;
   private Map<String, Integer> velocityServerProtocols;

   public VelocityViaConfig(File configFile) {
      super(new File(configFile, "config.yml"));
      this.reload();
   }

   @Override
   protected void loadFields() {
      super.loadFields();
      this.velocityPingInterval = this.getInt("velocity-ping-interval", 60);
      this.velocityPingSave = this.getBoolean("velocity-ping-save", true);
      this.velocityServerProtocols = this.get("velocity-servers", Map.class, new HashMap<>());
   }

   @Override
   protected void handleConfig(Map<String, Object> config) {
      Map<String, Object> servers;
      if (!(config.get("velocity-servers") instanceof Map)) {
         servers = new HashMap<>();
      } else {
         servers = (Map<String, Object>)config.get("velocity-servers");
      }

      for (Entry<String, Object> entry : new HashSet<>(servers.entrySet())) {
         if (!(entry.getValue() instanceof Integer)) {
            if (entry.getValue() instanceof String) {
               ProtocolVersion found = ProtocolVersion.getClosest((String)entry.getValue());
               if (found != null) {
                  servers.put(entry.getKey(), found.getVersion());
               } else {
                  servers.remove(entry.getKey());
               }
            } else {
               servers.remove(entry.getKey());
            }
         }
      }

      if (!servers.containsKey("default")) {
         try {
            servers.put("default", VelocityViaInjector.getLowestSupportedProtocolVersion());
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

      config.put("velocity-servers", servers);
   }

   @Override
   public List<String> getUnsupportedOptions() {
      return UNSUPPORTED;
   }

   @Override
   public boolean isItemCache() {
      return false;
   }

   @Override
   public boolean isNMSPlayerTicking() {
      return false;
   }

   public int getVelocityPingInterval() {
      return this.velocityPingInterval;
   }

   public boolean isVelocityPingSave() {
      return this.velocityPingSave;
   }

   public Map<String, Integer> getVelocityServerProtocols() {
      return this.velocityServerProtocols;
   }
}
