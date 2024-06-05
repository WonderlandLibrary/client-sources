package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockPistonExtension$EnumPistonType implements IStringSerializable {
   DEFAULT("normal"),
   STICKY("sticky");

   private final String VARIANT;
   private static final BlockPistonExtension$EnumPistonType[] $VALUES = new BlockPistonExtension$EnumPistonType[]{DEFAULT, STICKY};

   private BlockPistonExtension$EnumPistonType(String var3) {
      this.VARIANT = var3;
   }

   public String toString() {
      return this.VARIANT;
   }

   public String getName() {
      return this.VARIANT;
   }
}
