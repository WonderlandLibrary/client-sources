package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandEmote extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000351";
    
    @Override
    public String Ý() {
        return "me";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 0;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.me.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length <= 0) {
            throw new WrongUsageException("commands.me.usage", new Object[0]);
        }
        final IChatComponent var3 = CommandBase.Â(sender, args, 0, !(sender instanceof EntityPlayer));
        MinecraftServer.áƒ().Œ().HorizonCode_Horizon_È(new ChatComponentTranslation("chat.type.emote", new Object[] { sender.Ý(), var3 }));
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá());
    }
}
