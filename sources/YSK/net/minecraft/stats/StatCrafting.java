package net.minecraft.stats;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;

public class StatCrafting extends StatBase
{
    private final Item field_150960_a;
    
    public StatCrafting(final String s, final String s2, final IChatComponent chatComponent, final Item field_150960_a) {
        super(String.valueOf(s) + s2, chatComponent);
        this.field_150960_a = field_150960_a;
        final int idFromItem = Item.getIdFromItem(field_150960_a);
        if (idFromItem != 0) {
            IScoreObjectiveCriteria.INSTANCES.put(String.valueOf(s) + idFromItem, this.func_150952_k());
        }
    }
    
    public Item func_150959_a() {
        return this.field_150960_a;
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
