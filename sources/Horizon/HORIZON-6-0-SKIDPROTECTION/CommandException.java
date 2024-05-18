package HORIZON-6-0-SKIDPROTECTION;

public class CommandException extends Exception
{
    private final Object[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00001187";
    
    public CommandException(final String p_i1359_1_, final Object... p_i1359_2_) {
        super(p_i1359_1_);
        this.HorizonCode_Horizon_È = p_i1359_2_;
    }
    
    public Object[] HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
}
