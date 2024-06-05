package net.minecraft.client;

import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;

// $FF: synthetic class
class Minecraft$18 {
   static final int[] $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType;
   static final int[] $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType = new int[EntityMinecart$EnumMinecartType.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.FURNACE.ordinal()] = 1;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.CHEST.ordinal()] = 2;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.TNT.ordinal()] = 3;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.HOPPER.ordinal()] = 4;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[EntityMinecart$EnumMinecartType.COMMAND_BLOCK.ordinal()] = 5;
      } catch (NoSuchFieldError var4) {
         ;
      }

      $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType = new int[MovingObjectPosition$MovingObjectType.values().length];

      try {
         $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[MovingObjectPosition$MovingObjectType.ENTITY.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[MovingObjectPosition$MovingObjectType.BLOCK.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[MovingObjectPosition$MovingObjectType.MISS.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
