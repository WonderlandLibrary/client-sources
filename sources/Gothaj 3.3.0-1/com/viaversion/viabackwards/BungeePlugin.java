package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viaversion.api.Via;
import java.io.File;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin extends Plugin implements ViaBackwardsPlatform {
   public void onLoad() {
      Via.getManager().addEnableListener(() -> this.init(new File(this.getDataFolder(), "config.yml")));
   }

   @Override
   public void disable() {
   }
}
