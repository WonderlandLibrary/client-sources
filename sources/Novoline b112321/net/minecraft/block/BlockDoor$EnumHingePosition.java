package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockDoor$EnumHingePosition implements IStringSerializable {
   LEFT,
   RIGHT;

   private static final BlockDoor$EnumHingePosition[] $VALUES = new BlockDoor$EnumHingePosition[]{LEFT, RIGHT};

   public String toString() {
      return this.getName();
   }

   public String getName() {
      return this == LEFT?"left":"right";
   }
}
