package HORIZON-6-0-SKIDPROTECTION;

public class PlayerNotFoundException extends CommandException
{
    private static final String HorizonCode_Horizon_È = "CL_00001190";
    
    public PlayerNotFoundException() {
        this("commands.generic.player.notFound", new Object[0]);
    }
    
    public PlayerNotFoundException(final String p_i1362_1_, final Object... p_i1362_2_) {
        super(p_i1362_1_, p_i1362_2_);
    }
}
