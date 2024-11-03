package com.viaversion.viarewind;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.viaversion.viarewind.api.ViaRewindPlatform;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.velocity.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
   id = "viarewind",
   name = "ViaRewind",
   version = "3.0.6-SNAPSHOT",
   authors = {"Gerrygames", "FlorianMichael/EnZaXD", "creeper123123321"},
   dependencies = {@Dependency(
      id = "viaversion"
   ), @Dependency(
      id = "viabackwards",
      optional = true
   )},
   url = "https://viaversion.com/rewind"
)
public class VelocityPlugin implements ViaRewindPlatform {
   private Logger logger;
   @Inject
   private org.slf4j.Logger loggerSlf4j;
   @Inject
   @DataDirectory
   private Path configDir;

   @Subscribe(
      order = PostOrder.LATE
   )
   public void onProxyStart(ProxyInitializeEvent e) {
      this.logger = new LoggerWrapper(this.loggerSlf4j);
      Via.getManager().addEnableListener(() -> this.init(new File(this.configDir.toFile(), "config.yml")));
   }

   @Override
   public Logger getLogger() {
      return this.logger;
   }
}
