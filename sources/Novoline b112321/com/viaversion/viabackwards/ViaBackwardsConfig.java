package com.viaversion.viabackwards;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViaBackwardsConfig extends Config implements com.viaversion.viabackwards.api.ViaBackwardsConfig {
   private boolean addCustomEnchantsToLore;
   private boolean addTeamColorToPrefix;
   private boolean fix1_13FacePlayer;
   private boolean alwaysShowOriginalMobName;

   public ViaBackwardsConfig(File var1) {
      super(var1);
   }

   public void reloadConfig() {
      super.reloadConfig();
      this.loadFields();
   }

   private void loadFields() {
      this.addCustomEnchantsToLore = this.getBoolean("add-custom-enchants-into-lore", true);
      this.addTeamColorToPrefix = this.getBoolean("add-teamcolor-to-prefix", true);
      this.fix1_13FacePlayer = this.getBoolean("fix-1_13-face-player", false);
      this.alwaysShowOriginalMobName = this.getBoolean("always-show-original-mob-name", true);
   }

   public boolean addCustomEnchantsToLore() {
      return this.addCustomEnchantsToLore;
   }

   public boolean addTeamColorTo1_13Prefix() {
      return this.addTeamColorToPrefix;
   }

   public boolean isFix1_13FacePlayer() {
      return this.fix1_13FacePlayer;
   }

   public boolean alwaysShowOriginalMobName() {
      return this.alwaysShowOriginalMobName;
   }

   public URL getDefaultConfigURL() {
      return this.getClass().getClassLoader().getResource("assets/viabackwards/config.yml");
   }

   protected void handleConfig(Map var1) {
   }

   public List getUnsupportedOptions() {
      return Collections.emptyList();
   }
}
