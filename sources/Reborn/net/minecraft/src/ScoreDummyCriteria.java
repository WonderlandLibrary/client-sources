package net.minecraft.src;

import java.util.*;

public class ScoreDummyCriteria implements ScoreObjectiveCriteria
{
    private final String field_96644_g;
    
    public ScoreDummyCriteria(final String par1Str) {
        this.field_96644_g = par1Str;
        ScoreObjectiveCriteria.field_96643_a.put(par1Str, this);
    }
    
    @Override
    public String func_96636_a() {
        return this.field_96644_g;
    }
    
    @Override
    public int func_96635_a(final List par1List) {
        return 0;
    }
    
    @Override
    public boolean isReadOnly() {
        return false;
    }
}
