package net.minecraft.world.biome;

import net.minecraft.entity.EnumCreatureType;

// $FF: synthetic class
class BiomeGenBase$1 {
   static final int[] $SwitchMap$net$minecraft$entity$EnumCreatureType = new int[EnumCreatureType.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$entity$EnumCreatureType[EnumCreatureType.MONSTER.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$EnumCreatureType[EnumCreatureType.CREATURE.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$EnumCreatureType[EnumCreatureType.WATER_CREATURE.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$EnumCreatureType[EnumCreatureType.AMBIENT.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
