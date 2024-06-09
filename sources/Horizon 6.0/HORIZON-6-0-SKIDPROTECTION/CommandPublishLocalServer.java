package HORIZON-6-0-SKIDPROTECTION;

public class CommandPublishLocalServer extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000799";
    
    @Override
    public String Ý() {
        return "publish";
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.publish.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        final String var3 = MinecraftServer.áƒ().HorizonCode_Horizon_È(WorldSettings.HorizonCode_Horizon_È.Â, false);
        if (var3 != null) {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.publish.started", var3);
        }
        else {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.publish.failed", new Object[0]);
        }
    }
}
