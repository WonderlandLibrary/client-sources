package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandServerKick extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000550";
    
    @Override
    public String Ý() {
        return "kick";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.kick.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length <= 0 || args[0].length() <= 1) {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = MinecraftServer.áƒ().Œ().HorizonCode_Horizon_È(args[0]);
        String var4 = "Kicked by an operator.";
        boolean var5 = false;
        if (var3 == null) {
            throw new PlayerNotFoundException();
        }
        if (args.length >= 2) {
            var4 = CommandBase.HorizonCode_Horizon_È(sender, args, 1).Ø();
            var5 = true;
        }
        var3.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var4);
        if (var5) {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.kick.success.reason", var3.v_(), var4);
        }
        else {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.kick.success", var3.v_());
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length >= 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : null;
    }
}
