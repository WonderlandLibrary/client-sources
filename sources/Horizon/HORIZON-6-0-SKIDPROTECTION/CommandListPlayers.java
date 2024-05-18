package HORIZON-6-0-SKIDPROTECTION;

public class CommandListPlayers extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000615";
    
    @Override
    public String Ý() {
        return "list";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 0;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.players.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        final int var3 = MinecraftServer.áƒ().Ê();
        sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.players.list", new Object[] { var3, MinecraftServer.áƒ().ÇŽÉ() }));
        sender.HorizonCode_Horizon_È(new ChatComponentText(MinecraftServer.áƒ().Œ().Ó()));
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var3);
    }
}
