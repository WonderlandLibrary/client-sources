package com.viaversion.viarewind;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViaRewindConfig extends Config implements com.viaversion.viarewind.api.ViaRewindConfig {
   public ViaRewindConfig(File configFile) {
      super(configFile);
      this.reload();
   }

   @Override
   public com.viaversion.viarewind.api.ViaRewindConfig.CooldownIndicator getCooldownIndicator() {
      return com.viaversion.viarewind.api.ViaRewindConfig.CooldownIndicator.valueOf(this.getString("cooldown-indicator", "TITLE").toUpperCase());
   }

   @Override
   public boolean isReplaceAdventureMode() {
      return this.getBoolean("replace-adventure", false);
   }

   @Override
   public boolean isReplaceParticles() {
      return this.getBoolean("replace-particles", false);
   }

   @Override
   public int getMaxBookPages() {
      return this.getInt("max-book-pages", 100);
   }

   @Override
   public int getMaxBookPageSize() {
      return this.getInt("max-book-page-length", 5000);
   }

   @Override
   public boolean isEmulateWorldBorder() {
      return this.getBoolean("emulate-world-border", true);
   }

   @Override
   public String getWorldBorderParticle() {
      return this.getString("world-border-particle", "fireworksSpark");
   }

   @Override
   public URL getDefaultConfigURL() {
      return this.getClass().getClassLoader().getResource("assets/viarewind/config.yml");
   }

   @Override
   protected void handleConfig(Map<String, Object> map) {
   }

   @Override
   public List<String> getUnsupportedOptions() {
      return Collections.emptyList();
   }
}
