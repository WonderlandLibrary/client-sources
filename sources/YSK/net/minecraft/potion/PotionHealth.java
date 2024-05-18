package net.minecraft.potion;

import net.minecraft.util.*;

public class PotionHealth extends Potion
{
    @Override
    public boolean isReady(final int n, final int n2) {
        if (n >= " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public PotionHealth(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
    
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isInstant() {
        return " ".length() != 0;
    }
}
