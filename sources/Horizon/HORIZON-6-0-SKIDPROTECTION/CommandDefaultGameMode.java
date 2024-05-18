package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class CommandDefaultGameMode extends CommandGameMode
{
    private static final String HorizonCode_Horizon_È = "CL_00000296";
    
    @Override
    public String Ý() {
        return "defaultgamemode";
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.defaultgamemode.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length <= 0) {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
        final WorldSettings.HorizonCode_Horizon_È var3 = this.Ø(sender, args[0]);
        this.HorizonCode_Horizon_È(var3);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.defaultgamemode.success", new ChatComponentTranslation("gameMode." + var3.Â(), new Object[0]));
    }
    
    protected void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È p_71541_1_) {
        final MinecraftServer var2 = MinecraftServer.áƒ();
        var2.HorizonCode_Horizon_È(p_71541_1_);
        if (var2.£Õ()) {
            for (final EntityPlayerMP var4 : MinecraftServer.áƒ().Œ().Âµá€) {
                var4.HorizonCode_Horizon_È(p_71541_1_);
                var4.Ï­à = 0.0f;
            }
        }
    }
}
