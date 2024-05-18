package net.minecraft.entity.boss;

public final class BossStatus
{
    public static float healthScale;
    public static String bossName;
    public static int statusBarTime;
    public static boolean hasColorModifier;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void setBossStatus(final IBossDisplayData bossDisplayData, final boolean hasColorModifier) {
        BossStatus.healthScale = bossDisplayData.getHealth() / bossDisplayData.getMaxHealth();
        BossStatus.statusBarTime = (0x4B ^ 0x2F);
        BossStatus.bossName = bossDisplayData.getDisplayName().getFormattedText();
        BossStatus.hasColorModifier = hasColorModifier;
    }
}
