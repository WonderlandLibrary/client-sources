package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandBroadcast extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000191";
    
    @Override
    public String Ý() {
        return "say";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 1;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.say.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 0 && args[0].length() > 0) {
            final IChatComponent var3 = CommandBase.Â(sender, args, 0, true);
            MinecraftServer.áƒ().Œ().HorizonCode_Horizon_È(new ChatComponentTranslation("chat.type.announcement", new Object[] { sender.Ý(), var3 }));
            return;
        }
        throw new WrongUsageException("commands.say.usage", new Object[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length >= 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : null;
    }
}
