package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockRedstoneComparator$Mode implements IStringSerializable {
   COMPARE("compare"),
   SUBTRACT("subtract");

   private final String name;
   private static final BlockRedstoneComparator$Mode[] $VALUES = new BlockRedstoneComparator$Mode[]{COMPARE, SUBTRACT};

   private BlockRedstoneComparator$Mode(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }
}
