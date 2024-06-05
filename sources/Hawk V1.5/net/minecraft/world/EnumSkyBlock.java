package net.minecraft.world;

public enum EnumSkyBlock {
   private static final String __OBFID = "CL_00000151";
   public final int defaultLightValue;
   private static final EnumSkyBlock[] $VALUES = new EnumSkyBlock[]{SKY, BLOCK};
   SKY("SKY", 0, 15);

   private static final EnumSkyBlock[] ENUM$VALUES = new EnumSkyBlock[]{SKY, BLOCK};
   BLOCK("BLOCK", 1, 0);

   private EnumSkyBlock(String var3, int var4, int var5) {
      this.defaultLightValue = var5;
   }
}
