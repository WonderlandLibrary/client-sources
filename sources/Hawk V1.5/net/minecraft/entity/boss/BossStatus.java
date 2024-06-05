package net.minecraft.entity.boss;

public final class BossStatus {
   public static float healthScale;
   public static int statusBarTime;
   private static final String __OBFID = "CL_00000941";
   public static String bossName;
   public static boolean hasColorModifier;

   public static void setBossStatus(IBossDisplayData var0, boolean var1) {
      healthScale = var0.getHealth() / var0.getMaxHealth();
      statusBarTime = 100;
      bossName = var0.getDisplayName().getFormattedText();
      hasColorModifier = var1;
   }
}
