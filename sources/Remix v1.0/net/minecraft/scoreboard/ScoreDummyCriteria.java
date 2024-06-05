package net.minecraft.scoreboard;

import java.util.*;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria
{
    private final String field_96644_g;
    
    public ScoreDummyCriteria(final String p_i2311_1_) {
        this.field_96644_g = p_i2311_1_;
        IScoreObjectiveCriteria.INSTANCES.put(p_i2311_1_, this);
    }
    
    @Override
    public String getName() {
        return this.field_96644_g;
    }
    
    @Override
    public int func_96635_a(final List p_96635_1_) {
        return 0;
    }
    
    @Override
    public boolean isReadOnly() {
        return false;
    }
    
    @Override
    public EnumRenderType func_178790_c() {
        return EnumRenderType.INTEGER;
    }
}
