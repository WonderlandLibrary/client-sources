package com.viaversion.viarewind;

import com.viaversion.viarewind.api.ViaRewindPlatform;
import com.viaversion.viarewind.fabric.util.LoggerWrapper;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

public class ViaFabricAddon implements ViaRewindPlatform, Runnable {
   private final Logger logger = new LoggerWrapper(LogManager.getLogger("ViaRewind"));

   @Override
   public void run() {
      this.init(FabricLoader.getInstance().getConfigDir().resolve("ViaRewind").resolve("config.yml").toFile());
   }

   @Override
   public Logger getLogger() {
      return this.logger;
   }
}
