package net.minecraft.scoreboard;

import net.minecraft.util.*;
import java.util.*;

public class GoalColor implements IScoreObjectiveCriteria
{
    private final String field_178794_j;
    
    public GoalColor(final String p_i45549_1_, final EnumChatFormatting p_i45549_2_) {
        this.field_178794_j = p_i45549_1_ + p_i45549_2_.getFriendlyName();
        IScoreObjectiveCriteria.INSTANCES.put(this.field_178794_j, this);
    }
    
    @Override
    public String getName() {
        return this.field_178794_j;
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
