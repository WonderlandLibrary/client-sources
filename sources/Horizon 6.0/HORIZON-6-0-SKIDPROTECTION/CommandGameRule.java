package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Iterator;

public class CommandGameRule extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000475";
    
    @Override
    public String Ý() {
        return "gamerule";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.gamerule.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        final GameRules var3 = this.Ø­áŒŠá();
        final String var4 = (args.length > 0) ? args[0] : "";
        final String var5 = (args.length > 1) ? CommandBase.HorizonCode_Horizon_È(args, 1) : "";
        switch (args.length) {
            case 0: {
                sender.HorizonCode_Horizon_È(new ChatComponentText(CommandBase.HorizonCode_Horizon_È((Object[])var3.Â())));
                break;
            }
            case 1: {
                if (!var3.Ø­áŒŠá(var4)) {
                    throw new CommandException("commands.gamerule.norule", new Object[] { var4 });
                }
                final String var6 = var3.HorizonCode_Horizon_È(var4);
                sender.HorizonCode_Horizon_È(new ChatComponentText(var4).Â(" = ").Â(var6));
                sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, var3.Ý(var4));
                break;
            }
            default: {
                if (var3.HorizonCode_Horizon_È(var4, GameRules.Â.Â) && !"true".equals(var5) && !"false".equals(var5)) {
                    throw new CommandException("commands.generic.boolean.invalid", new Object[] { var5 });
                }
                var3.HorizonCode_Horizon_È(var4, var5);
                HorizonCode_Horizon_È(var3, var4);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.gamerule.success", new Object[0]);
                break;
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final GameRules p_175773_0_, final String p_175773_1_) {
        if ("reducedDebugInfo".equals(p_175773_1_)) {
            final int var2 = p_175773_0_.Â(p_175773_1_) ? 22 : 23;
            for (final EntityPlayerMP var4 : MinecraftServer.áƒ().Œ().Âµá€) {
                var4.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S19PacketEntityStatus(var4, (byte)var2));
            }
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            return CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá().Â());
        }
        if (args.length == 2) {
            final GameRules var4 = this.Ø­áŒŠá();
            if (var4.HorizonCode_Horizon_È(args[0], GameRules.Â.Â)) {
                return CommandBase.HorizonCode_Horizon_È(args, "true", "false");
            }
        }
        return null;
    }
    
    private GameRules Ø­áŒŠá() {
        return MinecraftServer.áƒ().HorizonCode_Horizon_È(0).Çªà¢();
    }
}
