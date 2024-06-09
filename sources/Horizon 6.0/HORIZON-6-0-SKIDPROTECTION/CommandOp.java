package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;
import com.mojang.authlib.GameProfile;

public class CommandOp extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000694";
    
    @Override
    public String Ý() {
        return "op";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.op.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1 || args[0].length() <= 0) {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.áƒ();
        final GameProfile var4 = var3.áŒŠÏ().HorizonCode_Horizon_È(args[0]);
        if (var4 == null) {
            throw new CommandException("commands.op.failed", new Object[] { args[0] });
        }
        var3.Œ().Â(var4);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.op.success", args[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            final String var4 = args[args.length - 1];
            final ArrayList var5 = Lists.newArrayList();
            for (final GameProfile var9 : MinecraftServer.áƒ().ÇŽÕ()) {
                if (!MinecraftServer.áƒ().Œ().Âµá€(var9) && CommandBase.HorizonCode_Horizon_È(var4, var9.getName())) {
                    var5.add(var9.getName());
                }
            }
            return var5;
        }
        return null;
    }
}
