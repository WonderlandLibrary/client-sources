package HORIZON-6-0-SKIDPROTECTION;

public class SyntaxErrorException extends CommandException
{
    private static final String HorizonCode_Horizon_È = "CL_00001189";
    
    public SyntaxErrorException() {
        this("commands.generic.snytax", new Object[0]);
    }
    
    public SyntaxErrorException(final String p_i1361_1_, final Object... p_i1361_2_) {
        super(p_i1361_1_, p_i1361_2_);
    }
}
