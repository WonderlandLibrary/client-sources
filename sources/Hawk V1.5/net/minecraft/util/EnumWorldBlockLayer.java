package net.minecraft.util;

public enum EnumWorldBlockLayer {
   SOLID("SOLID", 0, "Solid"),
   CUTOUT_MIPPED("CUTOUT_MIPPED", 1, "Mipped Cutout"),
   TRANSLUCENT("TRANSLUCENT", 3, "Translucent");

   private final String field_180338_e;
   private static final String __OBFID = "CL_00002152";
   CUTOUT("CUTOUT", 2, "Cutout");

   private static final EnumWorldBlockLayer[] ENUM$VALUES = new EnumWorldBlockLayer[]{SOLID, CUTOUT_MIPPED, CUTOUT, TRANSLUCENT};
   private static final EnumWorldBlockLayer[] $VALUES = new EnumWorldBlockLayer[]{SOLID, CUTOUT_MIPPED, CUTOUT, TRANSLUCENT};

   private EnumWorldBlockLayer(String var3, int var4, String var5) {
      this.field_180338_e = var5;
   }

   public String toString() {
      return this.field_180338_e;
   }
}
