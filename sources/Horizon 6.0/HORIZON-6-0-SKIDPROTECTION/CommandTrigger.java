package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;

public class CommandTrigger extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002337";
    
    @Override
    public String Ý() {
        return "trigger";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 0;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.trigger.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 3) {
            throw new WrongUsageException("commands.trigger.usage", new Object[0]);
        }
        EntityPlayerMP var3;
        if (sender instanceof EntityPlayerMP) {
            var3 = (EntityPlayerMP)sender;
        }
        else {
            final Entity var4 = sender.l_();
            if (!(var4 instanceof EntityPlayerMP)) {
                throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
            }
            var3 = (EntityPlayerMP)var4;
        }
        final Scoreboard var5 = MinecraftServer.áƒ().HorizonCode_Horizon_È(0).à¢();
        final ScoreObjective var6 = var5.HorizonCode_Horizon_È(args[0]);
        if (var6 == null || var6.Ý() != IScoreObjectiveCriteria.Ý) {
            throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
        }
        final int var7 = CommandBase.HorizonCode_Horizon_È(args[2]);
        if (!var5.HorizonCode_Horizon_È(var3.v_(), var6)) {
            throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
        }
        final Score var8 = var5.Â(var3.v_(), var6);
        if (var8.Ó()) {
            throw new CommandException("commands.trigger.disabled", new Object[] { args[0] });
        }
        if ("set".equals(args[1])) {
            var8.Ý(var7);
        }
        else {
            if (!"add".equals(args[1])) {
                throw new CommandException("commands.trigger.invalidMode", new Object[] { args[1] });
            }
            var8.HorizonCode_Horizon_È(var7);
        }
        var8.HorizonCode_Horizon_È(true);
        if (var3.Ý.Ý()) {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.trigger.success", args[0], args[1], args[2]);
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            final Scoreboard var4 = MinecraftServer.áƒ().HorizonCode_Horizon_È(0).à¢();
            final ArrayList var5 = Lists.newArrayList();
            for (final ScoreObjective var7 : var4.HorizonCode_Horizon_È()) {
                if (var7.Ý() == IScoreObjectiveCriteria.Ý) {
                    var5.add(var7.Â());
                }
            }
            return CommandBase.HorizonCode_Horizon_È(args, (String[])var5.toArray(new String[var5.size()]));
        }
        return (args.length == 2) ? CommandBase.HorizonCode_Horizon_È(args, "add", "set") : null;
    }
}
