package net.minecraft.stats;

import net.minecraft.util.*;

public class StatBasic extends StatBase
{
    @Override
    public StatBase registerStat() {
        super.registerStat();
        StatList.generalStats.add(this);
        return this;
    }
    
    public StatBasic(final String s, final IChatComponent chatComponent) {
        super(s, chatComponent);
    }
    
    public StatBasic(final String s, final IChatComponent chatComponent, final IStatType statType) {
        super(s, chatComponent, statType);
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
