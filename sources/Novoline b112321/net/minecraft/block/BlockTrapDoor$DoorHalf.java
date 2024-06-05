package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockTrapDoor$DoorHalf implements IStringSerializable {
   TOP("top"),
   BOTTOM("bottom");

   private final String name;
   private static final BlockTrapDoor$DoorHalf[] $VALUES = new BlockTrapDoor$DoorHalf[]{TOP, BOTTOM};

   private BlockTrapDoor$DoorHalf(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }
}
