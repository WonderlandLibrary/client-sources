package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandXP extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000398";
    
    @Override
    public String Ý() {
        return "xp";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.xp.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length <= 0) {
            throw new WrongUsageException("commands.xp.usage", new Object[0]);
        }
        String var3 = args[0];
        final boolean var4 = var3.endsWith("l") || var3.endsWith("L");
        if (var4 && var3.length() > 1) {
            var3 = var3.substring(0, var3.length() - 1);
        }
        int var5 = CommandBase.HorizonCode_Horizon_È(var3);
        final boolean var6 = var5 < 0;
        if (var6) {
            var5 *= -1;
        }
        final EntityPlayerMP var7 = (args.length > 1) ? CommandBase.HorizonCode_Horizon_È(sender, args[1]) : CommandBase.Â(sender);
        if (var4) {
            sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var7.áŒŠÉ);
            if (var6) {
                var7.Ø­à(-var5);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.xp.success.negative.levels", var5, var7.v_());
            }
            else {
                var7.Ø­à(var5);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.xp.success.levels", var5, var7.v_());
            }
        }
        else {
            sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var7.ÇŽØ);
            if (var6) {
                throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
            }
            var7.ˆà(var5);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.xp.success", var5, var7.v_());
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 2) ? CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá()) : null;
    }
    
    protected String[] Ø­áŒŠá() {
        return MinecraftServer.áƒ().ˆá();
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 1;
    }
}
