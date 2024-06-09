package HORIZON-6-0-SKIDPROTECTION;

public class EntityNotFoundException extends CommandException
{
    private static final String HorizonCode_Horizon_È = "CL_00002335";
    
    public EntityNotFoundException() {
        this("commands.generic.entity.notFound", new Object[0]);
    }
    
    public EntityNotFoundException(final String p_i46035_1_, final Object... p_i46035_2_) {
        super(p_i46035_1_, p_i46035_2_);
    }
}
