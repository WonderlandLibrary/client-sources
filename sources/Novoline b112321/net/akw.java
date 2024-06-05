package net;

import com.viaversion.viabackwards.api.ViaBackwardsConfig;
import net.api;

class akw implements ViaBackwardsConfig {
   final api a;

   akw(api var1) {
      this.a = var1;
   }

   public boolean addCustomEnchantsToLore() {
      return true;
   }

   public boolean addTeamColorTo1_13Prefix() {
      return true;
   }

   public boolean isFix1_13FacePlayer() {
      return true;
   }

   public boolean alwaysShowOriginalMobName() {
      return true;
   }
}
