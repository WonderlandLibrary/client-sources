package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandWorldBorder extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002336";
    
    @Override
    public String Ý() {
        return "worldborder";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.worldborder.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
        }
        final WorldBorder var3 = this.Ø­áŒŠá();
        if (args[0].equals("set")) {
            if (args.length != 2 && args.length != 3) {
                throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
            }
            final double var4 = var3.áˆºÑ¢Õ();
            final double var5 = CommandBase.HorizonCode_Horizon_È(args[1], 1.0, 6.0E7);
            final long var6 = (args.length > 2) ? (CommandBase.HorizonCode_Horizon_È(args[2], 0L, 9223372036854775L) * 1000L) : 0L;
            if (var6 > 0L) {
                var3.HorizonCode_Horizon_È(var4, var5, var6);
                if (var4 > var5) {
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.setSlowly.shrink.success", String.format("%.1f", var5), String.format("%.1f", var4), Long.toString(var6 / 1000L));
                }
                else {
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.setSlowly.grow.success", String.format("%.1f", var5), String.format("%.1f", var4), Long.toString(var6 / 1000L));
                }
            }
            else {
                var3.HorizonCode_Horizon_È(var5);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.set.success", String.format("%.1f", var5), String.format("%.1f", var4));
            }
        }
        else if (args[0].equals("add")) {
            if (args.length != 2 && args.length != 3) {
                throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
            }
            final double var4 = var3.Ø();
            final double var5 = var4 + CommandBase.HorizonCode_Horizon_È(args[1], -var4, 6.0E7 - var4);
            final long var6 = var3.áŒŠÆ() + ((args.length > 2) ? (CommandBase.HorizonCode_Horizon_È(args[2], 0L, 9223372036854775L) * 1000L) : 0L);
            if (var6 > 0L) {
                var3.HorizonCode_Horizon_È(var4, var5, var6);
                if (var4 > var5) {
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.setSlowly.shrink.success", String.format("%.1f", var5), String.format("%.1f", var4), Long.toString(var6 / 1000L));
                }
                else {
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.setSlowly.grow.success", String.format("%.1f", var5), String.format("%.1f", var4), Long.toString(var6 / 1000L));
                }
            }
            else {
                var3.HorizonCode_Horizon_È(var5);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.set.success", String.format("%.1f", var5), String.format("%.1f", var4));
            }
        }
        else if (args[0].equals("center")) {
            if (args.length != 3) {
                throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
            }
            final BlockPos var7 = sender.£á();
            final double var8 = CommandBase.Â(var7.HorizonCode_Horizon_È() + 0.5, args[1], true);
            final double var9 = CommandBase.Â(var7.Ý() + 0.5, args[2], true);
            var3.Â(var8, var9);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.center.success", var8, var9);
        }
        else if (args[0].equals("damage")) {
            if (args.length < 2) {
                throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
            }
            if (args[1].equals("buffer")) {
                if (args.length != 3) {
                    throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
                }
                final double var4 = CommandBase.HorizonCode_Horizon_È(args[2], 0.0);
                final double var5 = var3.ˆÏ­();
                var3.Â(var4);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.damage.buffer.success", String.format("%.1f", var4), String.format("%.1f", var5));
            }
            else if (args[1].equals("amount")) {
                if (args.length != 3) {
                    throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
                }
                final double var4 = CommandBase.HorizonCode_Horizon_È(args[2], 0.0);
                final double var5 = var3.£á();
                var3.Ý(var4);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.damage.amount.success", String.format("%.2f", var4), String.format("%.2f", var5));
            }
        }
        else if (args[0].equals("warning")) {
            if (args.length < 2) {
                throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
            }
            final int var10 = CommandBase.HorizonCode_Horizon_È(args[2], 0);
            if (args[1].equals("time")) {
                if (args.length != 3) {
                    throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
                }
                final int var11 = var3.£à();
                var3.Â(var10);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.warning.time.success", var10, var11);
            }
            else if (args[1].equals("distance")) {
                if (args.length != 3) {
                    throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
                }
                final int var11 = var3.µà();
                var3.Ý(var10);
                CommandBase.HorizonCode_Horizon_È(sender, this, "commands.worldborder.warning.distance.success", var10, var11);
            }
        }
        else if (args[0].equals("get")) {
            final double var4 = var3.Ø();
            sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Âµá€, MathHelper.Ý(var4 + 0.5));
            sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.worldborder.get.success", new Object[] { String.format("%.0f", var4) }));
        }
    }
    
    protected WorldBorder Ø­áŒŠá() {
        return MinecraftServer.áƒ().Ý[0].áŠ();
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "set", "center", "damage", "warning", "add", "get") : ((args.length == 2 && args[0].equals("damage")) ? CommandBase.HorizonCode_Horizon_È(args, "buffer", "amount") : ((args.length == 2 && args[0].equals("warning")) ? CommandBase.HorizonCode_Horizon_È(args, "time", "distance") : null));
    }
}
