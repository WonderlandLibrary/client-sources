package cc.novoline.viaversion.platform;

import cc.novoline.viaversion.platform.ViaBackwardsPlatformImplementation$1;
import java.io.File;
import java.util.logging.Logger;
import net.NO;
import net.VV;
import net.minecraft.client.Minecraft;
import viaversion.viabackwards.api.ViaBackwardsPlatform;
import viaversion.viaversion.api.Via;

public class ViaBackwardsPlatformImplementation implements ViaBackwardsPlatform {
   public ViaBackwardsPlatformImplementation() {
      NO.c();
      VV.a(this, new ViaBackwardsPlatformImplementation$1(this));
      this.init(Minecraft.getInstance().mcDataDir);
   }

   public Logger getLogger() {
      return Via.getPlatform().getLogger();
   }

   public void disable() {
   }

   public boolean isOutdated() {
      return false;
   }

   public File getDataFolder() {
      return Minecraft.getInstance().mcDataDir;
   }
}
