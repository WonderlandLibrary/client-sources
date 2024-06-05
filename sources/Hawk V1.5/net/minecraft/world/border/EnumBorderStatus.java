package net.minecraft.world.border;

public enum EnumBorderStatus {
   SHRINKING("SHRINKING", 1, 16724016),
   STATIONARY("STATIONARY", 2, 2138367);

   private final int id;
   GROWING("GROWING", 0, 4259712);

   private static final String __OBFID = "CL_00002013";
   private static final EnumBorderStatus[] $VALUES = new EnumBorderStatus[]{GROWING, SHRINKING, STATIONARY};
   private static final EnumBorderStatus[] ENUM$VALUES = new EnumBorderStatus[]{GROWING, SHRINKING, STATIONARY};

   private EnumBorderStatus(String var3, int var4, int var5) {
      this.id = var5;
   }

   public int func_177766_a() {
      return this.id;
   }
}
