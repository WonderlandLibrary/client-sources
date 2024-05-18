package net.minecraft.scoreboard;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class GoalColor implements IScoreObjectiveCriteria
{
    private final String goalName;
    
    public GoalColor(final String s, final EnumChatFormatting enumChatFormatting) {
        this.goalName = String.valueOf(s) + enumChatFormatting.getFriendlyName();
        IScoreObjectiveCriteria.INSTANCES.put(this.goalName, this);
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int func_96635_a(final List<EntityPlayer> list) {
        return "".length();
    }
    
    @Override
    public EnumRenderType getRenderType() {
        return EnumRenderType.INTEGER;
    }
    
    @Override
    public String getName() {
        return this.goalName;
    }
    
    @Override
    public boolean isReadOnly() {
        return "".length() != 0;
    }
}
