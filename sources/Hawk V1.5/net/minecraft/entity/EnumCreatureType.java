package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;

public enum EnumCreatureType {
   private static final EnumCreatureType[] ENUM$VALUES = new EnumCreatureType[]{MONSTER, CREATURE, AMBIENT, WATER_CREATURE};
   private final boolean isAnimal;
   private final Material creatureMaterial;
   WATER_CREATURE("WATER_CREATURE", 3, EntityWaterMob.class, 5, Material.water, true, false);

   private static final EnumCreatureType[] $VALUES = new EnumCreatureType[]{MONSTER, CREATURE, AMBIENT, WATER_CREATURE};
   MONSTER("MONSTER", 0, IMob.class, 70, Material.air, false, false);

   private final boolean isPeacefulCreature;
   AMBIENT("AMBIENT", 2, EntityAmbientCreature.class, 15, Material.air, true, false);

   private static final String __OBFID = "CL_00001551";
   private final Class creatureClass;
   private final int maxNumberOfCreature;
   CREATURE("CREATURE", 1, EntityAnimal.class, 10, Material.air, true, true);

   public boolean getAnimal() {
      return this.isAnimal;
   }

   public int getMaxNumberOfCreature() {
      return this.maxNumberOfCreature;
   }

   public Class getCreatureClass() {
      return this.creatureClass;
   }

   private EnumCreatureType(String var3, int var4, Class var5, int var6, Material var7, boolean var8, boolean var9) {
      this.creatureClass = var5;
      this.maxNumberOfCreature = var6;
      this.creatureMaterial = var7;
      this.isPeacefulCreature = var8;
      this.isAnimal = var9;
   }

   public boolean getPeacefulCreature() {
      return this.isPeacefulCreature;
   }
}
