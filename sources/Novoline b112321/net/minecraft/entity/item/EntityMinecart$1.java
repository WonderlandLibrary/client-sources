package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;

// $FF: synthetic class
class EntityMinecart$1 {
   static final int[] $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType;
   static final int[] $SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection = new int[BlockRailBase$EnumRailDirection.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[BlockRailBase$EnumRailDirection.ASCENDING_EAST.ordinal()] = 1;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[BlockRailBase$EnumRailDirection.ASCENDING_WEST.ordinal()] = 2;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[BlockRailBase$EnumRailDirection.ASCENDING_NORTH.ordinal()] = 3;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[BlockRailBase$EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 4;
      } catch (NoSuchFieldError var7) {
         ;
      }

      $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType = new int[EntityMinecart$EnumMinecartType.values().length];

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.CHEST.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.FURNACE.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.TNT.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.SPAWNER.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.HOPPER.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.COMMAND_BLOCK.ordinal()] = 6;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
