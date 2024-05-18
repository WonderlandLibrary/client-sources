package net.minecraft.scoreboard;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class ScoreHealthCriteria extends ScoreDummyCriteria
{
    @Override
    public int func_96635_a(final List<EntityPlayer> list) {
        float n = 0.0f;
        final Iterator<EntityPlayer> iterator = list.iterator();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayer entityPlayer = iterator.next();
            n += entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount();
        }
        if (list.size() > 0) {
            n /= list.size();
        }
        return MathHelper.ceiling_float_int(n);
    }
    
    public ScoreHealthCriteria(final String s) {
        super(s);
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
        return IScoreObjectiveCriteria.EnumRenderType.HEARTS;
    }
    
    @Override
    public boolean isReadOnly() {
        return " ".length() != 0;
    }
}
