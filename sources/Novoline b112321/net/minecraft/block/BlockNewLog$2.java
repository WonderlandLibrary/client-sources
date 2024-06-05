package net.minecraft.block;

import net.minecraft.block.BlockLog$EnumAxis;
import net.minecraft.block.BlockPlanks$EnumType;

// $FF: synthetic class
class BlockNewLog$2 {
   static final int[] $SwitchMap$net$minecraft$block$BlockPlanks$EnumType;
   static final int[] $SwitchMap$net$minecraft$block$BlockLog$EnumAxis = new int[BlockLog$EnumAxis.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$block$BlockLog$EnumAxis[BlockLog$EnumAxis.X.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLog$EnumAxis[BlockLog$EnumAxis.Z.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLog$EnumAxis[BlockLog$EnumAxis.NONE.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLog$EnumAxis[BlockLog$EnumAxis.Y.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
         ;
      }

      $SwitchMap$net$minecraft$block$BlockPlanks$EnumType = new int[BlockPlanks$EnumType.values().length];

      try {
         $SwitchMap$net$minecraft$block$BlockPlanks$EnumType[BlockPlanks$EnumType.ACACIA.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockPlanks$EnumType[BlockPlanks$EnumType.DARK_OAK.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
