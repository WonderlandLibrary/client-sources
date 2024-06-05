package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

enum BlockRedstoneWire$EnumAttachPosition implements IStringSerializable {
   UP("up"),
   SIDE("side"),
   NONE("none");

   private final String name;
   private static final BlockRedstoneWire$EnumAttachPosition[] $VALUES = new BlockRedstoneWire$EnumAttachPosition[]{UP, SIDE, NONE};

   private BlockRedstoneWire$EnumAttachPosition(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.getName();
   }

   public String getName() {
      return this.name;
   }
}
