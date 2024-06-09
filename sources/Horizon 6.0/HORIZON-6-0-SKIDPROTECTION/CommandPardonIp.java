package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.regex.Matcher;

public class CommandPardonIp extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000720";
    
    @Override
    public String Ý() {
        return "pardon-ip";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ICommandSender sender) {
        return MinecraftServer.áƒ().Œ().áˆºÑ¢Õ().HorizonCode_Horizon_È() && super.HorizonCode_Horizon_È(sender);
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.unbanip.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1 || args[0].length() <= 1) {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
        final Matcher var3 = CommandBanIp.HorizonCode_Horizon_È.matcher(args[0]);
        if (var3.matches()) {
            MinecraftServer.áƒ().Œ().áˆºÑ¢Õ().Â(args[0]);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.unbanip.success", args[0]);
            return;
        }
        throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().Œ().áˆºÑ¢Õ().Â()) : null;
    }
}
