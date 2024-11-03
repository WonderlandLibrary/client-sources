package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viabackwards.listener.FireDamageListener;
import com.viaversion.viabackwards.listener.FireExtinguishListener;
import com.viaversion.viabackwards.listener.LecternInteractListener;
import com.viaversion.viabackwards.listener.PlayerItemDropListener;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin implements ViaBackwardsPlatform {
   public BukkitPlugin() {
      Via.getManager().addEnableListener(() -> this.init(new File(this.getDataFolder(), "config.yml")));
   }

   public void onEnable() {
      if (Via.getManager().getInjector().lateProtocolVersionSetting()) {
         Via.getPlatform().runSync(this::enable);
      } else {
         this.enable();
      }
   }

   private void enable() {
      int protocolVersion = Via.getAPI().getServerVersion().highestSupportedVersion();
      if (protocolVersion >= ProtocolVersion.v1_17.getVersion()) {
         new PlayerItemDropListener(this).register();
      }

      if (protocolVersion >= ProtocolVersion.v1_16.getVersion()) {
         new FireExtinguishListener(this).register();
      }

      if (protocolVersion >= ProtocolVersion.v1_14.getVersion()) {
         new LecternInteractListener(this).register();
      }

      if (protocolVersion >= ProtocolVersion.v1_12.getVersion()) {
         new FireDamageListener(this).register();
      }
   }

   @Override
   public void disable() {
      this.getPluginLoader().disablePlugin(this);
   }
}
