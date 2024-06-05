package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockDoor$EnumDoorHalf implements IStringSerializable {
   UPPER,
   LOWER;

   private static final BlockDoor$EnumDoorHalf[] $VALUES = new BlockDoor$EnumDoorHalf[]{UPPER, LOWER};

   public String toString() {
      return this.getName();
   }

   public String getName() {
      return this == UPPER?"upper":"lower";
   }
}
