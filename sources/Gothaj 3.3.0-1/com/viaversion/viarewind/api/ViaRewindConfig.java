package com.viaversion.viarewind.api;

import com.viaversion.viaversion.api.configuration.Config;

public interface ViaRewindConfig extends Config {
   ViaRewindConfig.CooldownIndicator getCooldownIndicator();

   boolean isReplaceAdventureMode();

   boolean isReplaceParticles();

   int getMaxBookPages();

   int getMaxBookPageSize();

   boolean isEmulateWorldBorder();

   String getWorldBorderParticle();

   public static enum CooldownIndicator {
      TITLE,
      ACTION_BAR,
      BOSS_BAR,
      DISABLED;
   }
}
