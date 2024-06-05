package net.minecraft.client.stream;

import net.minecraft.client.stream.Metadata;
import net.minecraft.entity.EntityLivingBase;

public class MetadataCombat extends Metadata {
   public MetadataCombat(EntityLivingBase var1, EntityLivingBase var2) {
      super("player_combat");
      this.func_152808_a("player", var1.getName());
      this.func_152808_a("primary_opponent", var2.getName());
      this.func_152807_a("Combat between " + var1.getName() + " and " + var2.getName());
   }
}
