package HORIZON-6-0-SKIDPROTECTION;

public class CommandNotFoundException extends CommandException
{
    private static final String HorizonCode_Horizon_È = "CL_00001191";
    
    public CommandNotFoundException() {
        this("commands.generic.notFound", new Object[0]);
    }
    
    public CommandNotFoundException(final String p_i1363_1_, final Object... p_i1363_2_) {
        super(p_i1363_1_, p_i1363_2_);
    }
}
