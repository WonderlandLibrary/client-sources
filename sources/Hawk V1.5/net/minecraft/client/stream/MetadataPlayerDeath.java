package net.minecraft.client.stream;

import net.minecraft.entity.EntityLivingBase;

public class MetadataPlayerDeath extends Metadata {
   private static final String __OBFID = "CL_00002376";

   public MetadataPlayerDeath(EntityLivingBase var1, EntityLivingBase var2) {
      super("player_death");
      if (var1 != null) {
         this.func_152808_a("player", var1.getName());
      }

      if (var2 != null) {
         this.func_152808_a("killer", var2.getName());
      }

   }
}
