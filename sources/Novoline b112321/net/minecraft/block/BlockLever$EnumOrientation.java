package net.minecraft.block;

import net.minecraft.block.BlockLever$1;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public enum BlockLever$EnumOrientation implements IStringSerializable {
   DOWN_X(0, "down_x", EnumFacing.DOWN),
   EAST(1, "east", EnumFacing.EAST),
   WEST(2, "west", EnumFacing.WEST),
   SOUTH(3, "south", EnumFacing.SOUTH),
   NORTH(4, "north", EnumFacing.NORTH),
   UP_Z(5, "up_z", EnumFacing.UP),
   UP_X(6, "up_x", EnumFacing.UP),
   DOWN_Z(7, "down_z", EnumFacing.DOWN);

   private static final BlockLever$EnumOrientation[] META_LOOKUP = new BlockLever$EnumOrientation[values().length];
   private final int meta;
   private final String name;
   private final EnumFacing facing;
   private static final BlockLever$EnumOrientation[] $VALUES = new BlockLever$EnumOrientation[]{DOWN_X, EAST, WEST, SOUTH, NORTH, UP_Z, UP_X, DOWN_Z};

   private BlockLever$EnumOrientation(int var3, String var4, EnumFacing var5) {
      this.meta = var3;
      this.name = var4;
      this.facing = var5;
   }

   public int getMetadata() {
      return this.meta;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }

   public String toString() {
      return this.name;
   }

   public static BlockLever$EnumOrientation byMetadata(int var0) {
      if(var0 >= META_LOOKUP.length) {
         var0 = 0;
      }

      return META_LOOKUP[var0];
   }

   public static BlockLever$EnumOrientation forFacings(EnumFacing var0, EnumFacing var1) {
      switch(BlockLever$1.$SwitchMap$net$minecraft$util$EnumFacing[var0.ordinal()]) {
      case 1:
         switch(BlockLever$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[var1.getAxis().ordinal()]) {
         case 1:
            return DOWN_X;
         case 2:
            return DOWN_Z;
         default:
            throw new IllegalArgumentException("Invalid entityFacing " + var1 + " for facing " + var0);
         }
      case 2:
         switch(BlockLever$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[var1.getAxis().ordinal()]) {
         case 1:
            return UP_X;
         case 2:
            return UP_Z;
         default:
            throw new IllegalArgumentException("Invalid entityFacing " + var1 + " for facing " + var0);
         }
      case 3:
         return NORTH;
      case 4:
         return SOUTH;
      case 5:
         return WEST;
      case 6:
         return EAST;
      default:
         throw new IllegalArgumentException("Invalid facing: " + var0);
      }
   }

   public String getName() {
      return this.name;
   }

   static {
      for(BlockLever$EnumOrientation var10 : values()) {
         META_LOOKUP[var10.getMetadata()] = var10;
      }

   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
