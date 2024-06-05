package net.minecraft.util;

public enum EnumWorldBlockLayer {
   SOLID("Solid"),
   CUTOUT_MIPPED("Mipped Cutout"),
   CUTOUT("Cutout"),
   TRANSLUCENT("Translucent");

   private final String layerName;

   private EnumWorldBlockLayer(String var3) {
      this.layerName = var3;
   }

   public String toString() {
      return this.layerName;
   }
}
