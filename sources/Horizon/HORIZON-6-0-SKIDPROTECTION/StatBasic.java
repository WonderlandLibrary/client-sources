package HORIZON-6-0-SKIDPROTECTION;

public class StatBasic extends StatBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001469";
    
    public StatBasic(final String p_i45303_1_, final IChatComponent p_i45303_2_, final IStatType p_i45303_3_) {
        super(p_i45303_1_, p_i45303_2_, p_i45303_3_);
    }
    
    public StatBasic(final String p_i45304_1_, final IChatComponent p_i45304_2_) {
        super(p_i45304_1_, p_i45304_2_);
    }
    
    @Override
    public StatBase Ø() {
        super.Ø();
        StatList.Ý.add(this);
        return this;
    }
}
