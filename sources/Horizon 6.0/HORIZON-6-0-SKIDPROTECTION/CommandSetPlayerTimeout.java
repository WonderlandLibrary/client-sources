package HORIZON-6-0-SKIDPROTECTION;

public class CommandSetPlayerTimeout extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000999";
    
    @Override
    public String Ý() {
        return "setidletimeout";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.setidletimeout.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1) {
            throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
        }
        final int var3 = CommandBase.HorizonCode_Horizon_È(args[0], 0);
        MinecraftServer.áƒ().Ý(var3);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.setidletimeout.success", var3);
    }
}
