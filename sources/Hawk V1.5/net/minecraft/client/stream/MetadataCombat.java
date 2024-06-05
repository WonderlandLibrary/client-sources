package net.minecraft.client.stream;

import net.minecraft.entity.EntityLivingBase;

public class MetadataCombat extends Metadata {
   private static final String __OBFID = "CL_00002377";

   public MetadataCombat(EntityLivingBase var1, EntityLivingBase var2) {
      super("player_combat");
      this.func_152808_a("player", var1.getName());
      if (var2 != null) {
         this.func_152808_a("primary_opponent", var2.getName());
      }

      if (var2 != null) {
         this.func_152807_a(String.valueOf((new StringBuilder("Combat between ")).append(var1.getName()).append(" and ").append(var2.getName())));
      } else {
         this.func_152807_a(String.valueOf((new StringBuilder("Combat between ")).append(var1.getName()).append(" and others")));
      }

   }
}
