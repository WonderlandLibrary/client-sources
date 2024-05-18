package net.minecraft.item;

import net.minecraft.util.*;

public enum EnumRarity
{
    RARE(EnumRarity.I[0x53 ^ 0x57], "  ".length(), EnumChatFormatting.AQUA, EnumRarity.I[0xC ^ 0x9]);
    
    private static final EnumRarity[] ENUM$VALUES;
    public final String rarityName;
    
    COMMON(EnumRarity.I["".length()], "".length(), EnumChatFormatting.WHITE, EnumRarity.I[" ".length()]), 
    EPIC(EnumRarity.I[0x5D ^ 0x5B], "   ".length(), EnumChatFormatting.LIGHT_PURPLE, EnumRarity.I[0x4C ^ 0x4B]), 
    UNCOMMON(EnumRarity.I["  ".length()], " ".length(), EnumChatFormatting.YELLOW, EnumRarity.I["   ".length()]);
    
    private static final String[] I;
    public final EnumChatFormatting rarityColor;
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x4C ^ 0x44])["".length()] = I("\u0013:?*.\u001e", "Purga");
        EnumRarity.I[" ".length()] = I("\u001a\u0019;9\f7", "YvVTc");
        EnumRarity.I["  ".length()] = I("\u0004:\t\u0017\u000f\u001c;\u0004", "QtJXB");
        EnumRarity.I["   ".length()] = I("%;\u00017>\u001d:\f", "pUbXS");
        EnumRarity.I[0x6A ^ 0x6E] = I("\u001b&\u00027", "IgPrX");
        EnumRarity.I[0xA5 ^ 0xA0] = I("\u001d\u000242", "OcFWE");
        EnumRarity.I[0x48 ^ 0x4E] = I(" \n*!", "eZcbv");
        EnumRarity.I[0x30 ^ 0x37] = I("22\u0010\r", "wBynC");
    }
    
    static {
        I();
        final EnumRarity[] enum$VALUES = new EnumRarity[0x41 ^ 0x45];
        enum$VALUES["".length()] = EnumRarity.COMMON;
        enum$VALUES[" ".length()] = EnumRarity.UNCOMMON;
        enum$VALUES["  ".length()] = EnumRarity.RARE;
        enum$VALUES["   ".length()] = EnumRarity.EPIC;
        ENUM$VALUES = enum$VALUES;
    }
    
    private EnumRarity(final String s, final int n, final EnumChatFormatting rarityColor, final String rarityName) {
        this.rarityColor = rarityColor;
        this.rarityName = rarityName;
    }
}
