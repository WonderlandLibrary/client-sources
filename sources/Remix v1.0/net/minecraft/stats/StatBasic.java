package net.minecraft.stats;

import net.minecraft.util.*;

public class StatBasic extends StatBase
{
    public StatBasic(final String p_i45303_1_, final IChatComponent p_i45303_2_, final IStatType p_i45303_3_) {
        super(p_i45303_1_, p_i45303_2_, p_i45303_3_);
    }
    
    public StatBasic(final String p_i45304_1_, final IChatComponent p_i45304_2_) {
        super(p_i45304_1_, p_i45304_2_);
    }
    
    @Override
    public StatBase registerStat() {
        super.registerStat();
        StatList.generalStats.add(this);
        return this;
    }
}
