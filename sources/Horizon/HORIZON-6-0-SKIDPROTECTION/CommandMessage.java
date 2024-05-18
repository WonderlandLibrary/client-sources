package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import java.util.List;

public class CommandMessage extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000641";
    
    @Override
    public List Â() {
        return Arrays.asList("w", "msg");
    }
    
    @Override
    public String Ý() {
        return "tell";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 0;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.message.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.message.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.HorizonCode_Horizon_È(sender, args[0]);
        if (var3 == sender) {
            throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
        }
        final IChatComponent var4 = CommandBase.Â(sender, args, 1, !(sender instanceof EntityPlayer));
        final ChatComponentTranslation var5 = new ChatComponentTranslation("commands.message.display.incoming", new Object[] { sender.Ý(), var4.Âµá€() });
        final ChatComponentTranslation var6 = new ChatComponentTranslation("commands.message.display.outgoing", new Object[] { var3.Ý(), var4.Âµá€() });
        var5.à().HorizonCode_Horizon_È(EnumChatFormatting.Ø).Â(Boolean.valueOf(true));
        var6.à().HorizonCode_Horizon_È(EnumChatFormatting.Ø).Â(Boolean.valueOf(true));
        var3.HorizonCode_Horizon_È(var5);
        sender.HorizonCode_Horizon_È(var6);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá());
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
