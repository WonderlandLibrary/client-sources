package net.minecraft.src;

import java.util.*;

final class ScoreComparator implements Comparator
{
    public int func_96659_a(final Score par1Score, final Score par2Score) {
        return (par1Score.func_96652_c() > par2Score.func_96652_c()) ? 1 : ((par1Score.func_96652_c() < par2Score.func_96652_c()) ? -1 : 0);
    }
    
    @Override
    public int compare(final Object par1Obj, final Object par2Obj) {
        return this.func_96659_a((Score)par1Obj, (Score)par2Obj);
    }
}
