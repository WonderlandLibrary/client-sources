package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.mojang.authlib.GameProfile;

public class CommandPardonPlayer extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000747";
    
    @Override
    public String Ý() {
        return "pardon";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.unban.usage";
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ICommandSender sender) {
        return MinecraftServer.áƒ().Œ().áŒŠÆ().HorizonCode_Horizon_È() && super.HorizonCode_Horizon_È(sender);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1 || args[0].length() <= 0) {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.áƒ();
        final GameProfile var4 = var3.Œ().áŒŠÆ().HorizonCode_Horizon_È(args[0]);
        if (var4 == null) {
            throw new CommandException("commands.unban.failed", new Object[] { args[0] });
        }
        var3.Œ().áŒŠÆ().Â(var4);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.unban.success", args[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().Œ().áŒŠÆ().Â()) : null;
    }
}
