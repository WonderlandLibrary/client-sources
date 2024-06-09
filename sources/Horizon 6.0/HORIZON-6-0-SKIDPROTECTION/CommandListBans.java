package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandListBans extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000596";
    
    @Override
    public String Ý() {
        return "banlist";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ICommandSender sender) {
        return (MinecraftServer.áƒ().Œ().áˆºÑ¢Õ().HorizonCode_Horizon_È() || MinecraftServer.áƒ().Œ().áŒŠÆ().HorizonCode_Horizon_È()) && super.HorizonCode_Horizon_È(sender);
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.banlist.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length >= 1 && args[0].equalsIgnoreCase("ips")) {
            sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.banlist.ips", new Object[] { MinecraftServer.áƒ().Œ().áˆºÑ¢Õ().Â().length }));
            sender.HorizonCode_Horizon_È(new ChatComponentText(CommandBase.HorizonCode_Horizon_È((Object[])MinecraftServer.áƒ().Œ().áˆºÑ¢Õ().Â())));
        }
        else {
            sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.banlist.players", new Object[] { MinecraftServer.áƒ().Œ().áŒŠÆ().Â().length }));
            sender.HorizonCode_Horizon_È(new ChatComponentText(CommandBase.HorizonCode_Horizon_È((Object[])MinecraftServer.áƒ().Œ().áŒŠÆ().Â())));
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "players", "ips") : null;
    }
}
