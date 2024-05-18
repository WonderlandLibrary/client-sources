package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;

public class CommandStats extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002339";
    
    @Override
    public String Ý() {
        return "stats";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.stats.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.stats.usage", new Object[0]);
        }
        boolean var3;
        if (args[0].equals("entity")) {
            var3 = false;
        }
        else {
            if (!args[0].equals("block")) {
                throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }
            var3 = true;
        }
        byte var4;
        if (var3) {
            if (args.length < 5) {
                throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
            }
            var4 = 4;
        }
        else {
            if (args.length < 3) {
                throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
            }
            var4 = 2;
        }
        int var5 = var4 + 1;
        final String var6 = args[var4];
        if ("set".equals(var6)) {
            if (args.length < var5 + 3) {
                if (var5 == 5) {
                    throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
            }
        }
        else {
            if (!"clear".equals(var6)) {
                throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }
            if (args.length < var5 + 1) {
                if (var5 == 5) {
                    throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
            }
        }
        final CommandResultStats.HorizonCode_Horizon_È var7 = CommandResultStats.HorizonCode_Horizon_È.HorizonCode_Horizon_È(args[var5++]);
        if (var7 == null) {
            throw new CommandException("commands.stats.failed", new Object[0]);
        }
        final World var8 = sender.k_();
        CommandResultStats var11;
        if (var3) {
            final BlockPos var9 = CommandBase.HorizonCode_Horizon_È(sender, args, 1, false);
            final TileEntity var10 = var8.HorizonCode_Horizon_È(var9);
            if (var10 == null) {
                throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { var9.HorizonCode_Horizon_È(), var9.Â(), var9.Ý() });
            }
            if (var10 instanceof TileEntityCommandBlock) {
                var11 = ((TileEntityCommandBlock)var10).Â();
            }
            else {
                if (!(var10 instanceof TileEntitySign)) {
                    throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { var9.HorizonCode_Horizon_È(), var9.Â(), var9.Ý() });
                }
                var11 = ((TileEntitySign)var10).Ý();
            }
        }
        else {
            final Entity var12 = CommandBase.Â(sender, args[1]);
            var11 = var12.¥É();
        }
        if ("set".equals(var6)) {
            final String var13 = args[var5++];
            final String var14 = args[var5];
            if (var13.length() == 0 || var14.length() == 0) {
                throw new CommandException("commands.stats.failed", new Object[0]);
            }
            CommandResultStats.HorizonCode_Horizon_È(var11, var7, var13, var14);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.stats.success", var7.Â(), var14, var13);
        }
        else if ("clear".equals(var6)) {
            CommandResultStats.HorizonCode_Horizon_È(var11, var7, null, null);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.stats.cleared", var7.Â());
        }
        if (var3) {
            final BlockPos var9 = CommandBase.HorizonCode_Horizon_È(sender, args, 1, false);
            final TileEntity var10 = var8.HorizonCode_Horizon_È(var9);
            var10.ŠÄ();
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "entity", "block") : ((args.length == 2 && args[0].equals("entity")) ? CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá()) : (((args.length != 3 || !args[0].equals("entity")) && (args.length != 5 || !args[0].equals("block"))) ? (((args.length != 4 || !args[0].equals("entity")) && (args.length != 6 || !args[0].equals("block"))) ? (((args.length != 6 || !args[0].equals("entity")) && (args.length != 8 || !args[0].equals("block"))) ? null : CommandBase.HorizonCode_Horizon_È(args, this.Âµá€())) : CommandBase.HorizonCode_Horizon_È(args, CommandResultStats.HorizonCode_Horizon_È.Ý())) : CommandBase.HorizonCode_Horizon_È(args, "set", "clear")));
    }
    
    protected String[] Ø­áŒŠá() {
        return MinecraftServer.áƒ().ˆá();
    }
    
    protected List Âµá€() {
        final Collection var1 = MinecraftServer.áƒ().HorizonCode_Horizon_È(0).à¢().HorizonCode_Horizon_È();
        final ArrayList var2 = Lists.newArrayList();
        for (final ScoreObjective var4 : var1) {
            if (!var4.Ý().Â()) {
                var2.add(var4.Â());
            }
        }
        return var2;
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return args.length > 0 && args[0].equals("entity") && index == 1;
    }
}
