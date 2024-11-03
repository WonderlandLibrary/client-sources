package de.florianmichael.vialoadingbase.platform;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import java.io.File;
import java.util.logging.Logger;

public class ViaBackwardsPlatformImpl implements ViaBackwardsPlatform {
   private final File directory;

   public ViaBackwardsPlatformImpl(File directory) {
      this.init(new File(this.directory = directory, "viabackwards.yml"));
   }

   @Override
   public Logger getLogger() {
      return ViaLoadingBase.LOGGER;
   }

   @Override
   public boolean isOutdated() {
      return false;
   }

   @Override
   public void disable() {
   }

   @Override
   public File getDataFolder() {
      return this.directory;
   }
}
