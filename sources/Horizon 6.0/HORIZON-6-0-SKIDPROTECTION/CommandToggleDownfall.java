package HORIZON-6-0-SKIDPROTECTION;

public class CommandToggleDownfall extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001184";
    
    @Override
    public String Ý() {
        return "toggledownfall";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.downfall.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        this.Ø­áŒŠá();
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.downfall.success", new Object[0]);
    }
    
    protected void Ø­áŒŠá() {
        final WorldInfo var1 = MinecraftServer.áƒ().Ý[0].ŒÏ();
        var1.Â(!var1.Å());
    }
}
