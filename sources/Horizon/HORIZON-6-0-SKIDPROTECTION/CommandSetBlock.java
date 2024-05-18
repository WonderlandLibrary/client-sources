package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.List;

public class CommandSetBlock extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000949";
    
    @Override
    public String Ý() {
        return "setblock";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.setblock.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 4) {
            throw new WrongUsageException("commands.setblock.usage", new Object[0]);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 0);
        final BlockPos var3 = CommandBase.HorizonCode_Horizon_È(sender, args, 0, false);
        final Block var4 = CommandBase.à(sender, args[3]);
        int var5 = 0;
        if (args.length >= 5) {
            var5 = CommandBase.HorizonCode_Horizon_È(args[4], 0, 15);
        }
        final World var6 = sender.k_();
        if (!var6.Ó(var3)) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound var7 = new NBTTagCompound();
        boolean var8 = false;
        if (args.length >= 7 && var4.£á()) {
            final String var9 = CommandBase.HorizonCode_Horizon_È(sender, args, 6).Ø();
            try {
                var7 = JsonToNBT.HorizonCode_Horizon_È(var9);
                var8 = true;
            }
            catch (NBTException var10) {
                throw new CommandException("commands.setblock.tagError", new Object[] { var10.getMessage() });
            }
        }
        if (args.length >= 6) {
            if (args[5].equals("destroy")) {
                var6.Â(var3, true);
                if (var4 == Blocks.Â) {
                    CommandBase.HorizonCode_Horizon_È(sender, this, "commands.setblock.success", new Object[0]);
                    return;
                }
            }
            else if (args[5].equals("keep") && !var6.Ø­áŒŠá(var3)) {
                throw new CommandException("commands.setblock.noChange", new Object[0]);
            }
        }
        final TileEntity var11 = var6.HorizonCode_Horizon_È(var3);
        if (var11 != null) {
            if (var11 instanceof IInventory) {
                ((IInventory)var11).ŒÏ();
            }
            var6.HorizonCode_Horizon_È(var3, Blocks.Â.¥à(), (var4 == Blocks.Â) ? 2 : 4);
        }
        final IBlockState var12 = var4.Ý(var5);
        if (!var6.HorizonCode_Horizon_È(var3, var12, 2)) {
            throw new CommandException("commands.setblock.noChange", new Object[0]);
        }
        if (var8) {
            final TileEntity var13 = var6.HorizonCode_Horizon_È(var3);
            if (var13 != null) {
                var7.HorizonCode_Horizon_È("x", var3.HorizonCode_Horizon_È());
                var7.HorizonCode_Horizon_È("y", var3.Â());
                var7.HorizonCode_Horizon_È("z", var3.Ý());
                var13.HorizonCode_Horizon_È(var7);
            }
        }
        var6.HorizonCode_Horizon_È(var3, var12.Ý());
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 1);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.setblock.success", new Object[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length > 0 && args.length <= 3) ? CommandBase.HorizonCode_Horizon_È(args, 0, pos) : ((args.length == 4) ? CommandBase.HorizonCode_Horizon_È(args, Block.HorizonCode_Horizon_È.Ý()) : ((args.length == 6) ? CommandBase.HorizonCode_Horizon_È(args, "replace", "destroy", "keep") : null));
    }
}
