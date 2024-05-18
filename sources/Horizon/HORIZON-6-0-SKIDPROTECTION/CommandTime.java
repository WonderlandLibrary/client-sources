package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandTime extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001183";
    
    @Override
    public String Ý() {
        return "time";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.time.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 1) {
            if (args[0].equals("set")) {
                int var3;
                if (args[1].equals("day")) {
                    var3 = 1000;
                }
                else if (args[1].equals("night")) {
                    var3 = 13000;
                }
                else {
                    var3 = CommandBase.HorizonCode_Horizon_È(args[1], 0);
                }
                this.HorizonCode_Horizon_È(sender, var3);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.time.set", var3);
                return;
            }
            if (args[0].equals("add")) {
                final int var3 = CommandBase.HorizonCode_Horizon_È(args[1], 0);
                this.Â(sender, var3);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.time.added", var3);
                return;
            }
            if (args[0].equals("query")) {
                if (args[1].equals("daytime")) {
                    final int var3 = (int)(sender.k_().Ï­Ðƒà() % 2147483647L);
                    sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var3);
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.time.query", var3);
                    return;
                }
                if (args[1].equals("gametime")) {
                    final int var3 = (int)(sender.k_().Šáƒ() % 2147483647L);
                    sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var3);
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.time.query", var3);
                    return;
                }
            }
        }
        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "set", "add", "query") : ((args.length == 2 && args[0].equals("set")) ? CommandBase.HorizonCode_Horizon_È(args, "day", "night") : ((args.length == 2 && args[0].equals("query")) ? CommandBase.HorizonCode_Horizon_È(args, "daytime", "gametime") : null));
    }
    
    protected void HorizonCode_Horizon_È(final ICommandSender p_71552_1_, final int p_71552_2_) {
        for (int var3 = 0; var3 < MinecraftServer.áƒ().Ý.length; ++var3) {
            MinecraftServer.áƒ().Ý[var3].HorizonCode_Horizon_È((long)p_71552_2_);
        }
    }
    
    protected void Â(final ICommandSender p_71553_1_, final int p_71553_2_) {
        for (int var3 = 0; var3 < MinecraftServer.áƒ().Ý.length; ++var3) {
            final WorldServer var4 = MinecraftServer.áƒ().Ý[var3];
            var4.HorizonCode_Horizon_È(var4.Ï­Ðƒà() + p_71553_2_);
        }
    }
}
