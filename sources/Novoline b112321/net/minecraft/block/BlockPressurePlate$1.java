package net.minecraft.block;

import net.minecraft.block.BlockPressurePlate$Sensitivity;

// $FF: synthetic class
class BlockPressurePlate$1 {
   static final int[] $SwitchMap$net$minecraft$block$BlockPressurePlate$Sensitivity = new int[BlockPressurePlate$Sensitivity.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$block$BlockPressurePlate$Sensitivity[BlockPressurePlate$Sensitivity.EVERYTHING.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockPressurePlate$Sensitivity[BlockPressurePlate$Sensitivity.MOBS.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
