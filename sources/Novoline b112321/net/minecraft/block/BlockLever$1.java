package net.minecraft.block;

import net.minecraft.block.BlockLever$EnumOrientation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;

// $FF: synthetic class
class BlockLever$1 {
   static final int[] $SwitchMap$net$minecraft$util$EnumFacing;
   static final int[] $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation;
   static final int[] $SwitchMap$net$minecraft$util$EnumFacing$Axis = new int[EnumFacing$Axis.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$util$EnumFacing$Axis[EnumFacing$Axis.X.ordinal()] = 1;
      } catch (NoSuchFieldError var16) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumFacing$Axis[EnumFacing$Axis.Z.ordinal()] = 2;
      } catch (NoSuchFieldError var15) {
         ;
      }

      $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation = new int[BlockLever$EnumOrientation.values().length];

      try {
         $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[BlockLever$EnumOrientation.EAST.ordinal()] = 1;
      } catch (NoSuchFieldError var14) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[BlockLever$EnumOrientation.WEST.ordinal()] = 2;
      } catch (NoSuchFieldError var13) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[BlockLever$EnumOrientation.SOUTH.ordinal()] = 3;
      } catch (NoSuchFieldError var12) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[BlockLever$EnumOrientation.NORTH.ordinal()] = 4;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[BlockLever$EnumOrientation.UP_Z.ordinal()] = 5;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[BlockLever$EnumOrientation.UP_X.ordinal()] = 6;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[BlockLever$EnumOrientation.DOWN_X.ordinal()] = 7;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[BlockLever$EnumOrientation.DOWN_Z.ordinal()] = 8;
      } catch (NoSuchFieldError var7) {
         ;
      }

      $SwitchMap$net$minecraft$util$EnumFacing = new int[EnumFacing.values().length];

      try {
         $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.DOWN.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.UP.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.NORTH.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.SOUTH.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.WEST.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.EAST.ordinal()] = 6;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
