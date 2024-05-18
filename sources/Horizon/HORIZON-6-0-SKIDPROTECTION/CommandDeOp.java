package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.mojang.authlib.GameProfile;

public class CommandDeOp extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000244";
    
    @Override
    public String Ý() {
        return "deop";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.deop.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1 || args[0].length() <= 0) {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.áƒ();
        final GameProfile var4 = var3.Œ().£á().HorizonCode_Horizon_È(args[0]);
        if (var4 == null) {
            throw new CommandException("commands.deop.failed", new Object[] { args[0] });
        }
        var3.Œ().Ý(var4);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.deop.success", args[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().Œ().Å()) : null;
    }
}
