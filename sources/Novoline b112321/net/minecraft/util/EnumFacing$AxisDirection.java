package net.minecraft.util;

public enum EnumFacing$AxisDirection {
   POSITIVE("POSITIVE", 0, 1, "Towards positive"),
   NEGATIVE("NEGATIVE", 1, -1, "Towards negative");

   private final int offset;
   private final String description;
   private static final EnumFacing$AxisDirection[] $VALUES = new EnumFacing$AxisDirection[]{POSITIVE, NEGATIVE};

   private EnumFacing$AxisDirection(String var3, int var4, int var5, String var6) {
      this.offset = var5;
      this.description = var6;
   }

   public int getOffset() {
      return this.offset;
   }

   public String toString() {
      return this.description;
   }
}
