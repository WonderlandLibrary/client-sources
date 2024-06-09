package HORIZON-6-0-SKIDPROTECTION;

public class CommandKill extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000570";
    
    @Override
    public String Ý() {
        return "kill";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.kill.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 0) {
            final EntityPlayerMP var4 = CommandBase.Â(sender);
            var4.ÇŽÕ();
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.kill.successful", var4.Ý());
        }
        else {
            final Entity var5 = CommandBase.Â(sender, args[0]);
            var5.ÇŽÕ();
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.kill.successful", var5.Ý());
        }
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
