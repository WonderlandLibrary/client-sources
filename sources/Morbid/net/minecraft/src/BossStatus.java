package net.minecraft.src;

public final class BossStatus
{
    public static float healthScale;
    public static int statusBarLength;
    public static String bossName;
    public static boolean field_82825_d;
    
    public static void func_82824_a(final IBossDisplayData par0IBossDisplayData, final boolean par1) {
        BossStatus.healthScale = par0IBossDisplayData.getBossHealth() / par0IBossDisplayData.getMaxHealth();
        BossStatus.statusBarLength = 100;
        BossStatus.bossName = par0IBossDisplayData.getEntityName();
        BossStatus.field_82825_d = par1;
    }
}
