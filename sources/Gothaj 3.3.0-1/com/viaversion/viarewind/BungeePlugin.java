package com.viaversion.viarewind;

import com.viaversion.viarewind.api.ViaRewindPlatform;
import com.viaversion.viaversion.api.Via;
import java.io.File;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin extends Plugin implements ViaRewindPlatform {
   public void onLoad() {
      Via.getManager().addEnableListener(() -> this.init(new File(this.getDataFolder(), "config.yml")));
   }
}
