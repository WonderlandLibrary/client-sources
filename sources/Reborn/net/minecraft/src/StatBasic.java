package net.minecraft.src;

public class StatBasic extends StatBase
{
    public StatBasic(final int par1, final String par2Str, final IStatType par3IStatType) {
        super(par1, par2Str, par3IStatType);
    }
    
    public StatBasic(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public StatBase registerStat() {
        super.registerStat();
        StatList.generalStats.add(this);
        return this;
    }
}
