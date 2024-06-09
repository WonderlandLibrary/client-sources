package HORIZON-6-0-SKIDPROTECTION;

public class CommandStop extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001132";
    
    @Override
    public String Ý() {
        return "stop";
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.stop.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (MinecraftServer.áƒ().Ý != null) {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.stop.start", new Object[0]);
        }
        MinecraftServer.áƒ().Ø­à();
    }
}
